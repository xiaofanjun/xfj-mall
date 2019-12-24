package com.xfj.search.bootstrap;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 搜索服务启动类
 *
 * @author jin
 */

@SpringBootApplication
@ComponentScan(basePackages = "com.xfj.search")
@MapperScan(basePackages = "com.xfj.search.mapper")
@EnableElasticsearchRepositories(basePackages = "com.xfj.search.repository")
public class SearchProviderApplication {

    public static void main(String[] args) {
        System.setProperty("es.set.netty.runtime.available.processors", "false");
        SpringApplication.run(SearchProviderApplication.class, args);
    }

}

