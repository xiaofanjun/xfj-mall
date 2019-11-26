package com.xfj.commons.result;

import lombok.Data;

/**
 * @Author ZQ
 * @Description 相应数据
 * @Date 2019/11/24 19:20
 **/
@Data
public class ResponseData<T> {
    //是否返回成功
    private boolean success;
    //返回消息
    private String message;
    //返回码
    private int code;
    //返回数据
    private T result;
    //时间戳
    private long timestamp = System.currentTimeMillis();
}
