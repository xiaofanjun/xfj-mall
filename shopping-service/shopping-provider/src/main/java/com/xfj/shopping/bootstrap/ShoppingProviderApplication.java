package com.xfj.shopping.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@MapperScan(basePackages = "com.xfj.shopping.dal")
@ComponentScan(basePackages = "com.xfj.shopping")
@SpringBootApplication
@EnableTransactionManagement
public class ShoppingProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingProviderApplication.class, args);
    }

}
