package com.sellf.consumer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.self.producer.server.DemoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@SpringBootApplication
@EnableDubbo
public class ConsumerApplication {


    public static void main(String[] args)  {

        SpringApplication.run(ConsumerApplication.class,args);
        for (;;){
           // System.out.println("coming");
        }

     /*  ClassPathXmlApplicationContext springApplication= new ClassPathXmlApplicationContext(new String[]{"classpath*:dubbo-consumer.xml"});
        springApplication.start();
        DemoService demoService = (DemoService) springApplication.getBean("demoService");
        System.out.println(demoService.sayHello()+"###########");*/
    }

}
