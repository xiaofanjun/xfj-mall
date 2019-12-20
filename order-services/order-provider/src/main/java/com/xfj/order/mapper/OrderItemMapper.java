package com.xfj.order.mapper;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.order.entitys.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderItemMapper extends BaseDao<OrderItem> {
    List<OrderItem> queryByOrderId(@Param("orderId") String orderId);
    void updateStockStatus(@Param("status") Integer status,@Param("orderId") String orderId);
}