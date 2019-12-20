package com.xfj.order;

import com.xfj.order.rs.CancelOrderRS;
import com.xfj.order.rs.CreateOrderRS;
import com.xfj.order.rs.DeleteOrderRS;
import com.xfj.order.vo.CancelOrderVO;
import com.xfj.order.vo.CreateOrderVO;
import com.xfj.order.vo.DeleteOrderVO;

public interface OrderCoreService {

    /**
     * 创建订单
     * @param request
     * @return
     */
    CreateOrderRS createOrder(CreateOrderVO request);

    /**
     * 取消订单
     * @param request
     * @return
     */
    CancelOrderRS cancelOrder(CancelOrderVO request);


    /**
     * 删除订单
     * @param request
     * @return
     */
    DeleteOrderRS deleteOrder(DeleteOrderVO request);


    void updateOrder(Integer status,String orderId);

    /**
     * 删除订单与交易
     * @param request
     */
    void deleteOrderWithTransaction(DeleteOrderVO request);


}
