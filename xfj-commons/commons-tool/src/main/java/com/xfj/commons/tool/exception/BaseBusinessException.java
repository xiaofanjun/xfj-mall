package com.xfj.commons.tool.exception;

import lombok.Data;

/**
 * @Author ZQ
 * @Description //基础异常类
 * @Date 2019/11/24 19:24
 **/
@Data
public class BaseBusinessException extends RuntimeException {
    //错误码
    protected String errorCode;
    //错误信息
    protected String message;
    //
    protected String extFields;

    public BaseBusinessException() {
        super();
    }

    public BaseBusinessException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public BaseBusinessException(Throwable arg0) {
        super(arg0);
    }

    public BaseBusinessException(String errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public BaseBusinessException(String errorCode, String message) {
        super();
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseBusinessException(String errorCode, String message, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseBusinessException(String errorCode, String message, String extFields, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.message = message;
        this.extFields = extFields;
    }
}
