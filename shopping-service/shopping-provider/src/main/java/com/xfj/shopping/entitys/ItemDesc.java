package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "tb_item_desc")
@Data
public class ItemDesc extends BaseDO<String> {
    private Date created;

    private Date updated;

    private String itemDesc;
}