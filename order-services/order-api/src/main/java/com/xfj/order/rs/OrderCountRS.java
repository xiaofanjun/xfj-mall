package com.xfj.order.rs;

import com.xfj.commons.result.AbstractResponse;
import lombok.Data;


@Data
public class OrderCountRS extends AbstractResponse {

    private int count;
}
