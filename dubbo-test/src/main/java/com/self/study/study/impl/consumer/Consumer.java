package com.self.study.study.impl.consumer;

import com.alibaba.dubbo.config.annotation.Reference;
import com.self.study.study.api.DemoService;
import org.springframework.stereotype.Component;

@Component("consumer")
public class Consumer {


    @Reference
    private DemoService demoService;

    public String doSayHello(String name) {
        return demoService.sayHello(name);
    }

}
