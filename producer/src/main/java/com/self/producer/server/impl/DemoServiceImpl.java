package com.self.producer.server.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.producer.server.DemoService;

import java.io.Serializable;

@Service
public class DemoServiceImpl implements DemoService, Serializable {
    public String sayHello() {
        return "hello";
    }
}
