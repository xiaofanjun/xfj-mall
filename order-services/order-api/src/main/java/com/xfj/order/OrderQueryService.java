package com.xfj.order;

import com.xfj.order.rs.OrderCountRS;
import com.xfj.order.rs.OrderDetailRS;
import com.xfj.order.rs.OrderItemRS;
import com.xfj.order.rs.OrderListRS;
import com.xfj.order.vo.OrderCountVO;
import com.xfj.order.vo.OrderDetailVO;
import com.xfj.order.vo.OrderItemVO;
import com.xfj.order.vo.OrderListVO;

public interface OrderQueryService {


    /**
     * 查询指定用户下订单总数
     *
     * @param request
     * @return
     */
    OrderCountRS orderCount(OrderCountVO request);


    /**
     * 查询当前用户的历史订单列表
     *
     * @param request
     * @return
     */
    OrderListRS orderList(OrderListVO request);


    /**
     * 查询订单明细
     *
     * @param request
     * @return
     */
    OrderDetailRS orderDetail(OrderDetailVO request);

    /**
     * 查询订单条目
     *
     * @param request
     * @return
     */
    OrderItemRS orderItem(OrderItemVO request);

}
