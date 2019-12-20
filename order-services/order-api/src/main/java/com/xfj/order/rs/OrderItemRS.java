package com.xfj.order.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.order.dto.OrderDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemRS extends AbstractResponse {

    private String id;

    private String itemId;

    private String orderId;

    private Integer num;

    private String title;

    private BigDecimal price;

    private BigDecimal totalFee;

    private String picPath;

    private OrderDto orderDto;
}
