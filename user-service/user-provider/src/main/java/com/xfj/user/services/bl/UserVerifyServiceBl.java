package com.xfj.user.services.bl;

import com.xfj.commons.base.service.BaseService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.entitys.Member;
import com.xfj.user.entitys.UserVerify;
import com.xfj.user.mapper.MemberMapper;
import com.xfj.user.mapper.UserVerifyMapper;
import com.xfj.user.rs.UserVerifyRS;
import com.xfj.user.vo.UserVerifyVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author ZQ
 * @Description 用户激活
 * @Date 2019/11/29 16:14
 **/

@Slf4j
@Component
public class UserVerifyServiceBl extends BaseService<UserVerify, String> {

    @Autowired
    private MemberServiceBl memberServiceBl;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    UserVerifyMapper userVerifyMapper;

    private List<Member> member;

    private List<UserVerify> userVerifys;

    @Transactional
    public UserVerifyRS verifyMemer(UserVerifyVO request, UserVerifyRS response) {
        Example example = new Example(Member.class);
        //校验用户信息是否合法
        validateInfo(example, request, response);
        //更新用户校验表
        updateUserVerify(request, example);
        //更新member
        updateMember(example);
        return response;
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 更新用户表
     * @Date 2019/11/30 16:19
     * @Param [example]
     **/
    private void updateMember(Example example) {
        //更新Member 表的is_verify
        example.clear();
        example = new Example(Member.class);
        Member member_ = this.member.get(0);
        member_.setIsVerified("Y");
        memberServiceBl.updateByExample(member_, example);
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 更新用户校验表
     * @Date 2019/11/30 16:20
     * @Param [request, example]
     **/
    private void updateUserVerify(UserVerifyVO request, Example example) {
        example.clear();
        example.createCriteria().andEqualTo("uuid", request.getUuid());
        UserVerify userVerify = this.userVerifys.get(0);
        userVerify.setIsVerify("Y");
        //激活用户，修改tb_user_verify的信息 is_verify
        updateByExample(userVerify, example);
    }

    /**
     * @return com.xfj.user.rs.UserVerifyRS
     * @Author ZQ
     * @Description 校验信息
     * @Date 2019/11/30 16:10
     * @Param [example, response]
     **/
    private UserVerifyRS validateInfo(Example example, UserVerifyVO request, UserVerifyRS response) {
        example.createCriteria().andEqualTo("state", 1)
                .andEqualTo("username", request.getUserName());

        List<Member> member = memberMapper.selectByExample(example);
        if (member == null || member.size() == 0) {
            response.setCode(SysRetCodeConstants.USER_INFOR_INVALID.getCode());
            response.setMsg(SysRetCodeConstants.USER_INFOR_INVALID.getMessage());
            return response;
        }
        //是否存在注册激活信息
        example.clear();
        example = new Example(UserVerify.class);
        example.createCriteria().andEqualTo("uuid", request.getUuid());
        List<UserVerify> userVerifys = userVerifyMapper.selectByExample(example);
        if (userVerifys == null || userVerifys.size() == 0) {
            response.setCode(SysRetCodeConstants.USERVERIFY_INFOR_INVALID.getCode());
            response.setMsg(SysRetCodeConstants.USERVERIFY_INFOR_INVALID.getMessage());
            return response;
        }
        this.userVerifys = userVerifys;
        this.member = member;
        return null;
    }
}
