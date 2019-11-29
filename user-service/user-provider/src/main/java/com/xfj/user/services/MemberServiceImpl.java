package com.xfj.user.services;/**
 * Created by mic on 2019/7/30.
 */

import com.xfj.user.IMemberService;
import com.xfj.user.IUserLoginService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.MemberConverter;
import com.xfj.user.dal.entitys.Member;
import com.xfj.user.dal.persistence.MemberMapper;
import com.xfj.user.rs.CheckAuthRS;
import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.vo.CheckAuthVO;
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
    IUserLoginService userLoginService;

    @Autowired
    MemberConverter memberConverter;

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
        HeadImageRS response = new HeadImageRS();
        //TODO
        return response;
    }

    @Override
    public UpdateMemberRS updateMember(UpdateMemberVO request) {
        UpdateMemberRS response = new UpdateMemberRS();
        try {
            request.requestCheck();
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
        } catch (Exception e) {
            log.error("MemberServiceImpl.updateMember Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
