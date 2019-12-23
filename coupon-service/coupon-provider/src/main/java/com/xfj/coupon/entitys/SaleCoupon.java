package com.xfj.coupon.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import java.util.Date;
import javax.persistence.*;

@Table(name = "tb_sale_coupon")
@Data
public class SaleCoupon extends BaseDO<String> {

    private String name;

    private String desc;

    /**
     * // 1 满减券 2 通用0元减价券
     */
    private Byte type;

    /**
     * 优惠金额
     */
    @Column(name = "rule_id")
    private String ruleId;

    /**
     * 是否可用
     */
    @Column(name = "is_visible")
    private Boolean isVisible;

    /**
     * 优惠券可生成的优惠码数量
     */
    private Integer count;

    /**
     * 抢券开始时间  备用
     */
    @Column(name = "grab_start_time")
    private Date grabStartTime;

    @Column(name = "grab_end_time")
    private Date grabEndTime;

    /**
     * 优惠券有效期
     */
    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;


}