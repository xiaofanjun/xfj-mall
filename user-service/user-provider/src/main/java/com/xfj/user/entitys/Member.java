package com.xfj.user.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 * @Author ZQ
 * @Description 用户会员表(前端)
 * @Date 2019/12/3 11:16
 **/
@Table(name = "tb_member")
@Data
@EqualsAndHashCode(callSuper = true)
public class Member extends BaseDO<String> {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码，加密存储
     */
    private String password;

    /**
     * 注册手机号
     */
    private String phone;

    /**
     * 注册邮箱
     */
    private String email;

    private String sex;

    private String address;

    private Integer state;

    /**
     * 头像
     */
    private String file;

    private String description;

    /**
     * 积分
     */
    private Integer points;

    /**
     * 余额
     */
    private Double balance;

    /**
     * 是否激活,默认值N，激活Y
     */
    @Column(name = "isverified")
    private String isVerified;


}