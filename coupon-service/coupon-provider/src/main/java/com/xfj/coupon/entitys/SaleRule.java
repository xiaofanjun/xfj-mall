package com.xfj.coupon.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "tb_sale_rule")
@Data
public class SaleRule extends BaseDO<String> {
    /**
     * 活动规则需要绑定acti_id ，如果是优惠券规则， 此字段为null
     */
    @Column(name = "acti_id")
    private String actiId;

    /**
     * 满足规则条件的商品id
     */
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "full_price")
    private BigDecimal fullPrice;

    @Column(name = "discount_price")
    private BigDecimal discountPrice;

    @Column(name = "discount_rate")
    private BigDecimal discountRate;

    @Column(name = "gift_item_id")
    private String giftItemId;

    /**
     * 是否可叠加规则
     */
    private Boolean overlap;

    private String desc;

}