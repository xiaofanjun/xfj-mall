package com.xfj.user.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import lombok.Data;


/**
 * @Author ZQ
 * @Description
 **/
@Data
public class UpdateMemberVO extends AbstractRequest {
    private static final long serialVersionUID = -2337060153713102164L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 注册手机号
     */
    private String phone;
    /**
     * 注册邮箱
     */
    private String email;
    /**
     * 性别
     */
    private String sex;
    /**
     * 地址
     */
    private String address;
    /**
     * 头像
     */
    private String file;
    /**
     * 描述
     */
    private String description;
    private Integer points;
    private Long balance;

    private String token;

    @Override
    public void requestCheck() {
        if (null == id) {
            throw new ValidateException(
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getCode(),
                    SysRetCodeConstants.REQUEST_CHECK_FAILURE.getMessage());
        }
    }
}
