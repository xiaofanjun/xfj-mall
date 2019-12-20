package com.xfj.shopping.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.shopping.constants.ShoppingRetCode;
import lombok.Data;

/**
 * Created by mic on 2019/7/23.
 */
@Data
public class DeleteCheckedItemVO extends AbstractRequest {

    private Long userId;

    @Override
    public void requestCheck() {
        if (userId == null) {
            throw new ValidateException(ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), ShoppingRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());

        }
    }
}
