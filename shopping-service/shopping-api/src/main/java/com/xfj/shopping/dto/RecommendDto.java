package com.xfj.shopping.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RecommendDto implements Serializable {

    private String id;

    private String name;

    private Integer type;

    private Integer sortOrder;

    private Integer position;

    private String remark;


}
