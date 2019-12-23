package com.xfj.coupon.vo;

import com.xfj.commons.result.AbstractRequest;
import com.xfj.coupon.dto.OrderItemDto;
import lombok.Data;

import java.util.List;

/**
 * Created by oahnus on 2019/8/19
 * 23:54.
 *
 * TODO 改名字。。。
 */
@Data
public class UseCouponVO extends AbstractRequest {
    private List<OrderItemDto> orderItemList;

    @Override
    public void requestCheck() {

    }
}
