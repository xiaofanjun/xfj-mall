package com.xfj.order.converter;/**
 * Created by mic on 2019/7/31.
 */

import com.xfj.order.entitys.Order;
import com.xfj.order.entitys.OrderItem;
import com.xfj.order.entitys.OrderShipping;
import com.xfj.order.dto.*;
import com.xfj.order.rs.OrderDetailRS;
import com.xfj.order.rs.OrderItemRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;


@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({})
    OrderDetailRS order2res(Order order);

    @Mappings({})
    OrderDetailInfo order2detail(Order order);

    @Mappings({})
    OrderItemDto item2dto(OrderItem item);

    @Mappings({})
    OrderShippingDto shipping2dto(OrderShipping shipping);


    List<OrderItemDto> item2dto(List<OrderItem> items);

    @Mappings({})
    OrderItemRS item2res(OrderItem orderItem);

    @Mappings({})
    OrderDto order2dto(Order order);
}
