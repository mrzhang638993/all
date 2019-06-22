package com.sellf.consumer.services;

import com.self.producer.server.DemoService;
import jdk.nashorn.internal.ir.annotations.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestDemoConsumer {


    @GetMapping("/test")
    @ResponseBody
    public String   test(){
        System.out.println("coming");
        return   demoService.sayHello();
    }
    @Reference
    private DemoService  demoService;

}
