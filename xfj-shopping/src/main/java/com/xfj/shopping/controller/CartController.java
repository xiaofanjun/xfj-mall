package com.xfj.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfj.commons.result.ResponseData;
import com.xfj.commons.result.ResponseUtil;
import com.xfj.shopping.ICartService;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.form.CartForm;
import com.xfj.shopping.rs.*;
import com.xfj.shopping.vo.*;
import com.xfj.user.intercepter.TokenIntercepter;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/7/23-18:52
 */
@RestController
@RequestMapping("/shopping")
@Api(tags = "CartController", description = "购物车控制层")
public class CartController {

    @Reference(timeout = 3000, group = "${dubbo-group.name}")
    ICartService iCartService;

    /**
     * 获得购物车列表
     *
     * @param request
     * @return
     */
    @GetMapping("/carts")
    @ApiOperation("获得购物车列表")
    public ResponseData carts(HttpServletRequest request) {
        String userInfo = (String) request.getAttribute(TokenIntercepter.USER_INFO_KEY);
        JSONObject jsonObject = JSON.parseObject(userInfo);
        String uid = jsonObject.getString("uid");
        CartListByIdVO cartListByIdRequest = new CartListByIdVO();
        cartListByIdRequest.setUserId(uid);
        CartListByIdRS response = iCartService.getCartListById(cartListByIdRequest);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getCartProductDtos());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 添加商品到购物车
     *
     * @param cartForm
     * @return
     */
    @PostMapping("/carts")
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParam(name = "cartForm", value = "购物车信息", dataType = "CartForm", required = true)
    public ResponseData carts(@RequestBody CartForm cartForm) {
        AddCartVO request = new AddCartVO();
        request.setItemId(cartForm.getProductId());
        request.setNum(cartForm.getProductNum());
        request.setUserId(cartForm.getUserId());
        AddCartRS response = iCartService.addToCart(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 更新购物车中的商品
     *
     * @param cartForm
     * @return
     */
    @PutMapping("/carts")
    @ApiOperation("更新购物车中的商品")
    @ApiImplicitParam(name = "cartForm", value = "购物车信息", dataType = "CartForm", required = true)
    public ResponseData updateCarts(@RequestBody CartForm cartForm) {
        UpdateCartNumVO request = new UpdateCartNumVO();
        request.setChecked(cartForm.getChecked());
        request.setItemId(cartForm.getProductId());
        request.setNum(cartForm.getProductNum());
        request.setUserId(cartForm.getUserId());
        UpdateCartNumRS response = iCartService.updateCartNum(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 删除购物车中的商品
     *
     * @return
     */
    @ApiOperation("删除购物车中的商品")
    @DeleteMapping("/carts/{uid}/{pid}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "uid", value = "用户ID", paramType = "path"),
            @ApiImplicitParam(name = "pid", value = "商品ID", paramType = "path")
    })
    public ResponseData deleteCarts(@PathVariable("uid") String uid, @PathVariable("pid") String pid) {
        DeleteCartItemVO request = new DeleteCartItemVO();
        request.setUserId(uid);
        request.setItemId(pid);

        DeleteCartItemRS response = iCartService.deleteCartItem(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 对购物车的全选操作
     *
     * @param cartForm
     * @return
     */
    @ApiOperation("对购物车的全选操作")
    @PutMapping("/items")
    @ApiImplicitParam(name = "cartForm", value = "购物车信息", dataType = "CartForm", required = true)
    public ResponseData checkCarts(@RequestBody CartForm cartForm) {
        CheckAllItemVO request = new CheckAllItemVO();
        request.setChecked(cartForm.getChecked());
        request.setUserId(cartForm.getUserId());
        CheckAllItemRS response = iCartService.checkAllCartItem(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }

    /**
     * 删除购物车中选中的商品
     *
     * @param id
     * @return
     */
    @ApiOperation("删除购物车中选中的商品")
    @DeleteMapping("/items/{id}")
    @ApiImplicitParam(name = "id", value = "商品ID", paramType = "path")
    public ResponseData deleteCheckCartItem(@PathVariable("id") String id) {
        DeleteCheckedItemVO request = new DeleteCheckedItemVO();
        request.setUserId(id);
        request.setUserId(request.getUserId());
        DeleteCheckedItemRS response = iCartService.deleteCheckedItem(request);
        if (response.getCode().equals(ShoppingRetCode.SUCCESS.getCode())) {
            return new ResponseUtil().setData(response.getMsg());
        }
        return new ResponseUtil().setErrorMsg(response.getMsg());
    }


}
