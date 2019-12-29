package com.xfj.shopping.vo;

import com.xfj.commons.result.AbstractRequest;
import lombok.Data;


@Data
public class AllProductVO extends AbstractRequest {

    private Integer page;
    private Integer size;
    private String sort;
    private String cid;
    private Integer priceGt;
    private Integer priceLte;

    @Override
    public void requestCheck() {
        if (page <= 0) {
            setPage(1);
        }
    }
}
