package com.xfj.coupon.strategy;

import com.xfj.coupon.vo.UseCouponVO;

/**
 * Created by oahnus on 2019/8/19
 * 23:54.
 */
public abstract class SaleStrategy {
    public abstract boolean match(UseCouponVO request);
    public abstract void perform();
}
