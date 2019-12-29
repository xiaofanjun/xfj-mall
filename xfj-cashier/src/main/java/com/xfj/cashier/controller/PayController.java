package com.xfj.cashier.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfj.cashier.form.PayForm;
import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.pay.PayCoreService;
import com.xfj.pay.constants.PayReturnCodeEnum;
import com.xfj.pay.rs.PaymentRS;
import com.xfj.pay.rs.RefundRS;
import com.xfj.pay.vo.PaymentVO;
import com.xfj.pay.vo.RefundVO;
import com.xfj.user.annotation.Anoymous;
import com.xfj.user.intercepter.TokenIntercepter;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;


@Slf4j
@RestController
@RequestMapping("/cashier")
public class PayController {

    @Reference(timeout = 3000, retries = 0, group = "${dubbo-group.name}")
    PayCoreService payCoreService;

    @PostMapping("/pay")
    @Anoymous
    public ResponseData pay(@RequestBody PayForm payForm, HttpServletRequest httpServletRequest) {
        log.info("支付表单数据:{}", payForm);
        PaymentVO request = new PaymentVO();
        String userInfo = (String) httpServletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        Long uid = Long.parseLong(object.get("uid").toString());
        request.setUserId(uid);
        BigDecimal money = payForm.getMoney();
        request.setOrderFee(money);
        request.setPayChannel(payForm.getPayType());
        request.setSubject(payForm.getInfo());
        request.setSpbillCreateIp("115.195.125.164");
        request.setTradeNo(payForm.getOrderId());
        request.setTotalFee(money);
        PaymentRS response = payCoreService.execPay(request);
        if (response.getCode().equals(PayReturnCodeEnum.SUCCESS.getCode())) {
            return new ResponseUtil<>().setData(response.getHtmlStr());
        }
        return new ResponseUtil<>().setErrorMsg(response.getMsg());

    }


    @PostMapping("/refund")
    @Anoymous
    public ResponseData refund(@RequestBody PayForm refundForm, HttpServletRequest httpServletRequest) {
        log.info("订单退款入参:{}", JSON.toJSONString(refundForm));
        RefundVO refundRequest = new RefundVO();
        String userInfo = (String) httpServletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        String uid = object.get("uid").toString();
        refundRequest.setUserId(uid);
        refundRequest.setOrderId(refundForm.getOrderId());
        refundRequest.setRefundAmount(refundForm.getMoney());
        refundRequest.setPayChannel(refundForm.getPayType());
        RefundRS refundResponse = payCoreService.execRefund(refundRequest);
        log.info("订单退款同步返回结果:{}", JSON.toJSONString(refundResponse));
        return new ResponseUtil<>().setData(refundResponse);
    }


}
