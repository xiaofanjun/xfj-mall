package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/27 21:07
 **/
@Data
public class CheckAuthVO extends AbstractRequest {
    private String token;

    @Override
    public void requestCheck() {
        if (StringUtils.isBlank(token)) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }

    public void setToken(String token) {
        this.token = token;
    }
}
