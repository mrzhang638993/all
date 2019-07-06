package com.self.study.producer.server.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.study.producer.server.DemoService;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Service
@Component
public class DemoServiceImpl implements DemoService, Serializable {
    public String sayHello() {
        return "hello";
    }
}
