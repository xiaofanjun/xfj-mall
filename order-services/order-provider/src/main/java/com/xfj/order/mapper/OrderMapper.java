package com.xfj.order.mapper;

import com.xfj.commons.base.dao.BaseDao;
import com.xfj.order.entitys.Order;

public interface OrderMapper extends BaseDao<Order> {
    Long countAll();
}