package com.xfj.order.biz.handler;

import com.xfj.order.biz.TransOutboundInvoker;

public interface TransPipeline extends TransOutboundInvoker {

    /**
     * @param handlers
     */
    void addFirst(TransHandler... handlers);

    /**
     * @param handlers
     */
    void addLast(TransHandler... handlers);
}
