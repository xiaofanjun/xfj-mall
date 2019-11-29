package com.xfj.user.controller;

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.commons.tool.utils.CookieUtil;
import com.xfj.user.IKaptchaService;
import com.xfj.user.IUserRegisterService;
import com.xfj.user.annotation.Anoymous;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.rs.KaptchaCodeRS;
import com.xfj.user.rs.UserRegisterRS;
import com.xfj.user.vo.KaptchaCodeVO;
import com.xfj.user.vo.UserRegisterVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author ZQ
 * @Description 注册
 * @Date 2019/11/27 20:25
 **/
@RestController
@RequestMapping("/user")
public class RegisterController {

    @Reference(timeout = 3000)
    IUserRegisterService iUserRegisterService;

    @Reference(timeout = 3000)
    IKaptchaService kaptchaService;

    /**
     * @return com.xfj.commons.result.ResponseData
     * @Author ZQ
     * @Description 注册功能实现
     * @Date 2019/11/27 20:35
     * @Param [map, request]
     **/
    @Anoymous
    @PostMapping("/register")
    public ResponseData register(@RequestBody UserRegisterVO urRequest, HttpServletRequest request) {
        KaptchaCodeVO kaptchaCodeRequest = new KaptchaCodeVO();
        String uuid = CookieUtil.getCookieValue(request, "kaptcha_uuid");
        kaptchaCodeRequest.setUuid(uuid);
        kaptchaCodeRequest.setCode(urRequest.getCaptcha());
        KaptchaCodeRS response = kaptchaService.validateKaptchaCode(kaptchaCodeRequest);
        if (!response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setErrorMsg(response.getMsg());
        }
        UserRegisterRS registerResponse = iUserRegisterService.register(urRequest);
        if (registerResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil().setData(null);
        }
        return new ResponseUtil().setErrorMsg(registerResponse.getMsg());
    }
}
