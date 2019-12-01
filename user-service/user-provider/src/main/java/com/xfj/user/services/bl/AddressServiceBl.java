package com.xfj.user.services.bl;

import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.AddressConverter;
import com.xfj.user.dal.entitys.Address;
import com.xfj.user.dal.persistence.AddressMapper;
import com.xfj.user.rs.*;
import com.xfj.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:23
 **/
@Slf4j
@Component
public class AddressServiceBl {

    @Autowired
    AddressMapper addressMapper;

    @Autowired
    AddressConverter converter;

    @Transactional
    public AddAddressRS createAddress(AddAddressVO request) {
        AddAddressRS response = new AddAddressRS();
        checkAddressDefaultUnique(request.getIsDefault() != null && request.getIsDefault() == 1, request.getUserId());
        Address address = converter.req2Address(request);
        int row = addressMapper.insert(address);
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        log.info("AddressServiceImpl.createAddress effect row :" + row);
        return response;
    }

    @Transactional
    public UpdateAddressRS updateAddress(UpdateAddressVO request) {
        UpdateAddressRS response = new UpdateAddressRS();
        checkAddressDefaultUnique(request.getIsDefault() == 1, request.getUserId());
        Address address = converter.req2Address(request);
        int row = addressMapper.updateByPrimaryKey(address);
        response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        response.setCode(SysRetCodeConstants.SUCCESS.getCode());
        log.info("AddressServiceImpl.createAddress effect row :" + row);
        return response;
    }

    @Transactional
    public DeleteAddressRS deleteAddress(DeleteAddressVO request) {
        DeleteAddressRS response = new DeleteAddressRS();
        int row = addressMapper.deleteByPrimaryKey(request.getAddressId());
        if (row > 0) {
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        } else {
            response.setCode(SysRetCodeConstants.DATA_NOT_EXIST.getCode());
            response.setMsg(SysRetCodeConstants.DATA_NOT_EXIST.getMessage());
        }
        log.info("AddressServiceImpl.deleteAddress effect row :" + row);
        return response;
    }

    //地址只能有一个默认
    private void checkAddressDefaultUnique(boolean isDefault, Long userId) {
        if (isDefault) {
            Example example = new Example(Address.class);
            example.createCriteria().andEqualTo("userId", userId);
            List<Address> addresses = addressMapper.selectByExample(example);
            addresses.parallelStream().forEach(address -> {
                if (address.getIsDefault() == 1) {
                    address.setIsDefault(1);
                    addressMapper.updateByPrimaryKey(address);
                }
            });
        }
    }
}
