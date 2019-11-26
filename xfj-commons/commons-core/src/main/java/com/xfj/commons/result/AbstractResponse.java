package com.xfj.commons.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ZQ
 * @Description Response 抽象层
 * @Date 2019/11/24 19:19
 **/
@Data
public abstract class AbstractResponse implements Serializable {
    private static final long serialVersionUID = 7505997295595095971L;
    private String code;
    private String msg;
}
