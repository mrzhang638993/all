package com.self.producer.server.impl;

import com.self.producer.server.DemoService;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class DemoServiceImpl implements DemoService, Serializable {
    public String sayHello( ) {
        return "hello";
    }
}
