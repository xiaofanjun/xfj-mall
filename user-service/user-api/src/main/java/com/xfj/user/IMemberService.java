package com.xfj.user;/**
 * Created by mic on 2019/7/30.
 */

import com.xfj.user.rs.HeadImageRS;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.rs.UpdateMemberRS;
import com.xfj.user.vo.HeadImageVO;
import com.xfj.user.vo.QueryMemberVO;
import com.xfj.user.vo.UpdateMemberVO;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/30-下午11:47
 * 会员服务
 */
public interface IMemberService {

    /**
     * 根据用户id查询用户会员信息
     * @param request
     * @return
     */
    QueryMemberRS queryMemberById(QueryMemberVO request);

    /**
     * 修改用户头像
     * @param request
     * @return
     */
    HeadImageRS updateHeadImage(HeadImageVO request);

    /**
     * 更新信息
     * @param request
     * @return
     */
    UpdateMemberRS updateMember(UpdateMemberVO request);
}
