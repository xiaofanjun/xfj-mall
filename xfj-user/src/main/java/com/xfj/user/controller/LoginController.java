package com.xfj.user.controller;

import com.alibaba.fastjson.JSON;
import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.commons.tool.utils.CookieUtil;
import com.xfj.user.IKaptchaService;
import com.xfj.user.IUserLoginService;
import com.xfj.user.annotation.Anoymous;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.intercepter.TokenIntercepter;
import com.xfj.user.rs.KaptchaCodeRS;
import com.xfj.user.rs.UserLoginRS;
import com.xfj.user.vo.KaptchaCodeVO;
import com.xfj.user.vo.UserLoginVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author ZQ
 * @Description 登录
 * @Date 2019/11/24 18:42
 **/

@RestController
@RequestMapping("/user")
public class LoginController {

    @Reference(timeout = 3000)
    IUserLoginService iUserLoginService;

    @Reference(timeout = 3000)
    IKaptchaService kaptchaService;

    /**
     * 验证码开关 可以在配置文件配置下，先留个口
     */
    @Value("${captchaFlag:true}")
    private boolean captchaFlag;

    /**
     * @return com.xfj.commons.result.ResponseData
     * @Author ZQ
     * @Description 登录验证： 验证用户名，密码，验证码，登录成功后，把token放入cookie中
     * @Date 2019/11/27 20:11
     * @Param [map, request, response]
     **/
    @Anoymous
    @PostMapping("/login")
    public ResponseData login(@RequestBody Map<String, String> map,
                              HttpServletRequest request, HttpServletResponse response) {
        UserLoginVO loginRequest = new UserLoginVO();
        loginRequest.setPassword(map.get("userPwd"));
        loginRequest.setUserName(map.get("userName"));
        String captcha = map.get("captcha");

        if (captchaFlag) {
            KaptchaCodeVO kaptchaCodeRequest = new KaptchaCodeVO();
            String uuid = CookieUtil.getCookieValue(request, "kaptcha_uuid");
            kaptchaCodeRequest.setCode(captcha);
            kaptchaCodeRequest.setUuid(uuid);
            KaptchaCodeRS kaptchaCodeResponse = kaptchaService.validateKaptchaCode(kaptchaCodeRequest);
            if (!kaptchaCodeResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
                return new ResponseUtil<>().setErrorMsg(kaptchaCodeResponse.getMsg());
            }
        }
        UserLoginRS userLoginResponse = iUserLoginService.login(loginRequest);
        if (userLoginResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            Cookie cookie = CookieUtil.genCookie(TokenIntercepter.ACCESS_TOKEN, userLoginResponse.getToken(), "/", 24 * 60 * 60);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            return new ResponseData(ResponseData.SUCCESS, userLoginResponse, "处理成功");
        }
        return new ResponseData(userLoginResponse.getCode(), userLoginResponse.getMsg());
    }

    /**
     * @return com.xfj.commons.result.ResponseData
     * @Author ZQ
     * @Description 验证是否需要登录
     * @Date 2019/11/29 15:43
     * @Param [request]
     **/
    @GetMapping("/login")
    public ResponseData checkLogin(HttpServletRequest request) {
        String userInfo = (String) request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        Object object = JSON.parse(userInfo);
        return new ResponseUtil().setData(object);
    }

    /**
     * @return com.xfj.commons.result.ResponseData
     * @Author ZQ
     * @Description 退出
     * <p>
     * 销毁Cookie
     * @Date 2019/11/27 20:12
     * @Param [request, response]
     **/
    @GetMapping("/loginOut")
    public ResponseData loginOut(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(TokenIntercepter.ACCESS_TOKEN)) {
                    cookie.setValue(null);
                    cookie.setMaxAge(0);// 立即销毁cookie
                    cookie.setPath("/");
                    response.addCookie(cookie); //覆盖原来的token
                }
            }
        }
        return new ResponseUtil().setData(null);
    }
}
