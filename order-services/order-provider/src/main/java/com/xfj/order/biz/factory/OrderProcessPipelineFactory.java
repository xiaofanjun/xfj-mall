package com.xfj.order.biz.factory;


import com.xfj.order.biz.context.CreateOrderContext;
import com.xfj.order.biz.context.TransHandlerContext;
import com.xfj.order.biz.convert.CreateOrderConvert;
import com.xfj.order.biz.convert.TransConvert;
import com.xfj.order.vo.CreateOrderVO;
import com.xfj.order.biz.handler.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderProcessPipelineFactory extends AbstranctTransPipelineFactory<CreateOrderVO> {

    @Autowired
    private InitOrderHandler initOrderHandler;
    @Autowired
    private ValidateHandler validateHandler;
    @Autowired
    private LogisticalHandler logisticalHandler;
    @Autowired
    private ClearCartItemHandler clearCartItemHandler;
    @Autowired
    private SubStockHandler subStockHandler;
    @Autowired
    private SendMessageHandler sendMessageHandler;


    @Override
    protected TransHandlerContext createContext() {
        CreateOrderContext createOrderContext = new CreateOrderContext();
        return createOrderContext;
    }

    @Override
    protected void doBuild(TransPipeline pipeline) {
        pipeline.addLast(validateHandler);
        pipeline.addLast(subStockHandler);
        pipeline.addLast(initOrderHandler);
        pipeline.addLast(logisticalHandler);
        pipeline.addLast(clearCartItemHandler);
        pipeline.addLast(sendMessageHandler);
    }

    @Override
    protected TransConvert createConvert() { //构建转换器
        return new CreateOrderConvert();
    }
}
