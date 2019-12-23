package com.xfj.pay.utils;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.ExceptionUtil;
import com.xfj.pay.constants.PayReturnCodeEnum;

public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException4biz(response,e);
        } catch (Exception ex) {
            response.setCode(PayReturnCodeEnum.SYSTEM_ERROR.getCode());
            response.setMsg(PayReturnCodeEnum.SYSTEM_ERROR.getMsg());
        }
    }
}
