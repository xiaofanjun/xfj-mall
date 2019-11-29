package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/27 21:07
 **/
@Data
public class DeleteAddressVO extends AbstractRequest {
    private Long addressId;

    @Override
    public void requestCheck() {
        if (addressId == null) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
