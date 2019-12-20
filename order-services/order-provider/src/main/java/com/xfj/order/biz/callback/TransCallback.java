package com.xfj.order.biz.callback;

import com.xfj.order.biz.context.TransHandlerContext;

public interface TransCallback {

    void onDone(TransHandlerContext context);
}
