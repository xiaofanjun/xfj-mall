package com.xfj.commons.result;

import java.io.Serializable;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/24 19:19
 **/
public abstract class AbstractResponse implements Serializable {

    private static final long serialVersionUID = 7505997295595095971L;
    private String code;
    private String msg;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
