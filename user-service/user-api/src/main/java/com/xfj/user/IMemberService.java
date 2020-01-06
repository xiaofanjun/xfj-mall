package com.xfj.user;

import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.vo.HeadImageVO;
import com.xfj.user.vo.QueryMemberVO;
import com.xfj.user.vo.UpdateMemberVO;
import org.springframework.web.multipart.MultipartFile;


public interface IMemberService {

    /**
     * 根据用户id查询用户会员信息
     *
     * @param request
     * @return
     */
    QueryMemberRS queryMemberById(QueryMemberVO request);

    /**
     * 修改用户头像
     *
     * @param request
     * @return
     */
    HeadImageRS updateHeadImage(HeadImageVO request);


    /**
     * 更新信息
     *
     * @param request
     * @return
     */
    UpdateMemberRS updateMember(UpdateMemberVO request);
}
