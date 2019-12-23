package com.xfj.order.vo;


import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.order.constant.OrderRetCode;
import lombok.Data;

@Data
public class OrderCountVO extends AbstractRequest {

    private String userId;

    @Override
    public void requestCheck() {
        if (userId == null) {
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
