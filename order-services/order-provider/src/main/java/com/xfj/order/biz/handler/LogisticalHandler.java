package com.xfj.order.biz.handler;

import com.xfj.commons.tool.exception.BizException;
import com.xfj.order.biz.context.CreateOrderContext;
import com.xfj.order.biz.context.TransHandlerContext;
import com.xfj.order.constant.OrderRetCode;
import com.xfj.order.entitys.OrderShipping;
import com.xfj.order.mapper.OrderShippingMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class LogisticalHandler extends AbstractTransHandler {

    @Autowired
    OrderShippingMapper orderShippingMapper;

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public boolean handle(TransHandlerContext context) {
        log.info("begin LogisticalHandler :context:" + context);
        try {
            CreateOrderContext createOrderContext = (CreateOrderContext) context;
            OrderShipping orderShipping = new OrderShipping();
            orderShipping.setId(String.valueOf(createOrderContext.getOrderId()));
            orderShipping.setReceiverName(createOrderContext.getUserName());
            orderShipping.setReceiverAddress(createOrderContext.getStreetName());
            orderShipping.setReceiverPhone(createOrderContext.getTel());
            orderShipping.setCreatedate(new Date());
            orderShipping.setModifydate(new Date());
            orderShippingMapper.insert(orderShipping);
        } catch (Exception e) {
            throw new BizException(OrderRetCode.SHIPPING_DB_SAVED_FAILED.getCode(), OrderRetCode.SHIPPING_DB_SAVED_FAILED.getMessage());
        }
        return true;
    }
}
