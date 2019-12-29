package com.xfj.shopping.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.Table;
import java.math.BigDecimal;

@Data
@Table(name = "tb_panel_content_item")
public class PanelContentItem extends BaseDO<String> {

    private String panelId;

    private Integer type;

    private String productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;

    private String productName;

    private BigDecimal salePrice;

    private String subTitle;
}
