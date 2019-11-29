package com.xfj.user.utils;

import com.xfj.commons.result.AbstractResponse;
import com.xfj.commons.tool.exception.ExceptionUtil;
import com.xfj.user.constants.SysRetCodeConstants;

/**
 * @Author ZQ
 * @Description
 * @Date 2019/11/26 21:56
 **/
public class ExceptionProcessorUtils {

    public static void wrapperHandlerException(AbstractResponse response, Exception e) {
        try {
            ExceptionUtil.handlerException4biz(response, e);
        } catch (Exception ex) {
            response.setCode(SysRetCodeConstants.SYSTEM_ERROR.getCode());
            response.setMsg(SysRetCodeConstants.SYSTEM_ERROR.getMessage());
        }
    }
}
