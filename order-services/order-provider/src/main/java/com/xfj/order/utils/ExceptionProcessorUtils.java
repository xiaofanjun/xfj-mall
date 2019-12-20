package com.xfj.order.utils;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.ExceptionUtil;
import com.xfj.order.constant.OrderRetCode;

public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException4biz(response,e);
        } catch (Exception ex) {
            response.setCode(OrderRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(OrderRetCode.SYSTEM_ERROR.getMessage());
        }
    }
}
