package com.xfj.shopping;

import com.xfj.shopping.rs.*;
import com.xfj.shopping.vo.*;


public interface ICartService {


    CartListByIdRS getCartListById(CartListByIdVO request);

    /**
     * 添加商品到购物车
     *
     * @param request
     * @return
     */
    AddCartRS addToCart(AddCartVO request);


    /**
     * 更新购物车中商品的数量
     *
     * @param request
     * @return
     */
    UpdateCartNumRS updateCartNum(UpdateCartNumVO request);

    /**
     * 选择购物车中的所有商品
     *
     * @param request
     * @return
     */
    CheckAllItemRS checkAllCartItem(CheckAllItemVO request);

    /**
     * 删除购物车中的商品
     *
     * @param request
     * @return
     */
    DeleteCartItemRS deleteCartItem(DeleteCartItemVO request);

    /**
     * 删除选中的商品
     *
     * @param request
     * @return
     */
    DeleteCheckedItemRS deleteCheckedItem(DeleteCheckedItemVO request);


    /**
     * 清空指定用户的购物车缓存(用户下完订单之后清理）
     *
     * @param request
     * @return
     */
    ClearCartItemRS clearCartItemByUserID(ClearCartItemVO request);


}
