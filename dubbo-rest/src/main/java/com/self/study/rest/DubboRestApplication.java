package com.self.study.rest;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DubboRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(DubboRestApplication.class, args);
    }

}
