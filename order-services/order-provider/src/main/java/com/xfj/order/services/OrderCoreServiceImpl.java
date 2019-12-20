package com.xfj.order.services;/**
 * Created by mic on 2019/7/30.
 */

import com.xfj.order.OrderCoreService;
import com.xfj.order.biz.TransOutboundInvoker;
import com.xfj.order.biz.context.AbsTransHandlerContext;
import com.xfj.order.biz.factory.OrderProcessPipelineFactory;
import com.xfj.order.constant.OrderRetCode;
import com.xfj.order.constants.OrderConstants;
import com.xfj.order.entitys.Order;
import com.xfj.order.mapper.OrderItemMapper;
import com.xfj.order.mapper.OrderMapper;
import com.xfj.order.mapper.OrderShippingMapper;
import com.xfj.order.rs.CancelOrderRS;
import com.xfj.order.rs.CreateOrderRS;
import com.xfj.order.rs.DeleteOrderRS;
import com.xfj.order.utils.ExceptionProcessorUtils;
import com.xfj.order.vo.CancelOrderVO;
import com.xfj.order.vo.CreateOrderVO;
import com.xfj.order.vo.DeleteOrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;


@Slf4j
@Service(cluster = "failfast")
public class OrderCoreServiceImpl implements OrderCoreService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    OrderShippingMapper orderShippingMapper;

    @Autowired
    OrderProcessPipelineFactory orderProcessPipelineFactory;

    @Autowired
    OrderCoreService orderCoreService;


    /**
     * 创建订单的处理流程
     *
     * @param request
     * @return
     */
    @Override
    public CreateOrderRS createOrder(CreateOrderVO request) {
        CreateOrderRS response = new CreateOrderRS();
        try {
            TransOutboundInvoker invoker = orderProcessPipelineFactory.build(request);
            invoker.start(); //启动流程（pipeline来处理）
            AbsTransHandlerContext context = invoker.getContext();
            response = (CreateOrderRS) context.getConvert().convertCtx2Respond(context);
        } catch (Exception e) {
            log.error("OrderCoreServiceImpl.createOrder Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * 取消订单
     *
     * @param request
     * @return
     */
    @Override
    public CancelOrderRS cancelOrder(CancelOrderVO request) {
        CancelOrderRS response = new CancelOrderRS();
        try {
            Order order = new Order();
            order.setId(request.getOrderId());
            order.setStatus(OrderConstants.ORDER_STATUS_TRANSACTION_CANCEL);
            order.setCloseTime(new Date());
            int num = orderMapper.updateByPrimaryKey(order);
            log.info("cancelOrder,effect Row:" + num);
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("OrderCoreServiceImpl.cancelOrder Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }


    /**
     * 删除订单
     *
     * @param request
     * @return
     */
    @Override
    public DeleteOrderRS deleteOrder(DeleteOrderVO request) {
        DeleteOrderRS response = new DeleteOrderRS();
        try {
            request.requestCheck();
            deleteOrderWithTransaction(request);
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("OrderCoreServiceImpl.deleteOrder Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }


    @Override
    public void updateOrder(Integer status, String orderId) {
        Order order = new Order();
        order.setId(orderId);
        order.setStatus(status);
        orderMapper.updateByPrimaryKey(order);
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteOrderWithTransaction(DeleteOrderVO request) {
        orderMapper.deleteByPrimaryKey(request.getOrderId());
        Example example = new Example(Order.class);
        example.createCriteria().andEqualTo("orderId", request.getOrderId());
        orderItemMapper.deleteByExample(example);
        orderShippingMapper.deleteByPrimaryKey(request.getOrderId());
    }
}
