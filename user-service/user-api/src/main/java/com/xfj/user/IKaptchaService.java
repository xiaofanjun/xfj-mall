package com.xfj.user;

import com.xfj.user.rs.KaptchaCodeRS;
import com.xfj.user.vo.KaptchaCodeVO;

/**
 * @Author ZQ
 * @Description 验证码相关接口
 * @Date 2019/11/25 20:25
 **/
public interface IKaptchaService {

    /**
     * 获取图形验证码
     *
     * @param request
     * @return
     */
    KaptchaCodeRS getKaptchaCode(KaptchaCodeVO request);

    /**
     * 验证图形验证码
     *
     * @param request
     * @return
     */
    KaptchaCodeRS validateKaptchaCode(KaptchaCodeVO request);

}
