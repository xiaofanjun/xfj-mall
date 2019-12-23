package com.xfj.pay.biz.abs;


import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.BizException;
import com.xfj.pay.vo.PaymentNotifyVO;

public interface Payment {

    /**
     * 发起交易执行的过程
     * @param request
     * @return
     * @throws BizException
     */
    <T extends AbstractResponse> T process(AbstractRequest request) throws BizException;

    /**
     * 完成交易结果的处理
     * @param request
     * @return
     * @throws BizException
     */
    <T extends AbstractResponse> T completePayment(PaymentNotifyVO request) throws BizException;
}
