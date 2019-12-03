package com.xfj.commons;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @CLassNmae AppContext
 * @Description Context 工具类
 * @Author ZQ
 **/
@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

    /**
     * @return java.lang.Object
     * @Author ZQ
     * @Description 通过名字获取bean
     * @Date 2019/12/3 15:04
     * @Param [name]
     **/
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }
}
