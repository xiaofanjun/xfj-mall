package com.xfj.shopping.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.shopping.dto.CartProductDto;
import lombok.Data;

import java.util.List;

@Data
public class CartListByIdRS extends AbstractResponse {

    private List<CartProductDto> cartProductDtos;
}
