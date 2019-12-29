package com.xfj.user.controller;

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.commons.tool.utils.CookieUtil;
import com.xfj.user.IKaptchaService;
import com.xfj.user.annotation.Anoymous;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.rs.KaptchaCodeRS;
import com.xfj.user.vo.KaptchaCodeVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author ZQ
 * @Description 验证码
 * @Date 2019/11/27 19:49
 **/
@RestController
@RequestMapping("/user")
public class CaptchaController {

    @Reference(timeout = 3000, group = "${dubbo-group.name}")
    IKaptchaService kaptchaService;

    /**
     * @return com.xfj.commons.result.ResponseData
     * @Author ZQ
     * @Description // 获取验证码
     * <p>
     * 1: 把生成的验证码放入到redis
     * 2: 之后把验证码的key放到了 Cookie中
     * <p>
     * 验证码的校验在后端校验的原因： 后端返回的是图片，不是验证码字符串，另外防止可以通过前端直接拿到验证码
     * <p>
     * 把key放入到Cookie的原因： 可以保持同一会话，比如如果放入到rredis中，两个请求过来，取key值的时候就会出错
     * @Date 2019/11/25 20:21
     * @Param [response]
     **/
    @Anoymous
    @GetMapping("/kaptcha")
    public ResponseData getKaptchaCode(HttpServletResponse response) {
        KaptchaCodeVO kaptchaCodeRequest = new KaptchaCodeVO();
        KaptchaCodeRS kaptchaCodeResponse = kaptchaService.getKaptchaCode(kaptchaCodeRequest);
        if (kaptchaCodeResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            Cookie cookie = CookieUtil.genCookie("kaptcha_uuid", kaptchaCodeResponse.getUuid(), "/", 60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResponseUtil<>().setData(kaptchaCodeResponse.getImageCode());
        }
        return new ResponseUtil<>().setErrorMsg(kaptchaCodeResponse.getCode());
    }

    /**
     * @return com.xfj.commons.result.ResponseData
     * @Author ZQ
     * @Description 验证码校验
     * @Date 2019/11/27 19:50
     * @Param [code, httpServletRequest]
     **/
    @Anoymous
    @PostMapping("/kaptcha")
    public ResponseData validKaptchaCode(@RequestBody String code, HttpServletRequest httpServletRequest) {
        KaptchaCodeVO request = new KaptchaCodeVO();
        String uuid = CookieUtil.getCookieValue(httpServletRequest, "kaptcha_uuid");
        request.setUuid(uuid);
        request.setCode(code);
        KaptchaCodeRS response = kaptchaService.validateKaptchaCode(request);
        if (response.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            return new ResponseUtil<>().setData(null);
        }
        return new ResponseUtil<>().setErrorMsg(response.getCode());
    }
}
