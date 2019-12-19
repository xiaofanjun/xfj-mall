package com.xfj.user.services.bl;

import com.xfj.commons.base.service.BaseService;
import com.xfj.commons.producer.KafKaMessageProducer;
import com.xfj.commons.tool.exception.ValidateException;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.entitys.Member;
import com.xfj.user.entitys.Test;
import com.xfj.user.entitys.UserVerify;
import com.xfj.user.mapper.MemberMapper;
import com.xfj.user.rs.UserRegisterRS;
import com.xfj.user.vo.UserRegisterVO;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author ZQ
 * @Description 用户注册具体的业务逻辑实现，该层主要处理有事务相关问题
 * <p>
 * 在这里业务层BL 先不对外提供接口，如果有需要提供的话，直接该类实现对应的接口，然后该类实现该接口即可
 * @Date 2019/11/27 20:50
 **/
@Slf4j
@Component
public class UserRegisterServiceBl extends BaseService<Member, String> {

    @Autowired
    MemberMapper memberMapper;
    @Autowired
    RedissonClient redissonClient;
    @Autowired
    KafKaMessageProducer kafKaMessageProducer;
    @Autowired
    UserVerifyServiceBl userVerifyServiceBl;
    @Autowired
    TestServiceBl testServiceBl;

    /**
     * @return void
     * @Author ZQ
     * @Description 处理注册相关业务
     * @Date 2019/11/29 18:42
     * @Param [request]
     **/
    @Transactional
    public UserRegisterRS doRegisterBusiness(UserRegisterVO userVO, UserRegisterRS response) {
        //插入用户表
        Member member = insertMember(userVO);
        if (null == member) {
            response.setCode(SysRetCodeConstants.USER_REGISTER_FAILED.getCode());
            response.setMsg(SysRetCodeConstants.USER_REGISTER_FAILED.getMessage());
            return response;
        }
        //插入用户校验表
        UserVerify userVerify = inserUserVerity(member);
        if (null == userVerify) {
            response.setCode(SysRetCodeConstants.USER_REGISTER_VERIFY_FAILED.getCode());
            response.setMsg(SysRetCodeConstants.USER_REGISTER_VERIFY_FAILED.getMessage());
            return response;
        }
        //发送邮件激活 这个放到以后绑定邮箱的时候进行邮箱的激活
//        sendEmailToMq(member, userVerify);
        // 业务处理成功后返回
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        return response;
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 发送激活邮件
     * <p>
     * 1: 如何查看kakfa中的消息
     * 2:为什么在用户注册的时候把用户校验信息放入到kafka中?
     * @Date 2019/11/29 19:09
     * @Param [member, userVerify]
     **/
    private void sendEmailToMq(Member member, UserVerify userVerify) {
        //发送消息到KafKa 目前由于发送邮件激活
        Map map = new HashMap();
        map.put("username", userVerify.getUsername());
        map.put("key", userVerify.getUuid());
        map.put("email", member.getEmail());
        kafKaMessageProducer.sendMessage(SysRetCodeConstants.USER_REGISTER_TOPIC.getCode(), map);
    }

    /**
     * @return com.xfj.user.entitys.UserVerify
     * @Author ZQ
     * @Description 插入用户验证表
     * @Date 2019/11/29 19:07
     * @Param [member]
     **/
    private UserVerify inserUserVerity(Member member) {
        //插入用户验证表
        UserVerify userVerify = new UserVerify();
        userVerify.setUsername(member.getUsername());
        String key = member.getUsername() + member.getPassword() + UUID.randomUUID().toString();
        userVerify.setUuid(DigestUtils.md5DigestAsHex(key.getBytes()));
        userVerify.setIsExpire("N");//注册信息是否过期
        userVerify.setIsVerify("N");//是否验证成功
        userVerify.setRegisterDate(new Date());
        if (userVerifyServiceBl.save(userVerify) != -1) {
            return userVerify;
        }
        return null;
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 插入用户基本信息
     * @Date 2019/11/29 18:49
     * @Param [userVO]
     **/
    private Member insertMember(UserRegisterVO userVO) {
        Member member = new Member();
        member.setUsername(userVO.getUserName());
        member.setPassword(DigestUtils.md5DigestAsHex(userVO.getUserPwd().getBytes()));
        member.setState(1);
        member.setIsVerified("N");//未激活
        member.setEmail(userVO.getEmail());
        if (save(member) != -1) {
            return member;
        }
        return null;
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 校验参数以及校验用户名是否存在
     * @Date 2019/11/27 20:50
     * @Param [request]
     **/
    public void validUserRegisterRequest(UserRegisterVO request) {
        request.requestCheck();
        Example example = new Example(Member.class);
        example.createCriteria().andEqualTo("state", 1).andEqualTo("username", request.getUserName());

        List<Member> users = memberMapper.selectByExample(example);
        if (users != null && users.size() > 0) {
            throw new ValidateException(SysRetCodeConstants.USERNAME_ALREADY_EXISTS.getCode(), SysRetCodeConstants.USERNAME_ALREADY_EXISTS.getMessage());
        }
    }
}
