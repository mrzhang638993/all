package com.self.study.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) throws IOException {

        ClassPathXmlApplicationContext springApplication= new ClassPathXmlApplicationContext(new String[]{"classpath*:dubbo-producer.xml"});
        springApplication.start();
        System.in.read();
    }

}
