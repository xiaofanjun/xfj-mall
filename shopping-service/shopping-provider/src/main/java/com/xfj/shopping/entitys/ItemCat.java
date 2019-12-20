package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;

/**
 * @Author ZQ
 * @Description 商品类目
 * @Date 2019/12/20 19:35
 * @Param
 * @return
 **/
@Table(name = "tb_item_cat")
@Data
@EqualsAndHashCode(callSuper = true)
public class ItemCat extends BaseDO<String> {
    private String parentId;

    private String name;

    private Integer status;

    private Integer sortOrder;

    private Boolean isParent;

    private String icon;

    private String remark;

}