package com.xfj.shopping.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.shopping.dto.ProductDto;
import lombok.Data;

import java.util.List;


@Data
public class AllProductRS extends AbstractResponse {

    private List<ProductDto> productDtoList;

    private Long total;
}
