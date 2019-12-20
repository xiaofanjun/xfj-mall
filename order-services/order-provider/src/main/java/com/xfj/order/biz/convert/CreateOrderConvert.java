package com.xfj.order.biz.convert;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.result.AbstractResponse;
import com.xfj.order.biz.context.CreateOrderContext;
import com.xfj.order.biz.context.TransHandlerContext;
import com.xfj.order.constant.OrderRetCode;
import com.xfj.order.vo.CreateOrderVO;
import com.xfj.order.rs.CreateOrderRS;

public class CreateOrderConvert implements TransConvert{

    @Override
    public TransHandlerContext convertRequest2Ctx(AbstractRequest req, TransHandlerContext context) {
        CreateOrderVO createOrderRequest=(CreateOrderVO)req;
        CreateOrderContext createOrderContext=(CreateOrderContext) context;
        createOrderContext.setAddressId(createOrderRequest.getAddressId());
        createOrderContext.setCartProductDtoList(createOrderRequest.getCartProductDtoList());
        createOrderContext.setOrderTotal(createOrderRequest.getOrderTotal());
        createOrderContext.setStreetName(createOrderRequest.getStreetName());
        createOrderContext.setTel(createOrderRequest.getTel());
        createOrderContext.setUserId(createOrderRequest.getUserId());
        createOrderContext.setUserName(createOrderRequest.getUserName());
        createOrderContext.setUniqueKey(createOrderRequest.getUniqueKey());
        return createOrderContext;
    }

    @Override
    public AbstractResponse convertCtx2Respond(TransHandlerContext ctx) {
        CreateOrderContext createOrderContext=(CreateOrderContext) ctx;
        CreateOrderRS createOrderResponse=new CreateOrderRS();
        createOrderResponse.setOrderId(createOrderContext.getOrderId());
        createOrderResponse.setCode(OrderRetCode.SUCCESS.getCode());
        createOrderResponse.setMsg(OrderRetCode.SUCCESS.getMessage());
        return createOrderResponse;
    }
}
