package com.self.producer.server;

import com.alibaba.dubbo.config.annotation.Service;

@Service
public interface DemoService {
    String sayHello( );
}