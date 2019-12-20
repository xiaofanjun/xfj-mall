package com.xfj.order.biz.callback;


import com.xfj.order.biz.context.TransHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SendEmailCallback implements TransCallback {

    @Override
    public void onDone(TransHandlerContext context) {
        log.info("订单下单成功后，会发送邮件");
    }
}
