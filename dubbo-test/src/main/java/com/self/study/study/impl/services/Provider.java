package com.self.study.study.impl.services;

import com.alibaba.dubbo.config.annotation.Service;
import com.self.study.study.api.DemoService;

@Service
public class Provider  implements DemoService {

    @Override
    public String sayHello(String name) {
        return "annotation: hello, " + name;
    }

}
