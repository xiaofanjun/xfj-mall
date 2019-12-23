package com.xfj.pay.biz.payment;

import com.alibaba.fastjson.JSON;
import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.BizException;
import com.xfj.order.OrderCoreService;
import com.xfj.pay.biz.payment.channel.alipay.AlipayBuildRequest;
import com.xfj.pay.biz.payment.channel.alipay.AlipayNotify;
import com.xfj.pay.biz.payment.constants.AliPaymentConfig;
import com.xfj.pay.biz.payment.constants.PayResultEnum;
import com.xfj.pay.biz.payment.context.AliPaymentContext;
import com.xfj.pay.entitys.Payment;
import com.xfj.pay.mapper.PaymentMapper;
import com.xfj.pay.biz.abs.BasePayment;
import com.xfj.pay.biz.abs.Context;
import com.xfj.pay.biz.abs.Validator;
import com.xfj.pay.constants.PayChannelEnum;
import com.xfj.pay.constants.PayReturnCodeEnum;
import com.xfj.pay.rs.PaymentNotifyRS;
import com.xfj.pay.rs.PaymentRS;
import com.xfj.pay.vo.PaymentNotifyVO;
import com.xfj.pay.vo.PaymentVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

@Slf4j
@Service("aliPayment")
public class AliPayment extends BasePayment {

    @Resource(name = "aliPaymentValidator")
    private Validator validator;

    @Autowired
    AliPaymentConfig aliPaymentConfig;

    @Autowired
    PaymentMapper paymentMapper;

    @Reference(timeout = 30000)
    OrderCoreService orderCoreService;

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public Context createContext(AbstractRequest request) {
        AliPaymentContext aliPaymentContext = new AliPaymentContext();
        PaymentVO paymentRequest = (PaymentVO) request;
        aliPaymentContext.setSubject(paymentRequest.getSubject());
        aliPaymentContext.setOutTradeNo(paymentRequest.getTradeNo());
        aliPaymentContext.setTotalFee(paymentRequest.getTotalFee());
        aliPaymentContext.setUserId(paymentRequest.getUserId());
        return aliPaymentContext;
    }

    @Override
    public void prepare(AbstractRequest request, Context context) throws BizException {
        super.prepare(request, context);
        SortedMap sParaTemp = context.getsParaTemp();
        AliPaymentContext aliPaymentContext = (AliPaymentContext) context;
        sParaTemp.put("partner", aliPaymentConfig.getAli_partner());
        sParaTemp.put("service", aliPaymentConfig.getAli_service());
        //sParaTemp.put("seller_email", aliPaymentConfig.getSeller_email());
        sParaTemp.put("seller_id", aliPaymentConfig.getSeller_id());
        sParaTemp.put("payment_type", "1");
        sParaTemp.put("it_b_pay", aliPaymentConfig.getIt_b_pay());
        sParaTemp.put("notify_url", aliPaymentConfig.getNotify_url());
        sParaTemp.put("return_url", aliPaymentConfig.getReturn_url());
        sParaTemp.put("out_trade_no", aliPaymentContext.getOutTradeNo());
        sParaTemp.put("subject", aliPaymentContext.getSubject());
        sParaTemp.put("total_fee", aliPaymentContext.getTotalFee());
        aliPaymentContext.setsParaTemp(sParaTemp);
    }


    @Override
    public PaymentRS generalProcess(AbstractRequest request, Context context) throws BizException {
        Map<String, Object> sPara = AlipayBuildRequest.buildRequestParam(context.getsParaTemp(), aliPaymentConfig);
        log.info("支付宝支付组装请求参数:{}", JSON.toJSONString(sPara));
        String strPara = AlipayBuildRequest.buildRequest(sPara, "get", "确认", aliPaymentConfig);
        log.info("支付宝支付同步返回的表单:{}", strPara);
        PaymentRS response = new PaymentRS();
        response.setCode(PayReturnCodeEnum.SUCCESS.getCode());
        response.setMsg(PayReturnCodeEnum.SUCCESS.getMsg());
        response.setHtmlStr(strPara);
        return response;
    }


    @Override
    public void afterProcess(AbstractRequest request, AbstractResponse respond, Context context) throws BizException {
        log.info("Alipayment begin - afterProcess -request:" + request + "\n response:" + respond);
        PaymentVO paymentRequest = (PaymentVO) request;
        //插入支付记录表
        Payment payment = new Payment();
        payment.setCreatedate(new Date());
        //订单号
        payment.setOrderId(paymentRequest.getTradeNo());
        payment.setCreatedate(new Date());
        BigDecimal amount = paymentRequest.getOrderFee();
        payment.setOrderAmount(amount);
        payment.setPayerAmount(amount);
        payment.setPayerUid(paymentRequest.getUserId());
        payment.setPayerName("");//TODO
        payment.setPayWay(paymentRequest.getPayChannel());
        payment.setProductName(paymentRequest.getSubject());
        payment.setStatus(PayResultEnum.TRADE_PROCESSING.getCode());//
        payment.setRemark("支付宝支付");
        payment.setModifydate(new Date());
        paymentMapper.insert(payment);
    }

    @Override
    public String getPayChannel() {
        return PayChannelEnum.ALI_PAY.getCode();
    }

    /**
     * 回调通知处理
     *
     * @param request
     * @return
     * @throws BizException
     */
    @Override
    public AbstractResponse completePayment(PaymentNotifyVO request) {
        request.requestCheck();
        Map requestParams = request.getResultMap();
        Map<String, Object> params = new HashMap<>(requestParams.size());
        requestParams.forEach((key, value) -> {
            String[] values = (String[]) value;
            params.put((String) key, StringUtils.join(values, ","));
        });

        PaymentNotifyRS response = new PaymentNotifyRS();
        String orderId = params.get("out_trade_no").toString();
        //验证
        if (AlipayNotify.verify(params, aliPaymentConfig)) {
            Payment payment = new Payment();
            //TRADE_FINISH(支付完成)、TRADE_SUCCESS(支付成功)、FAIL(支付失败)
            String tradeStatus = params.get("trade_status").toString();
            if ("TRADE_SUCCESS".equals(tradeStatus)) {
                //更新支付表
                payment.setPayNo(params.get("trade_no").toString());
                payment.setStatus(PayResultEnum.TRADE_SUCCESS.getCode());
                payment.setPaySuccessTime((Date) params.get("gmt_payment"));
                Example example = new Example(Payment.class);
                example.createCriteria().andEqualTo("orderId", orderId);
                paymentMapper.updateByExampleSelective(payment, example);
                //更新订单表状态
                orderCoreService.updateOrder(1, orderId);
                response.setResult("success");
                return response;
            } else if ("TRADE_FINISH".equals(tradeStatus)) {
                payment.setStatus(PayResultEnum.TRADE_FINISHED.getCode());
                paymentMapper.updateByExampleSelective(payment, orderId);
                //更新订单表状态
                orderCoreService.updateOrder(1, orderId);
                response.setResult("success");
            } else if ("FAIL".equals(tradeStatus)) {
                payment.setStatus(PayResultEnum.FAIL.getCode());
                paymentMapper.updateByExampleSelective(payment, orderId);
                response.setResult("success");
            } else {
                response.setResult("fail");
            }
        } else {
            throw new BizException("支付宝支付验签失败");
        }
        return response;
    }
}
