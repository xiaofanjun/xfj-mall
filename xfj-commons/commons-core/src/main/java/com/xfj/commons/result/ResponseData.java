package com.xfj.commons.result;

import lombok.Data;

/**
 * @Author ZQ
 * @Description 返回前端数据
 * @Date 2019/11/24 19:20
 **/
@Data
public class ResponseData<T> {

    //成功标识
    public static final String SUCCESS = "200";
    //客户端请求错误
    public static final String REQUEST_ERROR = "400";
    //服务端错误
    public static final String SERVER_ERROR = "500";

    //是否返回成功
    private boolean success;
    //返回消息
    private String message;
    //返回码
    private String code;
    //返回数据
    private T result;
    //时间戳
    private long timestamp = System.currentTimeMillis();

    public ResponseData() {
    }

    public ResponseData(boolean success) {
        this.success = success;
        if (success) {
            this.code = SUCCESS;
            this.message = "处理成功";
        }
    }

    public ResponseData(String code, String msg) {
        this.code = code;
        this.message = msg;
        this.success = SUCCESS.equals(code) ? true : false;
    }

    public ResponseData(String code, T result, String msg) {
        this(code, msg);
        this.result = result;
    }
}
