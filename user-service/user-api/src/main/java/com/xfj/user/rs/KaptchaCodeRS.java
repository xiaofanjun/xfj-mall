package com.xfj.user.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @Author ZQ
 * @Description 验证码Response
 * @Date 2019/11/26 21:53
 **/
@Data
public class KaptchaCodeRS extends AbstractResponse {

    private String imageCode;

    private String uuid;


}
