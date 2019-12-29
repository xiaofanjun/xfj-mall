package com.xfj.shopping.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PanelContentDto implements Serializable {

    private static final long serialVersionUID = -1584205373584476401L;
    private String id;

    private String panelId;

    private Integer type;

    private String productId;

    private Integer sortOrder;

    private String fullUrl;

    private String picUrl;

    private String picUrl2;

    private String picUrl3;
}
