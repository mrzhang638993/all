package com.self.study.study.impl;

import com.self.study.study.api.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class Consumer {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo/consumer/consumer.xml"});
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        String mrzhang = demoService.sayHello("mrzhang");
        System.out.println(mrzhang);
        System.in.read();
    }
}
