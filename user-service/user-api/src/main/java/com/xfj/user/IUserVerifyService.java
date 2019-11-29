package com.xfj.user;

import com.xfj.user.rs.UserVerifyRS;
import com.xfj.user.vo.UserVerifyVO;

public interface IUserVerifyService {



    /**
     * 激活邮件
     * @param userVerifyRequest
     * @return
     */
    UserVerifyRS verifyMemer(UserVerifyVO userVerifyRequest);
}
