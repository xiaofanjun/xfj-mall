package com.xfj.commons.tool.exception;

/**
 * @Author ZQ
 * @Description //业务处理异常
 * @Date 2019/11/24 19:25
 **/
public class ProcessException extends BaseBusinessException {
    public ProcessException() {
        super();
    }

    public ProcessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ProcessException(Throwable arg0) {
        super(arg0);
    }

    public ProcessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public ProcessException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public ProcessException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }
}
