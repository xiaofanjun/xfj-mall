package com.xfj.shopping.vo;


import com.xfj.commons.result.AbstractRequest;
import lombok.Data;


@Data
public class AllProductCateVO extends AbstractRequest {
    private String sort;

    @Override
    public void requestCheck() {

    }
}
