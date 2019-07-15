package com.self.study.netty.rpc.demo.consumer;


import com.self.study.netty.rpc.client.ClientStubProxyFactory;
import com.self.study.netty.rpc.client.net.NettyNetClient;
import com.self.study.netty.rpc.demo.DemoService;
import com.self.study.netty.rpc.discovery.ZookeeperServiceInfoDiscoverer;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 消费者端示例代码
 * Consumer
 */
public class Consumer {

    /*
     * 运行代码依赖zk地址，在app.properties中配置即可
     * 配置项：zk.address=
     */
    public static void main(String[] args) throws Exception {
        send();

//        multithreadSend();
    }

    public static void send() {
        // 构建客户端stub代理
        ClientStubProxyFactory clientStubFactory = new ClientStubProxyFactory();
        clientStubFactory.setNetClient(new NettyNetClient());
        clientStubFactory.setSid(new ZookeeperServiceInfoDiscoverer());

        // 通过代理工厂获得客户端接口
        DemoService demoService = clientStubFactory.getProxy(DemoService.class);    // 获取远程服务代理

        //System.out.println(demoService);
        System.out.println("获得代理接口");

//        // 执行远程方法
//        String hello = demoService.sayHello("world");
//        System.out.println(hello); // 显示调用结果
//
//        hello = demoService.sayHello("dog");
//        System.out.println(hello); // 显示调用结果
//
//        StringBuffer buffer = new StringBuffer();
//        for (int i = 0; i < 2000; i++) {
//            buffer.append(i);
//        }
//        hello = demoService.sayHello(buffer.toString());
//        System.out.println(hello);

        for (int i = 0; i < 10000000; i++) {
            String s = demoService.sayHello(i + "");
            System.out.println(s);
        }
    }

    public static void multithreadSend() {
        int len = 100;
        CyclicBarrier cb = new CyclicBarrier(len);
        ZookeeperServiceInfoDiscoverer discoverer = new ZookeeperServiceInfoDiscoverer();
        Runnable run = new Runnable() {
            @Override
            public void run() {
                try {
                    // 构建客户端stub代理
                    ClientStubProxyFactory clientStubFactory = new ClientStubProxyFactory();
                    clientStubFactory.setNetClient(new NettyNetClient());
                    clientStubFactory.setSid(discoverer);

                    // 通过代理工厂获得客户端接口
                    DemoService demoService = clientStubFactory.getProxy(DemoService.class);    // 获取远程服务代理

                    //System.out.println(demoService);
                    System.out.println(Thread.currentThread().getName() + "-获得代理接口");
                    cb.await();
                    while (true) {
                        demoService.sayHello(Thread.currentThread().getName());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        };

        for (int i = 0; i < len; i++) {
            new Thread(run, "mt-" + i).start();
        }
    }
}
