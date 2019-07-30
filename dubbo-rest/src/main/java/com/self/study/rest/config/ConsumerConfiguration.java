package com.self.study.rest.config;


import com.alibaba.dubbo.config.*;
import com.self.study.dubboproducer.interfaces.DemoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;




//  消费信息
@Configuration
public class ConsumerConfiguration {


    //  协议配置
   /* @Bean(name="consumerProtocol")
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig= new ProtocolConfig();
        protocolConfig.setName("dubbo");
        return  protocolConfig;
    }*/



    //  注册中心配置
    @Bean("consumerRegistry")
    public RegistryConfig  registryConfig(){
        RegistryConfig  registryConfig= new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        //  设置注册和消费
        registryConfig.setRegister(true);
        registryConfig.setSubscribe(true);
        registryConfig.setAddress("127.0.0.1");
        return   registryConfig;
    }


    //  设置服务实例信息
    @Bean(name="consumerApplication")
    public ApplicationConfig applicationConfig(){
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-consumer");
        return   application;
    }

    @Bean(name="consumerConfig")
    public ConsumerConfig consumerConfig(){
        ConsumerConfig  consumerConfig= new ConsumerConfig();
        consumerConfig.setTimeout(20000);
        return  consumerConfig;
    }

    //  完成对应的实现相关的dubbo   reference  config的相关操作
    @Bean
    @ConditionalOnClass({RegistryConfig.class,ApplicationConfig.class,ConsumerConfig.class})
    public ReferenceConfig  referenceConfig(@Qualifier("consumerRegistry") RegistryConfig  registryConfig, @Qualifier("consumerApplication") ApplicationConfig  applicationConfig,@Qualifier("consumerConfig")ConsumerConfig  consumerConfig){
        ReferenceConfig  referenceConfig= new ReferenceConfig();
        referenceConfig.setApplication(applicationConfig);
        referenceConfig.setRegistry(registryConfig);
        // 设置消费接口
        referenceConfig.setInterface(DemoService.class);
        //  设置消费端的配置参数信息
        referenceConfig.setConsumer(consumerConfig);
        referenceConfig.setProtocol("dubbo");
        return  referenceConfig;
    }
}
