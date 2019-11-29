package com.xfj.user.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:20
 **/
@Data
public class AddressDto implements Serializable {

    private Long addressId;

    private Long userId;

    private String userName;

    private String tel;

    private String streetName;

    private Integer isDefault;
}
