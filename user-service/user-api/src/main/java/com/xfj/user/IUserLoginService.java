package com.xfj.user;

import com.xfj.user.rs.CheckAuthRS;
import com.xfj.user.rs.UserLoginRS;
import com.xfj.user.vo.CheckAuthVO;
import com.xfj.user.vo.UserLoginVO;

public interface IUserLoginService {

    /**
     * 用户登录
     * @param request
     * @return
     */
    UserLoginRS login(UserLoginVO request);


    /**
     * 校验过程
     * @param request
     * @return
     */
    CheckAuthRS validToken(CheckAuthVO request);
}
