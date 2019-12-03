package com.xfj.user.converter;

import com.xfj.user.entitys.Address;
import com.xfj.user.dto.AddressDto;
import com.xfj.user.vo.AddAddressVO;
import com.xfj.user.vo.UpdateAddressVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/27 21:06
 **/
@Mapper(componentModel = "spring")
public interface AddressConverter {

    @Mappings({})
    AddressDto address2List(Address addresses);

    /*@Mappings({})
    AddressDto address2Res(Address address);*/

    List<AddressDto> address2List(List<Address> addresses);

    @Mappings({})
    Address req2Address(AddAddressVO request);

    @Mappings({})
    Address req2Address(UpdateAddressVO request);
}
