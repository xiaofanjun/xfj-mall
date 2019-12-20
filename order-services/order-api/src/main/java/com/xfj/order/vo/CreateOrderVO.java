package com.xfj.order.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.order.constant.OrderRetCode;
import com.xfj.order.dto.CartProductDto;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-上午9:49
 */
@Data
public class CreateOrderVO extends AbstractRequest {


    private Long userId;

    private Long addressId;

    private String tel;

    private String userName;

    private String streetName;

    private BigDecimal orderTotal;

    List<CartProductDto> cartProductDtoList;//购物车中的商品列表

    private String uniqueKey; //业务唯一id


    @Override
    public void requestCheck() {
        if (userId == null || addressId == null ||
                StringUtils.isBlank(tel) || StringUtils.isBlank(userName) || StringUtils.isBlank(streetName) || orderTotal == null) {
            throw new ValidateException(OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getCode(), OrderRetCode.REQUISITE_PARAMETER_NOT_EXIST.getMessage());
        }
    }
}
