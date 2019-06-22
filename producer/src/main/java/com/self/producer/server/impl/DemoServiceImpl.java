package com.self.producer.server.impl;

import com.self.producer.server.DemoService;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class DemoServiceImpl implements DemoService, Serializable {
    public String sayHello( ) {
        return "hello";
    }
}
