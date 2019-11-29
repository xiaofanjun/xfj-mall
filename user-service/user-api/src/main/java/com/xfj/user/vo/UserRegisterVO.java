package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author ZQ
 * @Description 用户注册
 * @Date 2019/11/27 20:52
 **/
@Data
public class UserRegisterVO extends AbstractRequest {

    private String userName;
    private String userPwd;
    private String email;
    private String captcha;

    @Override
    public void requestCheck() {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(userPwd)) {
            throw new ValidateException(SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(), SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
