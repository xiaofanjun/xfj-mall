package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:09
 **/
@Data
public class QueryMemberVO extends AbstractRequest {

    private Long userId;

    @Override
    public void requestCheck() {
        if (userId == null) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
