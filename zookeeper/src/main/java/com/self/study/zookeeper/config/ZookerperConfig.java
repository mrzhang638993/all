package com.self.study.zookeeper.config;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ZookerperConfig {

    @Bean
    public ZkClient zkClient() {
        ZkClient zkClient = new ZkClient("localhost", 12000);
        //  可以进行对象的序列化操作的
        zkClient.setZkSerializer(new SerializableSerializer());
        return zkClient;
    }

    @Bean
    public CuratorFramework curatorFramework() {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString("localhost:2181").connectionTimeoutMs(12000)
                .retryPolicy(new RetryNTimes(2, 5))
                .build();
        //  必须要启动start ，否则存在异常的。状态无法检测的。
        curatorFramework.start();
        return curatorFramework;
    }

}
