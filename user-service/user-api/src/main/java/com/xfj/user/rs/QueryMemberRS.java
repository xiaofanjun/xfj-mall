package com.xfj.user.rs;/**
 * Created by mic on 2019/7/30.
 */

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:16
 **/
@Data
public class QueryMemberRS extends AbstractResponse {

    private String id;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String sex;

    private String address;

    private Integer state;

    private String file;

    private String description;

    private Integer points;

    private BigDecimal balance;
}
