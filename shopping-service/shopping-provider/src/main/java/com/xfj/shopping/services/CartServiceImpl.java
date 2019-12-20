package com.xfj.shopping.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfj.shopping.ICartService;
import com.xfj.shopping.constant.GlobalConstants;
import com.xfj.shopping.constants.ShoppingRetCode;
import com.xfj.shopping.converter.CartItemConverter;
import com.xfj.shopping.entitys.Item;
import com.xfj.shopping.mapper.ItemMapper;
import com.xfj.shopping.rs.*;
import com.xfj.shopping.utils.ExceptionProcessorUtils;
import com.xfj.shopping.dto.*;
import com.xfj.shopping.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    RedissonClient redissonClient;
    @Autowired
    ItemMapper itemMapper;

    /**
     * 根据用户id获得购物车中的商品列表
     *
     * @param request
     * @return
     */
    @Override
    public CartListByIdRS getCartListById(CartListByIdVO request) {
        CartListByIdRS response = new CartListByIdRS();
        List<CartProductDto> productDtos = new ArrayList<>();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {
            Map<Object, Object> items = redissonClient.getMap(generatorCartItemKey(request.getUserId()));
            items.values().forEach(obj -> {
                CartProductDto cartProductDto = JSONObject.parseObject(obj.toString(), CartProductDto.class);
                productDtos.add(cartProductDto);
            });
            response.setCartProductDtos(productDtos);
        } catch (Exception e) {
            log.error("CartServiceImpl.getCartListById Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }


    @Override
    public AddCartRS addToCart(AddCartVO request) {
        AddCartRS response = new AddCartRS();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {
            request.requestCheck();
            boolean exists = redissonClient.getMap(generatorCartItemKey(request.getUserId())).containsKey(request.getItemId());
            if (exists) {
                String cartItemJson = redissonClient.getMap(generatorCartItemKey(request.getUserId())).get(request.getItemId()).toString();
                CartProductDto cartProductDto = JSON.parseObject(cartItemJson, CartProductDto.class);
                cartProductDto.setProductNum(cartProductDto.getProductNum().longValue() + request.getNum().longValue());
                redissonClient.getMap(generatorCartItemKey(request.getUserId())).put(request.getItemId(), JSON.toJSON(cartProductDto).toString());
                return response;
            }
            Item item = itemMapper.selectByPrimaryKey(request.getItemId());
            if (item != null) {
                CartProductDto cartProductDto = CartItemConverter.item2Dto(item);
                cartProductDto.setChecked("true");
                cartProductDto.setProductNum(request.getNum().longValue());
                redissonClient.getMap(generatorCartItemKey(request.getUserId())).put(request.getItemId(), JSON.toJSON(cartProductDto).toString());
                return response;
            }
            response.setCode(ShoppingRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(ShoppingRetCode.SYSTEM_ERROR.getMessage());
        } catch (Exception e) {
            log.error("CartServiceImpl.addToCart Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public UpdateCartNumRS updateCartNum(UpdateCartNumVO request) {
        UpdateCartNumRS response = new UpdateCartNumRS();
        response.setCode(ShoppingRetCode.SUCCESS.getCode());
        response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        try {
            RMap itemMap = redissonClient.getMap(generatorCartItemKey(request.getUserId()));
            Object item = itemMap.get(request.getItemId());
            if (item != null) {
                CartProductDto cartProductDto = JSON.parseObject(item.toString(), CartProductDto.class);
                cartProductDto.setChecked(request.getChecked());
                cartProductDto.setProductNum(request.getNum().longValue());
                itemMap.put(request.getItemId(), JSON.toJSON(cartProductDto));
            }
        } catch (Exception e) {
            log.error("CartServiceImpl.updateCartNum Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public CheckAllItemRS checkAllCartItem(CheckAllItemVO request) {
        CheckAllItemRS response = new CheckAllItemRS();
        try {
            RMap items = redissonClient.getMap(generatorCartItemKey(request.getUserId()));
            items.values().forEach(obj -> {
                CartProductDto cartProductDto = (CartProductDto) obj;
                cartProductDto.setChecked(request.getChecked());//true / false
                items.put(cartProductDto.getProductId(), cartProductDto);
            });
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("CartServiceImpl.checkAllCartItem Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public DeleteCartItemRS deleteCartItem(DeleteCartItemVO request) {
        DeleteCartItemRS response = new DeleteCartItemRS();
        try {
            RMap rMap = redissonClient.getMap(generatorCartItemKey(request.getUserId()));
            rMap.remove(request.getItemId());
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("CartServiceImpl.deleteCartItem Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public DeleteCheckedItemRS deleteCheckedItem(DeleteCheckedItemVO request) {
        DeleteCheckedItemRS response = new DeleteCheckedItemRS();
        try {
            RMap itemMap = redissonClient.getMap(generatorCartItemKey(request.getUserId()));
            itemMap.values().forEach(obj -> {
                CartProductDto cartProductDto = JSON.parseObject(obj.toString(), CartProductDto.class);
                if ("true".equals(cartProductDto.getChecked())) {
                    itemMap.remove(cartProductDto.getProductId());
                }
            });
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("CartServiceImpl.deleteCheckedItem Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    @Override
    public ClearCartItemRS clearCartItemByUserID(ClearCartItemVO request) {
        ClearCartItemRS response = new ClearCartItemRS();
        try {
            RMap itemMap = redissonClient.getMap(generatorCartItemKey(request.getUserId()));
            itemMap.values().forEach(obj -> {
                CartProductDto cartProductDto = JSON.parseObject(obj.toString(), CartProductDto.class);
                if (request.getProductIds().contains(cartProductDto.getProductId())) {
                    itemMap.remove(cartProductDto.getProductId());
                }
            });
            response.setCode(ShoppingRetCode.SUCCESS.getCode());
            response.setMsg(ShoppingRetCode.SUCCESS.getMessage());
        } catch (Exception e) {
            log.error("CartServiceImpl.clearCartItemByUserID Occur Exception :" + e);
            ExceptionProcessorUtils.wrapperHandlerException(response, e);
        }
        return response;
    }

    private String generatorCartItemKey(String userId) {
        StringBuilder sb = new StringBuilder(GlobalConstants.CART_ITEM_CACHE_PREFIX);
        sb.append(":").append(userId);
        return sb.toString();
    }

}
