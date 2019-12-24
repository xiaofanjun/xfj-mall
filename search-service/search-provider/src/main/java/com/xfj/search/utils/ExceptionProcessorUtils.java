package com.xfj.search.utils;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.ExceptionUtil;
import com.xfj.search.consts.SearchRetCode;

public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e){
        try {
            ExceptionUtil.handlerException4biz(response,e);
        } catch (Exception ex) {
            response.setCode(SearchRetCode.SYSTEM_ERROR.getCode());
            response.setMsg(SearchRetCode.SYSTEM_ERROR.getMsg());
        }
    }
}
