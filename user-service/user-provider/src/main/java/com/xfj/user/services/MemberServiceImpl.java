package com.xfj.user.services;

import com.xfj.user.IMemberService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.MemberConverter;
import com.xfj.user.entitys.Member;
import com.xfj.user.mapper.MemberMapper;
import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.services.bl.MemberServiceBl;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.vo.HeadImageVO;
import com.xfj.user.vo.QueryMemberVO;
import com.xfj.user.vo.UpdateMemberVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:21
 **/
@Slf4j
@Service
public class MemberServiceImpl implements IMemberService {

    @Autowired
    MemberMapper memberMapper;
    @Autowired
    MemberConverter memberConverter;
    @Autowired
    private MemberServiceBl memberServiceBl;

    /**
     * 根据用户id查询用户会员信息
     *
     * @param request
     * @return
     */
    @Override
    public QueryMemberRS queryMemberById(QueryMemberVO request) {
        QueryMemberRS queryMemberResponse = new QueryMemberRS();
        try {
            request.requestCheck();
            Member member = memberMapper.selectByPrimaryKey(request.getUserId());
            if (member == null) {
                queryMemberResponse.setCode(SysRetCodeConstants.DATA_NOT_EXIST.getCode());
                queryMemberResponse.setMsg(SysRetCodeConstants.DATA_NOT_EXIST.getMessage());
            }
            queryMemberResponse = memberConverter.member2Res(member);
            queryMemberResponse.setCode(SysRetCodeConstants.SUCCESS.getCode());
            queryMemberResponse.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("MemberServiceImpl.queryMemberById Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(queryMemberResponse, e);
        }
        return queryMemberResponse;
    }

    @Override
    public HeadImageRS updateHeadImage(HeadImageVO request) {
        HeadImageRS response = null;
        try {
            request.requestCheck();
            response = memberServiceBl.updateHeadImage(request);
        } catch (Exception e) {
            log.error("MemberServiceImpl.updateHeadImage Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public UpdateMemberRS updateMember(UpdateMemberVO request) {
        log.error("MemberServiceImpl.updateMember request :" + request);
        UpdateMemberRS response = null;
        try {
            request.requestCheck();
            response = memberServiceBl.updateMember(request);
        } catch (Exception e) {
            log.error("MemberServiceImpl.updateMember Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
