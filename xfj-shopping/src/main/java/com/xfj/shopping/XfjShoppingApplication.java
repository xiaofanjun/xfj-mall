package com.xfj.shopping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xfj.shopping")
@SpringBootApplication
public class XfjShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfjShoppingApplication.class, args);
    }

}
