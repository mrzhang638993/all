package com.self.study.producer.server.impl;

import com.self.study.producer.server.DemoService;

public class DemoServiceImpl implements DemoService {
    public String sayHello(String name) {
        return "hello";
    }
}
