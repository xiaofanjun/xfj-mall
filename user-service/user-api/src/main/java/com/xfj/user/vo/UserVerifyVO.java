package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:11
 **/
@Data
public class UserVerifyVO extends AbstractRequest {

    private String userName;
    /**
     * 注册时生产的唯一序号
     */
    private String uuid;

    @Override
    public void requestCheck() {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(uuid)) {
            throw new ValidateException(SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(), SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
