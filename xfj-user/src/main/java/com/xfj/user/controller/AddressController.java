package com.xfj.user.controller;/**
 * Created by Zq on 2019/7/31.
 */

import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Zq 老师
 * create-date: 2019/7/31-上午10:03
 */
@RestController
@RequestMapping("/address")
public class AddressController {


    /**
     * 获取地址列表信息
     * @return
     */
    @GetMapping("/address")
    public ResponseData address(){
        return new ResponseUtil<>().setData(null);
    }


}
