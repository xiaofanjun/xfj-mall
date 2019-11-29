package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/25 20:29
 **/
@Data
public class KaptchaCodeVO extends AbstractRequest {

    private String uuid;

    private String code;

    @Override
    public void requestCheck() {
        if (StringUtils.isBlank(uuid) || StringUtils.isBlank(code)) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
