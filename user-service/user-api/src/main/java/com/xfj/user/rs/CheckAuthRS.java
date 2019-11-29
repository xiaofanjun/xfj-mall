package com.xfj.user.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/27 21:15
 **/
@Data
public class CheckAuthRS extends AbstractResponse {

    private String userinfo;

    public Object getUserinfo() {
        return userinfo;
    }
}
