package com.xfj.user.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "t_test")
@Data
@EqualsAndHashCode(callSuper = true)
public class Test extends BaseDO<String> {
    private String name;
    private int age;
}