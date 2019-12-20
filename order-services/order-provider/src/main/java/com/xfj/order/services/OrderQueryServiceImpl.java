package com.xfj.order.services;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfj.order.OrderQueryService;
import com.xfj.order.constant.OrderRetCode;
import com.xfj.order.converter.OrderConverter;
import com.xfj.order.entitys.Order;
import com.xfj.order.entitys.OrderShipping;
import com.xfj.order.mapper.OrderItemMapper;
import com.xfj.order.mapper.OrderMapper;
import com.xfj.order.mapper.OrderShippingMapper;
import com.xfj.order.rs.OrderCountRS;
import com.xfj.order.rs.OrderDetailRS;
import com.xfj.order.rs.OrderItemRS;
import com.xfj.order.rs.OrderListRS;
import com.xfj.order.utils.ExceptionProcessorUtils;
import com.xfj.order.entitys.OrderItem;
import com.xfj.order.dto.*;
import com.xfj.order.vo.OrderCountVO;
import com.xfj.order.vo.OrderDetailVO;
import com.xfj.order.vo.OrderItemVO;
import com.xfj.order.vo.OrderListVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderQueryServiceImpl implements OrderQueryService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    OrderShippingMapper orderShippingMapper;

    @Autowired
    OrderConverter orderConverter;

    @Override
    public OrderCountRS orderCount(OrderCountVO request) {
        OrderCountRS response=new OrderCountRS();
        try {
            Long count = orderMapper.countAll();
            response.setCount(count.intValue());
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        }catch (Exception e){
            log.error("OrderQueryServiceImpl.orderCount occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    /**
     * 查询历史订单列表
     * @param request
     * @return
     * @author GP17513-成都-Rigel
     */
    @Override
    public OrderListRS orderList(OrderListVO request) {
        OrderListRS response = new OrderListRS();
        try{
            request.requestCheck();
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
            PageHelper.startPage(request.getPage(),request.getSize());
            Example example = new Example(Order.class);
            example.createCriteria().andEqualTo("userId",request.getUserId());
            List<Order> orderList = orderMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(orderList)){
                response.setTotal(0L);
                response.setDetailInfoList(new ArrayList<>());
                return response;
            }
            List<OrderDetailInfo> infos = new ArrayList<>();
            PageInfo<Order> pageInfo=new PageInfo<>(orderList);
            response.setTotal(pageInfo.getTotal());
            orderList.forEach( order -> {
                OrderDetailInfo info = orderConverter.order2detail(order);
                List<OrderItem> list =  orderItemMapper.queryByOrderId(order.getId());
//                OrderItemExample itemExample=new OrderItemExample();
//                itemExample.createCriteria().andOrderIdEqualTo(order.getOrderId());
//                List<OrderItem> list=orderItemMapper.selectByExample(itemExample);
                OrderShipping orderShipping=orderShippingMapper.selectByPrimaryKey(order.getId());
                info.setOrderItemDto(orderConverter.item2dto(list));
                info.setOrderShippingDto(orderConverter.shipping2dto(orderShipping));
                infos.add(info);
            });
            response.setDetailInfoList(infos);
        }catch (Exception e){
            log.info("OrderQueryServiceImpl.orderList occur Exception: {}" , e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    /**
     * 查询订单明细
     * @param request
     * @return
     */
    @Override
    public OrderDetailRS orderDetail(OrderDetailVO request) {
        OrderDetailRS response=new OrderDetailRS();
        try{
            request.requestCheck();
            Order order=orderMapper.selectByPrimaryKey(request.getOrderId());
//            OrderItemExample example=new OrderItemExample();
//            OrderItemExample.Criteria criteria=example.createCriteria();
//            criteria.andOrderIdEqualTo(order.getOrderId());
//            List<OrderItem> list=orderItemMapper.selectByExample(example);
            List<OrderItem> list =  orderItemMapper.queryByOrderId(order.getId());
            OrderShipping orderShipping=orderShippingMapper.selectByPrimaryKey(order.getId());
            response=orderConverter.order2res(order);
            response.setOrderItemDto(orderConverter.item2dto(list));
            response.setOrderShippingDto(orderConverter.shipping2dto(orderShipping));
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
            return response;
        }catch (Exception e){
            log.error("OrderQueryServiceImpl.orderDetail occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }

    @Override
    public OrderItemRS orderItem(OrderItemVO request) {
        OrderItemRS response = new OrderItemRS();
        try {
            request.requestCheck();
            OrderItem orderItem = orderItemMapper.selectByPrimaryKey(request.getOrderItemId());
            response = orderConverter.item2res(orderItem);
            Order order = orderMapper.selectByPrimaryKey(orderItem.getOrderId());
            response.setOrderDto(orderConverter.order2dto(order));
            response.setCode(OrderRetCode.SUCCESS.getCode());
            response.setMsg(OrderRetCode.SUCCESS.getMessage());
        } catch (Exception e){
            log.error("OrderQueryServiceImpl.orderItem occur Exception :" +e);
            ExceptionProcessorUtils.wrapperHandlerException(response,e);
        }
        return response;
    }
}
