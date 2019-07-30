package com.self.study.dubboproducer.config;

import com.alibaba.dubbo.config.*;
import com.self.study.dubboproducer.interfaces.DemoService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;


//  对外提供dubbo服务的配置类信息
@Configuration
public class DubboProducerConfig {

    //  协议配置
    @Bean(name = "dubboProtocol")
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setName("dubbo");
        protocolConfig.setPort(10080);
        return protocolConfig;
    }

    //  注册中心配置
    @Bean("dubboRegistry")
    public RegistryConfig registryConfig() {
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        //  设置注册和消费
        registryConfig.setRegister(true);
        //registryConfig.setSubscribe(true);
        registryConfig.setAddress("127.0.0.1");
        return registryConfig;
    }


    //  设置服务实例信息
    @Bean(name = "dubboApplication")
    public ApplicationConfig applicationConfig() {
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-rest");
        return application;
    }

    @Bean(name = "dubboProvider")
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setWait(20000);
        providerConfig.setDispatcher("all");
        providerConfig.setThreadpool("fixed");
        providerConfig.setIothreads(5);
        return providerConfig;
    }

    @Bean
    @ConditionalOnClass({ProtocolConfig.class, RegistryConfig.class, ApplicationConfig.class, ProviderConfig.class})
    public ServiceConfig serviceConfig(@Qualifier("dubboProtocol") ProtocolConfig protocolConfig, @Qualifier("dubboRegistry") RegistryConfig registryConfig, @Qualifier("dubboApplication") ApplicationConfig applicationConfig, @Qualifier("dubboProvider") ProviderConfig providerConfig) {
        ServiceConfig serviceConfig = new ServiceConfig();
        serviceConfig.setProvider(providerConfig);
        //  设置对外提供服务的接口信息
        serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRegister(true);
        serviceConfig.setApplication(applicationConfig);
        serviceConfig.setRegistry(registryConfig);
        List<ProtocolConfig> protocolConfigs= new ArrayList<>();
        protocolConfigs.add(protocolConfig);
        serviceConfig.setProtocols(protocolConfigs);
        return serviceConfig;
    }

}
