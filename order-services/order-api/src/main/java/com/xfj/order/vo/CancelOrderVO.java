package com.xfj.order.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.order.constant.OrderRetCode;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class CancelOrderVO extends AbstractRequest {

    private String orderId;

    @Override
    public void requestCheck() {
        if (StringUtils.isBlank(orderId)) {
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
