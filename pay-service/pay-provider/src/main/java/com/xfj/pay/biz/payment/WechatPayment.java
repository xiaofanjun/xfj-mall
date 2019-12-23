package com.xfj.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.github.wxpay.sdk.WXPayUtil;
import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.BizException;
import com.xfj.commons.tool.utils.UtilDate;
import com.xfj.order.OrderCoreService;
import com.xfj.pay.biz.abs.BasePayment;
import com.xfj.pay.biz.abs.Context;
import com.xfj.pay.biz.abs.Validator;
import com.xfj.pay.biz.payment.channel.wechatpay.WeChatBuildRequest;
import com.xfj.pay.biz.payment.commons.HttpClientUtil;
import com.xfj.pay.biz.payment.constants.PayResultEnum;
import com.xfj.pay.entitys.Payment;
import com.xfj.pay.mapper.PaymentMapper;
import com.xfj.pay.rs.PaymentNotifyRS;
import com.xfj.pay.rs.PaymentRS;
import com.xfj.pay.utils.GlobalIdGeneratorUtil;
import com.xfj.pay.biz.payment.context.WechatPaymentContext;
import com.xfj.pay.constants.PayChannelEnum;
import com.xfj.pay.biz.payment.constants.PaymentConstants;
import com.xfj.pay.biz.payment.constants.WechatPaymentConfig;
import com.xfj.pay.constants.PayReturnCodeEnum;
import com.xfj.pay.vo.PaymentNotifyVO;
import com.xfj.pay.vo.PaymentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service("wechatPayment")
public class WechatPayment extends BasePayment {

    @Autowired
    private WechatPaymentConfig wechatPaymentConfig;

    @Resource(name = "wechatPaymentValidator")
    private Validator validator;

    @Autowired
    private PaymentMapper paymentMapper;

    @Autowired
    GlobalIdGeneratorUtil globalIdGeneratorUtil;

    private final String COMMENT_GLOBAL_ID_CACHE_KEY = "COMMENT_ID";

    @Reference(timeout = 3000)
    OrderCoreService orderCoreService;

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public Context createContext(AbstractRequest request) {
        WechatPaymentContext wechatPaymentContext = new WechatPaymentContext();
        PaymentVO paymentRequest = (PaymentVO) request;
        wechatPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
        wechatPaymentContext.setProductId(paymentRequest.getTradeNo());
        wechatPaymentContext.setSpbillCreateIp(paymentRequest.getSpbillCreateIp());
        wechatPaymentContext.setTradeType(PaymentConstants.TradeTypeEnum.NATIVE.getType());
        wechatPaymentContext.setTotalFee(paymentRequest.getTotalFee());
        wechatPaymentContext.setBody(paymentRequest.getSubject());
        return wechatPaymentContext;
    }

    @Override
    public void prepare(AbstractRequest request, Context context) throws BizException {
        super.prepare(request, context);
        SortedMap paraMap = context.getsParaTemp();
        WechatPaymentContext wechatPaymentContext = (WechatPaymentContext) context;
        paraMap.put("body", wechatPaymentContext.getBody());
        paraMap.put("out_trade_no", wechatPaymentContext.getOutTradeNo());
        //单位分
        paraMap.put("total_fee", wechatPaymentContext.getTotalFee().multiply(new BigDecimal("100")).intValue());
        paraMap.put("spbill_create_ip", wechatPaymentContext.getSpbillCreateIp());
        paraMap.put("appid", wechatPaymentConfig.getWechatAppid());
        paraMap.put("mch_id", wechatPaymentConfig.getWechatMch_id());
        paraMap.put("nonce_str", WeChatBuildRequest.getNonceStr());
        paraMap.put("trade_type", wechatPaymentContext.getTradeType());
        paraMap.put("product_id", wechatPaymentContext.getProductId());
        // 此路径是微信服务器调用支付结果通知路径
        paraMap.put("device_info", "WEB");
        paraMap.put("notify_url", wechatPaymentConfig.getWechatNotifyurl());
        //二维码的失效时间（5分钟）
        paraMap.put("time_expire", UtilDate.getExpireTime(30 * 60 * 1000L));
        String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
        paraMap.put("sign", sign);
        log.info("微信生成sign:{}", JSON.toJSONString(paraMap));
        String xml = WeChatBuildRequest.getRequestXml(paraMap);
        wechatPaymentContext.setXml(xml);
    }

