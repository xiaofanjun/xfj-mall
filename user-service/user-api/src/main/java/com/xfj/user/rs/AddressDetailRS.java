package com.xfj.user.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.user.dto.AddressDto;
import lombok.Data;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:14
 **/
@Data
public class AddressDetailRS extends AbstractResponse {
    private AddressDto addressDto;

}
