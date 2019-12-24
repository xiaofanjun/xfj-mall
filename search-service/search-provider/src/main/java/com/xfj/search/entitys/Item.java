package com.xfj.search.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.ToString;

import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_item")
@Data
@ToString
public class Item extends BaseDO<String> {
    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String image;

    private String cid;

    private Integer status;
}