package com.self.study.study.consumer.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.self.study.producer.server.DemoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsumerConfig {

    @Bean
    public RegistryConfig registryConfig(){
        RegistryConfig  registryConfig= new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
      /*  registryConfig.setProtocol("dubbo");*/
        return    registryConfig;
    }
    @Bean
    public ReferenceConfig referenceConfig(RegistryConfig registryConfig){
        ReferenceConfig  referenceConfig= new ReferenceConfig();
        referenceConfig.setInterface("demoService");
        /*serviceConfig.setRef(DemoService.class);*/
        referenceConfig.setApplication(new ApplicationConfig("dubbo-consumer"));
        referenceConfig.setRegistry(registryConfig);
        referenceConfig.setInterface(DemoService.class);
        return   referenceConfig;
    }

    @Bean
    public   DemoService  demoService(ReferenceConfig referenceConfig){
        DemoService demoService= (DemoService) referenceConfig.get();
        return   demoService;
    }
}
