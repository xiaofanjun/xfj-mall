package com.xfj.user.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Table(name = "tb_address")
@Data
@EqualsAndHashCode(callSuper = true)
public class Address extends BaseDO<String> {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "user_name")
    private String userName;

    private String tel;

    @Column(name = "street_name")
    private String streetName;

    @Column(name = "is_default")
    private Integer isDefault;


}