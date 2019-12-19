package com.xfj.user.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/27 21:18
 **/
@Data
public class UserLoginRS extends AbstractResponse {

    private String  id;
    private String username;
    private String phone;
    private String email;
    private String sex;
    private String address;
    private String file;
    private String description;
    private Integer points;
    private Long balance;
    private int state;
    private String token;
}
