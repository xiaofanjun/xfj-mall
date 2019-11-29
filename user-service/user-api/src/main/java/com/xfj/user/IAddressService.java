package com.xfj.user;

import com.xfj.user.rs.*;
import com.xfj.user.vo.*;

/**
 * @Author ZQ
 * @Description 快递邮寄地址相关服务
 * @Date 2019/11/29 9:31
 **/
public interface IAddressService {

    /**
     * 获取地址列表，根据用户id
     *
     * @param request
     * @return
     */
    AddressListRS addressList(AddressListVO request);

    /**
     * 获取地址详细，根据地址id
     *
     * @param request
     * @return
     */
    AddressDetailRS addressDetail(AddressDetailVO request);

    /**
     * 添加地址
     *
     * @param request
     * @return
     */
    AddAddressRS createAddress(AddAddressVO request);

    /**
     * 修改地址信息
     *
     * @param request
     * @return
     */
    UpdateAddressRS updateAddress(UpdateAddressVO request);

    /**
     * 删除地址（for id）
     *
     * @param request
     * @return
     */
    DeleteAddressRS deleteAddress(DeleteAddressVO request);
}
