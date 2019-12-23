package com.xfj.coupon.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.xfj.coupon")
@MapperScan("com.xfj.coupon.mapper")
@EnableTransactionManagement
public class CouponProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CouponProviderApplication.class, args);
    }

}
