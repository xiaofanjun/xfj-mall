package com.xfj.cashier;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.xfj.cashier")
@SpringBootApplication
public class XfjCashierApplication {

    public static void main(String[] args) {
        SpringApplication.run(XfjCashierApplication.class, args);
    }

}
