package com.xfj.order.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.order.dto.OrderItemDto;
import com.xfj.order.dto.OrderShippingDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDetailRS extends AbstractResponse {

    private String orderId;

    private BigDecimal payment;

    private Integer paymentType;

    private BigDecimal postFee;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Date paymentTime;

    private Date consignTime;

    private Date endTime;

    private Date closeTime;

    private String shippingName;

    private String shippingCode;

    private Long userId;

    private String buyerMessage;

    private String buyerNick;

    private Integer buyerComment;

    private List<OrderItemDto> orderItemDto;

    private OrderShippingDto orderShippingDto;
}
