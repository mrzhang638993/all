package com.self.study.study.consumer.services;

import com.alibaba.dubbo.config.annotation.Reference;
import com.self.study.producer.server.DemoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestDemoConsumer {


    @GetMapping("/test")
    @ResponseBody
    public String test() {
        System.out.println("coming");
        return demoService.sayHello();
    }

    @Reference
    private DemoService demoService;

}
