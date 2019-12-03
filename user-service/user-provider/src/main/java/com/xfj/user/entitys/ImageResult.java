package com.xfj.user.entitys;

import lombok.Data;

/**
 * @Author ZQ
 * @Description 验证码
 * @Date 2019/11/26 21:49
 **/
@Data
public class ImageResult {
    String img;
    String code;
}