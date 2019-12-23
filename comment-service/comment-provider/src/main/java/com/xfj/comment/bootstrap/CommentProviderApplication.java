package com.xfj.comment.bootstrap;

import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author heps
 * @date 2019/8/11 22:42
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.xfj.comment")
@MapperScan("com.xfj.comment.mapper")
@EnableTransactionManagement
public class CommentProviderApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommentProviderApplication.class, args);
    }
}
