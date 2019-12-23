package com.xfj.pay;

import com.xfj.pay.rs.PaymentNotifyRS;
import com.xfj.pay.rs.PaymentRS;
import com.xfj.pay.rs.RefundRS;
import com.xfj.pay.vo.PaymentNotifyVO;
import com.xfj.pay.vo.PaymentVO;
import com.xfj.pay.vo.RefundVO;

public interface PayCoreService {


    /**
     * 执行支付操作
     * @param request
     * @return
     */
    PaymentRS execPay(PaymentVO request);


    /**
     * 支付、退款结果通知处理(等待微信或者支付宝异步通知支付结果）
     * @param request
     * @return
     */
    PaymentNotifyRS paymentResultNotify(PaymentNotifyVO request);

    /**
     * 执行退款
     * @param refundRequest
     * @return
     */
    RefundRS execRefund(RefundVO refundRequest);

}
