package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author ZQ
 * @Description 商品描述表
 * @Date 2019/12/20 19:36
 * @Param
 * @return
 **/
@Table(name = "tb_item_desc")
@Data
public class ItemDesc extends BaseDO<String> {

    private String itemDesc;
}