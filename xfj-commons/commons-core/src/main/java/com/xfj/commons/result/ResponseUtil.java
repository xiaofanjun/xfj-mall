package com.xfj.commons.result;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/24 19:20
 **/
@Deprecated
public class ResponseUtil<T> {

    private ResponseData<T> responseData;
    //成功标识
    public static final int SUCCESS = 200;
    //客户端请求错误
    public static final int REQUEST_ERROR = 400;
    //服务端错误
    public static final int SERVER_ERROR = 500;

    public ResponseUtil() {
        responseData = new ResponseData<>();
        responseData.setSuccess(true);
        responseData.setMessage("success");
        responseData.setCode(String.valueOf(SUCCESS));
    }

    /**
     * @return com.xfj.commons.result.ResponseData<T>
     * @Author ZQ
     * @Description 成功
     * @Date 2019/11/27 19:43
     * @Param [t]
     **/
    public ResponseData<T> setData(T t) {
        if (null != t) {
            this.responseData.setResult(t);
        }
        this.responseData.setSuccess(true);
        responseData.setCode(String.valueOf(SUCCESS));
        return this.responseData;
    }

    /**
     * @return com.xfj.commons.result.ResponseData<T>
     * @Author ZQ
     * @Description 成功+自定义消息
     * @Date 2019/11/27 19:43
     * @Param [t, msg]
     **/
    public ResponseData<T> setData(T t, String msg) {
        this.responseData.setResult(t);
        this.responseData.setSuccess(true);
        this.responseData.setMessage(msg);
        responseData.setCode(String.valueOf(SUCCESS));
        return this.responseData;
    }

    /**
     * @return com.xfj.commons.result.ResponseData<T>
     * @Author ZQ
     * @Description 失败，默认返回500
     * @Date 2019/11/27 19:43
     * @Param [msg]
     **/
    public ResponseData<T> setErrorMsg(String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode(String.valueOf(SERVER_ERROR));
        return this.responseData;
    }

    /**
     * @return com.xfj.commons.result.ResponseData<T>
     * @Author ZQ
     * @Description 自定义错误码+自定义信息
     * @Date 2019/11/27 19:44
     * @Param [code, msg]
     **/
    public ResponseData<T> setErrorMsg(Integer code, String msg) {
        this.responseData.setSuccess(false);
        this.responseData.setMessage(msg);
        responseData.setCode(String.valueOf(code));
        return this.responseData;
    }
}
