package com.xfj.user.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * @Author ZQ
 * @Description 用户校验表
 * @Date 2019/12/3 11:17
 **/
@Table(name = "tb_user_verify")
@Data
@EqualsAndHashCode(callSuper = true)
public class UserVerify extends BaseDO<String> {

    private static final long serialVersionUID = 1L;
    private String username;

    //注册时生成的唯一号
    private String uuid;

    //注册时间
    @Column(name = "register_date")
    private Date registerDate;

    //是否验证过期
    @Column(name = "is_verify")
    private String isVerify;

    //是否过期
    @Column(name = "is_expire")
    private String isExpire;


}