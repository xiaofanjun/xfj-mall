package com.xfj.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.form.AddressForm;
import com.xfj.user.IAddressService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.intercepter.TokenIntercepter;
import com.xfj.user.rs.AddAddressRS;
import com.xfj.user.rs.AddressListRS;
import com.xfj.user.rs.DeleteAddressRS;
import com.xfj.user.rs.UpdateAddressRS;
import com.xfj.user.vo.AddAddressVO;
import com.xfj.user.vo.AddressListVO;
import com.xfj.user.vo.DeleteAddressVO;
import com.xfj.user.vo.UpdateAddressVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/shopping")
@Api(tags = "AddressController", description = "地址控制层")
public class AddressController {

    @Reference(timeout = 3000, group = "dubbo-test")
    IAddressService addressService;

    /**
     * 获取当前用户的地址列表
     *
     * @return
     */
    @GetMapping("/addresses")
    @ApiOperation("获取当前用户的地址列表")
    public ResponseData addressList(HttpServletRequest request) {
        String userInfo = (String) request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        String uid = object.get("uid").toString();
        AddressListVO addressListRequest = new AddressListVO();
        addressListRequest.setUserId(uid);
        AddressListRS response = addressService.addressList(addressListRequest);
        if (response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getAddressDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @ApiOperation("添加地址")
    @PostMapping("/addresses")
    @ApiImplicitParam(name = "form", value = "地址信息", dataType = "AddressForm", required = true)
    public ResponseData addressAdd(@RequestBody AddressForm form, HttpServletRequest servletRequest) {
        log.debug(form.is_Default() + "");
        log.debug(form.toString());

        AddAddressVO request = new AddAddressVO();
        String userInfo = (String) servletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        String uid = object.get("uid").toString();
        request.setUserId(uid);
        request.setUserName(form.getUserName());
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setIsDefault(form.is_Default() ? 1 : null);
        AddAddressRS response = addressService.createAddress(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    @ApiOperation("删除地址")
    @DeleteMapping("/addresses/{addressid}")
    @ApiImplicitParam(name = "addressid", value = "地址ID", paramType = "path", required = true)
    public ResponseData addressDel(@PathVariable("addressid") String addressid) {
        DeleteAddressVO request = new DeleteAddressVO();
        request.setAddressId(addressid);
        DeleteAddressRS response = addressService.deleteAddress(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());

    }

    @ApiOperation("更新地址")
    @PutMapping("/addresses")
    @ApiImplicitParam(name = "form", value = "地址信息", dataType = "AddressForm", required = true)
    public ResponseData addressUpdate(@RequestBody AddressForm form, HttpServletRequest servletRequest) {
        UpdateAddressVO request = new UpdateAddressVO();
        String userInfo = (String) servletRequest.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject object = JSON.parseObject(userInfo);
        String uid = object.get("uid").toString();
        request.setAddressId(form.getAddressId());
        request.setIsDefault(form.is_Default() ? 1 : null);
        request.setStreetName(form.getStreetName());
        request.setTel(form.getTel());
        request.setUserId(uid);
        request.setUserName(form.getUserName());

        UpdateAddressRS response = addressService.updateAddress(request);

        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }
}
