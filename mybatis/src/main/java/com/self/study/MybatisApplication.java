package com.self.study;


//  备注：对应的导包的信息需要正确的，导包错误的话，底层的话是无法实现实例化的操作的。

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MybatisApplication.class, args);
    }

}
