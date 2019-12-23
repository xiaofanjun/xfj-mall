package com.xfj.coupon.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_sale_coupon_code")
@Data
public class SaleCouponCode extends BaseDO<String> {

    /**
     * 优惠码
     */
    private String code;

    /**
     * 优惠券id
     */
    @Column(name = "coupon_id")
    private Long couponId;

    /**
     * 0 未领取 1 已领取未使用 2 冻结中 3 已使用 4 已过期
     */
    private Byte status;

    @Column(name = "user_id")
    private String userId;

    /**
     * 关联的订单id
     */
    @Column(name = "order_id")
    private String orderId;

    /**
     * 使用时间
     */
    @Column(name = "consume_time")
    private Date consumeTime;

}