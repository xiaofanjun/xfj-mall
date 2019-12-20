package com.xfj.shopping.vo;

import com.xfj.commons.result.AbstractRequest;
import lombok.Data;


@Data
public class AddCartVO extends AbstractRequest {

    private String userId;
    private String itemId;
    private Integer num;

    @Override
    public void requestCheck() {

    }
}
