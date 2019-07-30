package com.self.study.dubboproducer.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.study.dubboproducer.interfaces.DemoService;

@Service
public class Provider implements DemoService {

    @Override
    public String doSayHello(String name) {
        return name + "do  say  hello ";
    }

    @Override
    public String sayHello(String name) {
        return "annotation: hello, " + name;
    }

}
