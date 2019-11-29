package com.xfj.user.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.user.dto.AddressDto;
import lombok.Data;

import java.util.List;

/**
 * @Author ZQ
 * @Description //TODO
 * @Date 2019/11/27 21:15
 **/
@Data
public class AddressListRS extends AbstractResponse {

    private List<AddressDto> addressDtos;
}
