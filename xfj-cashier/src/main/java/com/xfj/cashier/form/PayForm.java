package com.xfj.cashier.form;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PayForm {

    private String nickName;
    private BigDecimal money;
    private String info;
    private String orderId;
    private String payType;
}
