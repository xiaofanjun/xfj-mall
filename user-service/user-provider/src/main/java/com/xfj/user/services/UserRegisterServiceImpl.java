package com.xfj.user.services;

import com.xfj.commons.annotation.ServiceLog;
import com.xfj.user.IUserRegisterService;
import com.xfj.user.rs.UserRegisterRS;
import com.xfj.user.services.bl.UserRegisterServiceBl;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.vo.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author ZQ
 * @Description 对外提供接口,
 * <p>
 * 在该类中不得出现写操作（所以在该层也不会出现事务问题）
 * <p>
 * 还可以添加一些比如为空的判断操作
 * @Date 2019/11/27 20:50
 **/
@Slf4j
@Service(group = "${dubbo-group.name}")
public class UserRegisterServiceImpl implements IUserRegisterService {

    @Autowired
    private UserRegisterServiceBl registerServiceBl;

    @ServiceLog(value = "前端用户注册")
    public UserRegisterRS register(UserRegisterVO userVO) {
        UserRegisterRS response = new UserRegisterRS();
        try {
            //校验
            registerServiceBl.validUserRegisterRequest(userVO);
            //处理注册业务
            response = registerServiceBl.doRegisterBusiness(userVO, response);
            if (null != response) {
                return response;
            }
        } catch (Exception e) {
            log.error("UserLoginServiceImpl.register Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

}
