package com.xfj.user.services;

import com.xfj.user.IAddressService;
import com.xfj.user.constants.SysRetCodeConstants;
import com.xfj.user.converter.AddressConverter;
import com.xfj.user.entitys.Address;
import com.xfj.user.mapper.AddressMapper;
import com.xfj.user.rs.*;
import com.xfj.user.services.bl.AddressServiceBl;
import com.xfj.user.utils.ExceptionProcessorUtils;
import com.xfj.user.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/27 21:23
 **/
@Slf4j
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    AddressMapper addressMapper;
    @Autowired
    AddressConverter converter;
    @Autowired
    AddressServiceBl addressServiceBl;

    @Override
    public AddressListRS addressList(AddressListVO request) {
        //TODO 地址信息要做缓存处理
        AddressListRS response = new AddressListRS();
        try {
            request.requestCheck();
            Example example = new Example(Address.class);

            example.createCriteria().andEqualTo("userId", request.getUserId());
            List<Address> addresses = addressMapper.selectByExample(example);
            response.setAddressDtos(converter.address2List(addresses));
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("AddressServiceImpl.addressList occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public AddressDetailRS addressDetail(AddressDetailVO request) {
        AddressDetailRS response = new AddressDetailRS();
        try {
            request.requestCheck();
            Address address = addressMapper.selectByPrimaryKey(request.getAddressId());
            response.setAddressDto(converter.address2List(address));
            response.setCode(SysRetCodeConstants.SUCCESS.getCode());
            response.setMsg(SysRetCodeConstants.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("AddressServiceImpl.addressDetail occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public AddAddressRS createAddress(AddAddressVO request) {
        log.error("AddressServiceImpl.createAddress request :" + request);
        AddAddressRS response = null;
        try {
            request.requestCheck();
            response = addressServiceBl.createAddress(request);
        } catch (Exception e) {
            log.error("AddressServiceImpl.createAddress occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public UpdateAddressRS updateAddress(UpdateAddressVO request) {
        log.error("begin - AddressServiceImpl.updateAddress request :" + request);
        UpdateAddressRS response = null;
        try {
            request.requestCheck();
            response = addressServiceBl.updateAddress(request);
        } catch (Exception e) {
            log.error("AddressServiceImpl.updateAddress occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public DeleteAddressRS deleteAddress(DeleteAddressVO request) {
        log.error("begin - AddressServiceImpl.deleteAddress request :" + request);
        DeleteAddressRS response = null;
        try {
            request.requestCheck();
            response = addressServiceBl.deleteAddress(request);
        } catch (Exception e) {
            log.error("AddressServiceImpl.deleteAddress occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }
}
