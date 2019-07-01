package com.self.study.impl;

import com.self.study.api.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return  "Hello " + name;
    }
}
