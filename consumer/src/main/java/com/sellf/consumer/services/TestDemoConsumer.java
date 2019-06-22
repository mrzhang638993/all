package com.sellf.consumer.services;

import com.self.producer.server.DemoService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Component;

@Component
public class TestDemoConsumer {
    @Reference
    private DemoService  demoService;

    public  void   test(){
        System.out.println(demoService.sayHello());
    }
}
