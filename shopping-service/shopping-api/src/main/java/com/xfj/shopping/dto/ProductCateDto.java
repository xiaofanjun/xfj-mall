package com.xfj.shopping.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class ProductCateDto implements Serializable {
    private String id;
    private String name;
    private String parentId;
    private Boolean isParent;
    private String iconUrl;
}
