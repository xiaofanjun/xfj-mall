package com.xfj.pay.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author zq
 * @decription 启动类
 * @date 2019年8月8日 15:13:51
 */
@ComponentScan(basePackages = {"com.xfj.pay"})
@MapperScan(basePackages = "com.xfj.pay.mapper")
@SpringBootApplication
@EnableTransactionManagement
public class PayProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayProviderApplication.class, args);
    }

}
