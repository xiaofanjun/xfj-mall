package com.xfj.order.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

@Data
public class CreateOrderRS extends AbstractResponse {

    private String orderId;
}
