package com.xfj.user.controller;

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.user.IUserRegisterService;
import com.xfj.user.IUserVerifyService;
import com.xfj.user.annotation.Anoymous;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.rs.UserVerifyRS;
import com.xfj.user.vo.UserVerifyVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author ZQ
 * @Description 用户注册激活
 * @Date 2019/11/27 20:26
 **/
@RestController
@RequestMapping("/user")
public class UserVerifyController {

    @Reference(timeout = 3000, group = "${dubbo-group.name}")
    IUserRegisterService iUserRegisterService;

    @Reference(timeout = 3000, group = "${dubbo-group.name}")
    IUserVerifyService iUserVerifyService;

    @Anoymous
    @GetMapping("/verify")
    public ResponseData register(@RequestParam String uuid, @RequestParam String username, HttpServletRequest request) {
        if (!(StringUtils.isNotBlank(uuid) && StringUtils.isNotBlank(username))) {
            return new ResponseUtil<>().setErrorMsg("注册序号/用户名不允许为空");
        }
        UserVerifyVO userVerifyRequest = new UserVerifyVO();
        userVerifyRequest.setUserName(username);
        userVerifyRequest.setUuid(uuid);
        UserVerifyRS userVerifyResponse = iUserVerifyService.verifyMemer(userVerifyRequest);
        if (userVerifyResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(null);
        } else {
            return new ResponseUtil().setData(userVerifyResponse.getMsg());
        }
    }
}
