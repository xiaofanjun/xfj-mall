package com.xfj.user.services;

import com.alibaba.fastjson.JSON;
import com.xfj.user.IUserLoginService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.UserConverterMapper;
import com.xfj.user.entitys.Member;
import com.xfj.user.mapper.MemberMapper;
import com.xfj.user.rs.CheckAuthRS;
import com.xfj.user.rs.UserLoginRS;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.utils.JwtTokenUtils;
import com.xfj.user.vo.CheckAuthVO;
import com.xfj.user.vo.UserLoginVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author ZQ
 * @Description 登录相关业务逻辑
 * @Date 2019/11/27 21:24
 **/
@Slf4j
@Service
public class UserLoginServiceImpl implements IUserLoginService {
    @Autowired
    MemberMapper memberMapper;

    /**
     * @return com.xfj.user.rs.UserLoginRS
     * @Author ZQ
     * @Description 登录成功创建加密token
     * @Date 2019/11/29 15:39
     * @Param [request]
     **/
    @Override
    public UserLoginRS login(UserLoginVO request) {
        log.info("Begin UserLoginServiceImpl.login: request:" + request);
        UserLoginRS response = new UserLoginRS();
        try {
            request.requestCheck();
            Example example = new Example(Member.class);
            example.createCriteria().andEqualTo("state", 1).andEqualTo("username", request.getUserName());

            List<Member> member = memberMapper.selectByExample(example);
            if (member == null || member.size() == 0) {
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            if (!DigestUtils.md5DigestAsHex(request.getPassword().getBytes()).equals(member.get(0).getPassword())) {
                response.setCode(SysRetCodeConstants.USERORPASSWORD_ERRROR.getCode());
                response.setMsg(SysRetCodeConstants.USERORPASSWORD_ERRROR.getMessage());
                return response;
            }
            Map<String, Object> map = new HashMap<>();
            map.put("uid", member.get(0).getId());
            map.put("file", member.get(0).getFile());
            //创建token
            String token = JwtTokenUtils.builder().msg(JSON.toJSON(map).toString()).build().creatJwtToken();
            response = UserConverterMapper.INSTANCE.converter(member.get(0));
            response.setToken(token);
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("UserLoginServiceImpl.login Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * @return com.xfj.user.rs.CheckAuthRS
     * @Author ZQ
     * @Description 校验token
     * @Date 2019/11/29 15:33
     * @Param [request]
     **/
    @Override
    public CheckAuthRS validToken(CheckAuthVO request) {
        log.info("Begin UserLoginServiceImpl.validToken: request:" + request);
        CheckAuthRS response = new CheckAuthRS();
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        try {
            request.requestCheck();
            String decodeMsg = JwtTokenUtils.builder().token(request.getToken()).build().freeJwt();
            if (StringUtils.isNotBlank(decodeMsg)) {
                log.info("validate success");
                response.setUserinfo(decodeMsg);
                return response;
            }
            response.setCode(SysRetCodeConstants.TOKEN_VALID_FAILED.getCode());
            response.setMsg(SysRetCodeConstants.TOKEN_VALID_FAILED.getMessage());
        } catch (Exception e) {
            log.error("UserLoginServiceImpl.validToken Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
