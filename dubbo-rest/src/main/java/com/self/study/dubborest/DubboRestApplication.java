package com.self.study.dubborest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class DubboRestApplication {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context= new ClassPathXmlApplicationContext(new String[]{"config.xml"});
         context.start();
        System.in.read();
    }

}
