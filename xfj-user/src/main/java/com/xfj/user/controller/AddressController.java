package com.xfj.user.controller;

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ZQ
 * @Description 地址
 * @Date 2019/11/27 20:24
 **/
@RestController
@RequestMapping("/address")
public class AddressController {


    /**
     * 获取地址列表信息
     * todo
     *
     * @return
     */
    @GetMapping("/address")
    public ResponseData address() {
        return new ResponseUtil<>().setData(null);
    }


}
