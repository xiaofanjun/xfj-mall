package com.xfj.shopping.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class PanelContentItemDto implements Serializable {

    private static final long serialVersionUID = -6930891177670846634L;
    private String id;

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

    private String SubTitle;
}
