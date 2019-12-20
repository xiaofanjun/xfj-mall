package com.xfj.user.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * @CLassNmae SystemConfig
 * @Description 系统相关配置表
 * @Author ZQ
 * @Date 2019/12/19 10:08
 * @Version 1.0
 **/
@Table(name = "t_system_config")
@Data
@EqualsAndHashCode(callSuper = true)
public class SystemConfig extends BaseDO<String> {
    //模块
    private String module;
    //模块描述
    private String moduleDes;
    //模块描述对应的关键字
    private String moduleKey;
    //模块描述vue
    private String moduleValue;
}