package com.xfj.shopping.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.shopping.dto.ProductDetailDto;
import lombok.Data;


@Data
public class ProductDetailRS extends AbstractResponse {
    private ProductDetailDto productDetailDto;
}
