package com.xfj.commons.config;

import com.xfj.commons.annotation.ServiceLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @CLassNmae LogAspect
 * @Description 切面日志
 * @Author ZQ
 **/
@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.xfj.commons.annotation.ServiceLog)")
    public void serviceLog() {
    }

    @Before(value = "serviceLog()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        //类名
        String className = joinPoint.getTarget().getClass().getName();
        //请求方法
        String method = (className + "." + joinPoint.getSignature().getName() + "()");
        //方法参数
        String methodParam = Arrays.toString(joinPoint.getArgs());
        //方法描述
        String description = getServiceMthodDescription(joinPoint);
        log.info("Service Request  Method      : {} ", method);
        log.info("Service Request  Param       : {} ", methodParam);
        log.info("Service Request  Description : {} ", description);

    }

    @AfterThrowing(pointcut = "serviceLog()", throwing = "ex")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        log.error("Service Occur Exception : {} ", ex.toString());
    }

    /**
     * @return java.lang.String
     * @Author ZQ
     * @Description 获取注解中对方法的描述信息 用于service层注解
     * @Date 2019/12/1 18:28
     * @Param [joinPoint]
     **/
    public static String getServiceMthodDescription(JoinPoint joinPoint)
            throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ServiceLog.class).value();
                    break;
                }
            }
        }
        return description;
    }
}
