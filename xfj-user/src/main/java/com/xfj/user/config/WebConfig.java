package com.xfj.user.config;

import com.xfj.user.intercepter.TokenIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author ZQ
 * @Description WebMVC 配置
 * @Date 2019/10/13 20:26
 **/
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * @return com.xfj.user.intercepter.TokenIntercepter
     * @Author ZQ
     * @Description 定义拦截器
     * @Date 2019/11/29 15:46
     * @Param []
     **/
    @Bean
    public TokenIntercepter tokenIntercepter() {
        return new TokenIntercepter();
    }

    /**
     * @return void
     * @Author ZQ
     * @Description 添加拦截器
     * <p>
     * addPathPatterns: 表示 需要进行拦截的路径
     * <p>
     * excludePathPatterns: 表示 不需要进行拦截的路径, 如果前面是/**,这里需要添加这个,否则添加这个挺鸡肋的
     * <p>
     * 在本项目中vue前端对于非白名单的请求都会请求 /user/login 进行登录验证
     * @Date 2019/11/29 14:15
     * @Param [registry]
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenIntercepter())
                .addPathPatterns("/user/**");
    }
}
