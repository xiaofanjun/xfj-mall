package com.xfj.order.entitys;

import com.xfj.commons.base.domain.BaseDO;
import lombok.Data;

import javax.persistence.*;

@Table(name = "tb_order_shipping")
@Data
public class OrderShipping extends BaseDO<String> {

    /**
     * 收货人全名
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * 固定电话
     */
    @Column(name = "receiver_phone")
    private String receiverPhone;

    /**
     * 移动电话
     */
    @Column(name = "receiver_mobile")
    private String receiverMobile;

    /**
     * 省份
     */
    @Column(name = "receiver_state")
    private String receiverState;

    /**
     * 城市
     */
    @Column(name = "receiver_city")
    private String receiverCity;

    /**
     * 区/县
     */
    @Column(name = "receiver_district")
    private String receiverDistrict;

    /**
     * 收货地址，如：xx路xx号
     */
    @Column(name = "receiver_address")
    private String receiverAddress;

    /**
     * 邮政编码,如：310001
     */
    @Column(name = "receiver_zip")
    private String receiverZip;



}