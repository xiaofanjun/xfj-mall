package com.xfj.user.converter;

import com.xfj.user.dal.entitys.Member;
import com.xfj.user.rs.UserLoginRS;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:25
 **/
@Mapper
public interface UserConverterMapper {

    UserConverterMapper INSTANCE = Mappers.getMapper(UserConverterMapper.class);

    @Mappings({})
    UserLoginRS converter(Member member);

}
