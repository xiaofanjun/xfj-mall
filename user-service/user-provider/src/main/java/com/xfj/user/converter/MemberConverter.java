package com.xfj.user.converter;

import com.xfj.user.entitys.Member;
import com.xfj.user.rs.QueryMemberRS;
import com.xfj.user.vo.UpdateMemberVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:25
 **/
@Mapper(componentModel = "spring")
public interface MemberConverter {

    @Mappings({})
    QueryMemberRS member2Res(Member member);

    @Mappings({})
    Member updateReq2Member(UpdateMemberVO request);
}
