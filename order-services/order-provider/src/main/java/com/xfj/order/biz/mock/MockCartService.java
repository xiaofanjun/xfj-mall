package com.xfj.order.biz.mock;


import com.xfj.shopping.ICartService;
import com.xfj.shopping.rs.*;
import com.xfj.shopping.vo.*;

public class MockCartService implements ICartService {
    @Override
    public CartListByIdRS getCartListById(CartListByIdVO cartListByIdVO) {
        return null;
    }

    @Override
    public AddCartRS addToCart(AddCartVO addCartVO) {
        return null;
    }

    @Override
    public UpdateCartNumRS updateCartNum(UpdateCartNumVO updateCartNumVO) {
        return null;
    }

    @Override
    public CheckAllItemRS checkAllCartItem(CheckAllItemVO checkAllItemVO) {
        return null;
    }

    @Override
    public DeleteCartItemRS deleteCartItem(DeleteCartItemVO deleteCartItemVO) {
        return null;
    }

    @Override
    public DeleteCheckedItemRS deleteCheckedItem(DeleteCheckedItemVO deleteCheckedItemVO) {
        return null;
    }

    @Override
    public ClearCartItemRS clearCartItemByUserID(ClearCartItemVO clearCartItemVO) {
        return null;
    }
}
