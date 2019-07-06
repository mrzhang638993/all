package com.self.study.producer.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProducerConfig {


   /* @Bean
    public RegistryConfig  registryConfig(){
        RegistryConfig  registryConfig= new RegistryConfig();
        registryConfig.setAddress("zookeeper://127.0.0.1:2181");
        registryConfig.setProtocol("dubbo");
         return    registryConfig;
    }
    @Bean
    public ServiceConfig  serviceConfig(RegistryConfig registryConfig){
        ServiceConfig  serviceConfig= new ServiceConfig();
        //serviceConfig.setInterface("demoService");
        serviceConfig.setApplication(new ApplicationConfig("dubbo-producer"));
       // serviceConfig.setInterface(DemoService.class);
        serviceConfig.setRegistry(registryConfig);
       // serviceConfig.setRef(new DemoServiceImpl());
        serviceConfig.export();
        return   serviceConfig;
    }*/
}
