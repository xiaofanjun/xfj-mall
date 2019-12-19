package com.xfj.user.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @Author ZQ
 * @Description 头像返回数据
 * @Date 2019/11/27 21:16
 **/
@Data
public class HeadImageRS extends AbstractResponse {
    private String imageUrl;//用户头像路径
}
