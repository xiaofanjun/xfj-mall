package com.xfj.pay.biz.payment.validator;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.order.OrderQueryService;
import com.xfj.pay.biz.abs.BaseValidator;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Service;

@Service("wechatPaymentValidator")
public class WechatPaymentValidator extends BaseValidator {
     @Reference(timeout=3000)
     OrderQueryService orderQueryService;
    @Override
    public void specialValidate(AbstractRequest request) {
        commonValidate(request,orderQueryService);
    }
}
