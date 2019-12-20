package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

@Table(name = "tb_item")
@EqualsAndHashCode(callSuper = true)
@Data
public class Item extends BaseDO<String> {
    private String title;

    private String sellPoint;

    private BigDecimal price;

    private Integer num;

    private Integer limitNum;

    private String image;

    private Long cid;

    private Integer status;

    private Date created;

    private Date updated;

    @Transient
    private String imageBig;


    public void setSellPoint(String sellPoint) {
        this.sellPoint = sellPoint == null ? null : sellPoint.trim();
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public String[] getImages() {
        if (image != null && !"".equals(image)) {
            String[] strings = image.split(",");
            return strings;
        }
        return null;
    }

    public String getImageBig() {
        if (image != null && !"".equals(image)) {
            String[] strings = image.split(",");
            return strings[0];
        }
        return null;
    }

}