    @Override
    public AbstractResponse generalProcess(AbstractRequest request, Context context) throws BizException {
        PaymentRS response = new PaymentRS();
        WechatPaymentContext wechatPaymentContext = (WechatPaymentContext) context;
        log.info("微信支付组装的请求参数:{}", wechatPaymentContext.getXml());
        String xml = HttpClientUtil.httpPost(wechatPaymentConfig.getWechatUnifiedOrder(), wechatPaymentContext.getXml());
        log.info("微信支付同步返回的结果:{}", xml);
        Map<String, String> resultMap = WeChatBuildRequest.doXMLParse(xml);
        if ("SUCCESS".equals(resultMap.get("return_code"))) {
            if ("SUCCESS".equals(resultMap.get("result_code"))) {
                response.setPrepayId(resultMap.get("prepay_id"));
                response.setCodeUrl(resultMap.get("code_url"));
                response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
                response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
            } else {
                String errMsg = resultMap.get("err_code") + ":" + resultMap.get("err_code_des");
                response.setCode(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getCode());
                response.setMsg(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getMsg(errMsg));
            }
        } else {
            response.setCode(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getCode());
            response.setMsg(PayReturnCodeEnum.PAYMENT_PROCESSOR_FAILED.getMsg(resultMap.get("return_msg")));
        }
        return response;
    }

    @Override
    public void afterProcess(AbstractRequest request, AbstractResponse respond, Context context) throws BizException {
        //插入支付表
        log.info("Alipayment begin - afterProcess -request:" + request + "\n response:" + respond);
        PaymentVO paymentRequest = (PaymentVO) request;
        //插入支付记录表
        PaymentRS response = (PaymentRS) respond;
        Payment payment = new Payment();
        payment.setCreatedate(new Date());
        BigDecimal amount = paymentRequest.getOrderFee();
        payment.setOrderAmount(amount);
        payment.setOrderId(paymentRequest.getTradeNo());
        //payment.setTradeNo(globalIdGeneratorUtil.getNextSeq(COMMENT_GLOBAL_ID_CACHE_KEY, 1));
        payment.setPayerAmount(amount);
        payment.setPayerUid(paymentRequest.getUserId());
        payment.setPayerName("");//TODO
        payment.setPayWay(paymentRequest.getPayChannel());
        payment.setProductName(paymentRequest.getSubject());
        payment.setStatus(PayResultEnum.TRADE_PROCESSING.getCode());//
        payment.setRemark("微信支付");
        payment.setPayNo(response.getPrepayId());//第三方的交易id
        payment.setModifydate(new Date());
        paymentMapper.insert(payment);
    }

    @Override
    public String getPayChannel() {
        return PayChannelEnum.WECHAT_PAY.getCode();
    }

    @Override
    public AbstractResponse completePayment(PaymentNotifyVO request) throws BizException {
        request.requestCheck();
        PaymentNotifyRS response = new PaymentNotifyRS();
        Map xmlMap = new HashMap();
        String xml = request.getXml();
        try {
            xmlMap = WXPayUtil.xmlToMap(xml);
        } catch (Exception e) {
            e.printStackTrace();
        }
        SortedMap<Object, Object> paraMap = new TreeMap<>();
        xmlMap.forEach(paraMap::put);
        //组装返回的结果的签名字符串
        String rsSign = paraMap.remove("sign").toString();
        String sign = WeChatBuildRequest.createSign(paraMap, wechatPaymentConfig.getWechatMchsecret());
        //验证签名
        if (rsSign.equals(sign)) {
            //SUCCESS、FAIL
            String resultCode = paraMap.get("return_code").toString();
            if ("SUCCESS".equals(resultCode)) {
                if ("SUCCESS".equals(paraMap.get("result_code"))) {
                    //更新支付表
                    Payment payment = new Payment();
                    payment.setStatus(PayResultEnum.TRADE_SUCCESS.getCode());
                    payment.setPaySuccessTime((UtilDate.parseStrToDate(UtilDate.simple, paraMap.get("time_end").toString(), new Date())));
                    Example example = new Example(Payment.class);
                    example.createCriteria().andEqualTo("orderId", paraMap.get("out_trade_no"));
                    paymentMapper.updateByExampleSelective(payment, example);
                    //更新订单表状态
                    orderCoreService.updateOrder(1, paraMap.get("out_trade_no").toString());
                    response.setResult(WeChatBuildRequest.setXML("SUCCESS", "OK"));
                }
            }
        } else {
            throw new BizException("微信返回结果签名验证失败");
        }
        return response;
    }
}
