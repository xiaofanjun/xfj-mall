package com.xfj.user.services;

import com.xfj.user.IUserVerifyService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.rs.UserVerifyRS;
import com.xfj.user.services.bl.UserVerifyServiceBl;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.vo.UserVerifyVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author ZQ
 * @Description 用户激活
 * @Date 2019/11/29 16:14
 **/
@Service
@Slf4j
public class UserVerifyServiceImpl implements IUserVerifyService {
    @Autowired
    private UserVerifyServiceBl userVerifyServiceBl;

    @Override
    public UserVerifyRS verifyMemer(UserVerifyVO request) {
        UserVerifyRS response = new UserVerifyRS();
        try {
            request.requestCheck();
            response = userVerifyServiceBl.verifyMemer(request, response);
            if (null != response) {
                return response;
            }
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setCode(SysRetCodeConstants.SUCCESS.getMessage());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
            return response;
        }
    }

}
