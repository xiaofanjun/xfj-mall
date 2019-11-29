package com.xfj.user.intercepter;

import com.alibaba.fastjson.JSON;
import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.commons.tool.utils.CookieUtil;
import com.xfj.user.IUserLoginService;
import com.xfj.user.annotation.Anoymous;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.rs.CheckAuthRS;
import com.xfj.user.vo.CheckAuthVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @Author ZQ
 * @Description 用来实现token拦截认证
 * <p>
 * token 作用：可以避免重复登录，从而减少对数据的交互次数
 * 作用二：避免表单重复提交，不过在这里目前还没有用到
 * @Date 2019/10/13 20:32
 **/
public class TokenIntercepter extends HandlerInterceptorAdapter {

    @Reference(timeout = 3000)
    IUserLoginService iUserLoginService;

    public static String ACCESS_TOKEN = "access_token";

    public static String USER_INFO_KEY = "userInfo";

    /**
     * @return boolean
     * @Author ZQ
     * @Description 拦截所有请求
     * <p>
     * 校验是否有Anoymous 注解，没有证明需要进行token校验
     * <p>
     * token 校验通过 存放一下用户信息到request中
     * @Date 2019/11/29 15:15
     * @Param [request, response, handler]
     **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Object bean = handlerMethod.getBean();
        if (isAnoymous(handlerMethod)) {
            return true;
        }
        String token = CookieUtil.getCookieValue(request, ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            ResponseData responseData = new ResponseUtil().setErrorMsg("token已失效");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().write(JSON.toJSON(responseData).toString());
            return false;
        }
        CheckAuthVO checkAuthRequest = new CheckAuthVO();
        checkAuthRequest.setToken(token);
        CheckAuthRS checkAuthResponse = iUserLoginService.validToken(checkAuthRequest);
        if (checkAuthResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            request.setAttribute(USER_INFO_KEY, checkAuthResponse.getUserinfo()); //保存token解析后的信息后续要用
            return super.preHandle(request, response, handler);
        }
        ResponseData responseData = new ResponseUtil().setErrorMsg(checkAuthResponse.getMsg());
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(JSON.toJSON(responseData).toString());
        return false;
    }

    private boolean isAnoymous(HandlerMethod handlerMethod) {
        Object bean = handlerMethod.getBean();
        Class clazz = bean.getClass();
        if (clazz.getAnnotation(Anoymous.class) != null) {
            return true;
        }
        Method method = handlerMethod.getMethod();
        return method.getAnnotation(Anoymous.class) != null;
    }
}
