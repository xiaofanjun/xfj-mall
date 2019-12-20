package com.xfj.shopping.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.shopping.dto.ProductCateDto;
import lombok.Data;

import java.util.List;

@Data
public class AllProductCateRS extends AbstractResponse {
    private List<ProductCateDto> productCateDtoList;
}
