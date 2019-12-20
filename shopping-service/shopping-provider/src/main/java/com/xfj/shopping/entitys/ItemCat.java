package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_item_cat")
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemCat extends BaseDO<String> {
    private Long parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Boolean isParent;

    private String icon;

    private String remark;

    private Date created;

    private Date updated;
}