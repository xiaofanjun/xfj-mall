package com.xfj.user;

import com.xfj.user.rs.UserRegisterRS;
import com.xfj.user.vo.UserRegisterVO;

public interface IUserRegisterService {

    /**
     * 实现用户注册功能
     * @param request
     * @return
     */
    UserRegisterRS register(UserRegisterVO request);
}
