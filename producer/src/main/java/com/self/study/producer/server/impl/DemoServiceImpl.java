package com.self.study.producer.server.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.study.producer.server.DemoService;

import java.io.Serializable;

@Service
public class DemoServiceImpl implements DemoService, Serializable {
    public String sayHello() {
        return "hello";
    }
}
