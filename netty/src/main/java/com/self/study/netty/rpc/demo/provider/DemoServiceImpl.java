package com.self.study.netty.rpc.demo.provider;


import com.self.study.netty.rpc.demo.DemoService;
import com.self.study.netty.rpc.server.Service;


@Service(DemoService.class)
public class DemoServiceImpl implements DemoService {
    /**
     * 代码实现
     */
    public String sayHello(String name) {
        return "Hello " + name;
    }
}
