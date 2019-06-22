package com.sellf.study.consumer;

import com.self.study.producer.server.DemoService;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class ConsumerApplication {


    public static void main(String[] args) {

       ClassPathXmlApplicationContext springApplication= new ClassPathXmlApplicationContext(new String[]{"classpath*:dubbo-consumer.xml"});
        springApplication.start();
        DemoService demoService = (DemoService) springApplication.getBean("demoService");
        System.out.println(demoService.sayHello("mrzhang")+"###########");
    }

}
