package com.self.study.impl.config;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.self.study.api.DemoService;
import com.self.study.impl.consumer.Consumer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
//  启动相关的dubbo服务
@EnableDubbo(scanBasePackages = {"com.self.study.impl.consumer"})
@PropertySource("classpath:dubbo/consumer/dubbo-consumer.properties")
//  扫描对应的接口信息,形成对应的bean
@ComponentScan(value = "com.self.study.impl.consumer")
public class ConsumerConfiguration {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConsumerConfiguration.class);
        context.start();
        final Consumer demoService = (Consumer) context.getBean("consumer");
        System.out.println("hello :" + demoService.doSayHello("world"));
        System.out.println("goodbye :" + demoService.doSayHello("world"));
        System.out.println("greeting :" + demoService.doSayHello("world"));
        System.out.println("reply :" + demoService.doSayHello("world"));
    }
}
