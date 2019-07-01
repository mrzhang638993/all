package com.self.study.impl;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;


public class Provider {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext  context= new ClassPathXmlApplicationContext(new String[]{"classpath:dubbo/provider/provider.xml"});
        context.start();
       for(;;){

       }

    }
}
