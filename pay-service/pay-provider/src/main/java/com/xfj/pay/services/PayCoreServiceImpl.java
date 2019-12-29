package com.xfj.pay.services;

import com.alibaba.fastjson.JSON;
import com.xfj.pay.biz.abs.BasePayment;
import com.xfj.pay.rs.PaymentNotifyRS;
import com.xfj.pay.rs.PaymentRS;
import com.xfj.pay.rs.RefundRS;
import com.xfj.pay.utils.ExceptionProcessorUtils;
import com.xfj.pay.PayCoreService;
import com.xfj.pay.vo.PaymentNotifyVO;
import com.xfj.pay.vo.PaymentVO;
import com.xfj.pay.vo.RefundVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service(cluster = "failover", group = "${dubbo-group.name}")
public class PayCoreServiceImpl implements PayCoreService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentRS execPay(PaymentVO request) {

        PaymentRS paymentResponse = new PaymentRS();
        try {
            paymentResponse = BasePayment.paymentMap.get(request.getPayChannel()).process(request);
        } catch (Exception e) {
            log.error("PayCoreServiceImpl.execPay Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(paymentResponse, e);
        }
        return paymentResponse;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public PaymentNotifyRS paymentResultNotify(PaymentNotifyVO request) {
        log.info("paymentResultNotify request:{}", JSON.toJSONString(request));
        PaymentNotifyRS response = new PaymentNotifyRS();
        try {
            response = BasePayment.paymentMap.get
                    (request.getPayChannel()).completePayment(request);

        } catch (Exception e) {
            log.error("paymentResultNotify occur exception:" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        } finally {
            log.info("paymentResultNotify return result:{}", JSON.toJSONString(response));
        }
        return response;
    }

    /**
     * 执行退款
     *
     * @param refundRequest
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public RefundRS execRefund(RefundVO refundRequest) {
        RefundRS refundResponse = new RefundRS();
        try {
            refundResponse = BasePayment.paymentMap.get(refundRequest.getPayChannel()).process(refundRequest);
        } catch (Exception e) {
            log.error("PayCoreServiceImpl.execRefund Occur Exception :{}", e);
            ExceptionProcessorUtils.wrapperHandlerException(refundResponse, e);
        }
        return refundResponse;
    }
}
