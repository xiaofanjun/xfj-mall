package com.xfj.user.services.bl;

import com.xfj.user.IUserLoginService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.MemberConverter;
import com.xfj.user.dal.entitys.Member;
import com.xfj.user.dal.persistence.MemberMapper;
import com.xfj.user.rs.CheckAuthRS;
import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.vo.CheckAuthVO;
import com.xfj.user.vo.HeadImageVO;
import com.xfj.user.vo.UpdateMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:21
 **/
@Slf4j
@Component
public class MemberServiceBl {

    @Autowired
    MemberMapper memberMapper;

    @Autowired
    IUserLoginService userLoginService;

    @Autowired
    MemberConverter memberConverter;

    @Transactional
    public HeadImageRS updateHeadImage(HeadImageVO request) {
        HeadImageRS response = new HeadImageRS();
        //TODO
        return response;
    }

    @Transactional
    public UpdateMemberRS updateMember(UpdateMemberVO request) {
        UpdateMemberRS response = new UpdateMemberRS();
        CheckAuthVO checkAuthRequest = new CheckAuthVO();
        checkAuthRequest.setToken(request.getToken());
        CheckAuthRS authResponse = userLoginService.validToken(checkAuthRequest);
        if (!authResponse.getCode().equals(SysRetCodeConstants.SUCCESS.getCode())) {
            response.setCode(authResponse.getCode());
            response.setMsg(authResponse.getMsg());
            return response;
        }
        Member member = memberConverter.updateReq2Member(request);
        int row = memberMapper.updateByPrimaryKeySelective(member);
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        log.info("MemberServiceImpl.updateMember effect row :" + row);
        return response;
    }
}
