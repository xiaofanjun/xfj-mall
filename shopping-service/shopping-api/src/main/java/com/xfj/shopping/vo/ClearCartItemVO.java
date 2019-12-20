package com.xfj.shopping.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.shopping.constants.ShoppingRetCode;
import lombok.Data;

import java.util.List;


@Data
public class ClearCartItemVO extends AbstractRequest {

    private Long userId;
    private List<Long> productIds;

    @Override
    public void requestCheck() {
        if (userId == null) {
            throw new ValidateException(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
