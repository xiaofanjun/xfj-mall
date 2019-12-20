package com.xfj.shopping.vo;
import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.shopping.constants.ShoppingRetCode;
import lombok.Data;

@Data
public class CartListByIdVO extends AbstractRequest {
    private Long userId;

    @Override
    public void requestCheck() {
        if (userId == null || userId.intValue() == 0) {
            throw new ValidateException(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
