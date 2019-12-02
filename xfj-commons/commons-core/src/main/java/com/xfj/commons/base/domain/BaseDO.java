package com.xfj.commons.base.domain;

import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * @CLassNmae BaseDO
 * @Description 基础的DO
 * @Author ZQ
 * @Date 2019/12/1 21:05
 * @Version 1.0
 **/
@Data
public class BaseDO<T> {
    private static final long serialVersionUID = 1253855922292878867L;
    @Id
    private T id;
    private String operator;
    private Date createdate;
    private Date modifydate;
    private String sass_id;
}
