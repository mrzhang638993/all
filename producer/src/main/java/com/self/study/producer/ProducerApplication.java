package com.self.study.producer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@SpringBootApplication
@ImportResource("dubbo-producer.xml")
public class ProducerApplication {

    public static void main(String[] args) {

     /* ClassPathXmlApplicationContext springApplication= new ClassPathXmlApplicationContext(new String[]{"classpath*:dubbo-producer.xml"});
        springApplication.start();*/
        SpringApplication.run(ProducerApplication.class, args);
        for (; ; ) {
            //System.out.println("coming");
        }
    }

}
