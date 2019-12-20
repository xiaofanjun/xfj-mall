package com.xfj.order.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.order.constant.OrderRetCode;
import lombok.Data;

@Data
public class OrderListVO extends AbstractRequest {

    private Long userId;
    private Integer page;
    private Integer size;
    private String sort;

    @Override
    public void requestCheck() {
        if (page == null || page < 1) {
            page = 1;
        }
        if (size == null || size < 1) {
            size = 5;
        }
        if (userId == null) {
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());

        }
    }
}
