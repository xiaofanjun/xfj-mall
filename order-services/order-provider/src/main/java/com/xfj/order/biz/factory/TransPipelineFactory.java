package com.xfj.order.biz.factory;

import com.xfj.order.biz.TransOutboundInvoker;

public interface TransPipelineFactory<T> {

    TransOutboundInvoker build(T obj);
}
