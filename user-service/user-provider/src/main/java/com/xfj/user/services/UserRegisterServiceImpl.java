package com.xfj.user.services;

import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.IUserRegisterService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.dal.entitys.Member;
import com.xfj.user.dal.entitys.UserVerify;
import com.xfj.user.dal.persistence.MemberMapper;
import com.xfj.user.dal.persistence.UserVerifyMapper;
import com.xfj.user.rs.UserRegisterRS;
import com.xfj.user.registerVerification.KafKaRegisterSuccProducer;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.vo.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author ZQ
 * @Description 用户注册
 * @Date 2019/11/27 20:50
 **/
@Slf4j
@Service
public class UserRegisterServiceImpl implements IUserRegisterService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    RedissonClient redissonClient;

    @Autowired
    KafKaRegisterSuccProducer kafKaRegisterSuccProducer;

    @Autowired
    UserVerifyMapper userVerifyMapper;

    @Override
    public UserRegisterRS register(UserRegisterVO request) {
        log.info("Begin UserLoginServiceImpl.register: request:" + request);
        UserRegisterRS response = new UserRegisterRS();
        try {
            validUserRegisterRequest(request);
            Member member = new Member();
            member.setUsername(request.getUserName());
            member.setPassword(DigestUtils.md5DigestAsHex(request.getUserPwd().getBytes()));

            member.setState(1);
            member.setCreated(new Date());
            member.setUpdated(new Date());
            member.setIsVerified("N");//为激活
            member.setEmail(request.getEmail());
            if (memberMapper.insert(member) != 1) {
                response.setCode(SysRetCodeConstants.USER_REGISTER_FAILED.getCode());
                response.setMsg(SysRetCodeConstants.USER_REGISTER_FAILED.getMessage());
                return response;
            }
            //插入用户验证表
            UserVerify userVerify = new UserVerify();
            userVerify.setUsername(member.getUsername());
            String key = member.getUsername() + member.getPassword() + UUID.randomUUID().toString();
            userVerify.setUuid(DigestUtils.md5DigestAsHex(key.getBytes()));
            userVerify.setIsExpire("N");//注册信息是否过期
            userVerify.setIsVerify("N");//是否验证成功
            userVerify.setRegisterDate(new Date());
            if (userVerifyMapper.insert(userVerify) != 1) {
                response.setCode(SysRetCodeConstants.USER_REGISTER_VERIFY_FAILED.getCode());
                response.setMsg(SysRetCodeConstants.USER_REGISTER_VERIFY_FAILED.getMessage());
                return response;
            }
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
            //发送消息到KafKa 目前由于发送邮件激活
            Map map = new HashMap();
            map.put("username", userVerify.getUsername());
            map.put("key", userVerify.getUuid());
            map.put("email", member.getEmail());
            kafKaRegisterSuccProducer.sendRegisterSuccInfo(map);
        } catch (Exception e) {
            log.error("UserLoginServiceImpl.register Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 校验参数以及校验用户名是否存在
     * @Date 2019/11/27 20:50
     * @Param [request]
     **/
    private void validUserRegisterRequest(UserRegisterVO request) {
        request.requestCheck();
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("state", 1).andEqualTo("username", request.getUserName());

        List<Member> users = memberMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            throw new ValidateException(SysRetCodeConstants.USERNAME_ALREADY_EXISTS.getCode(), SysRetCodeConstants.USERNAME_ALREADY_EXISTS.getMessage());
        }
    }
}
