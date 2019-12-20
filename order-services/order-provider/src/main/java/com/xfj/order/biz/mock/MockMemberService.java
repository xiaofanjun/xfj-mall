package com.xfj.order.biz.mock;


import com.xfj.user.IMemberService;
import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.vo.HeadImageVO;
import com.xfj.user.vo.QueryMemberVO;
import com.xfj.user.vo.UpdateMemberVO;

public class MockMemberService implements IMemberService {
    @Override
    public QueryMemberRS queryMemberById(QueryMemberVO queryMemberVO) {
        return null;
    }

    @Override
    public HeadImageRS updateHeadImage(HeadImageVO headImageVO) {
        return null;
    }

    @Override
    public UpdateMemberRS updateMember(UpdateMemberVO updateMemberVO) {
        return null;
    }
}
