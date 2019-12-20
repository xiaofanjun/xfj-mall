package com.xfj.order.rs;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.order.dto.OrderDetailInfo;
import lombok.Data;

import java.util.List;

@Data
public class OrderListRS extends AbstractResponse {

    private List<OrderDetailInfo> detailInfoList;

    /**
     * 总记录数
     */
    private Long total;

}
