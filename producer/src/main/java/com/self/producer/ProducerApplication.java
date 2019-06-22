package com.self.producer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
@EnableDubbo
public class ProducerApplication {

    public static void main(String[] args)  {

      /*  ClassPathXmlApplicationContext springApplication= new ClassPathXmlApplicationContext(new String[]{"classpath*:dubbo-producer.xml"});
        springApplication.start();*/
        SpringApplication.run(ProducerApplication.class,args);
       for (;;){
           //System.out.println("coming");
       }
    }

}
