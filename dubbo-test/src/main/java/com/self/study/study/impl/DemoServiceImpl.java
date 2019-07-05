package com.self.study.study.impl;

import com.self.study.study.api.DemoService;

public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        return  "Hello " + name;
    }
}
