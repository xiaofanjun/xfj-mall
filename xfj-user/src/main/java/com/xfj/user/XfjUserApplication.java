package com.xfj.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xfj.user")
@SpringBootApplication
public class XfjUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfjUserApplication.class, args);
    }

}
