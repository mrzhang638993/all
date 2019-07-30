package com.self.study.rest.config;

import com.alibaba.dubbo.config.*;
import com.self.study.rest.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//  对外提供rest服务的配置类信息
@Configuration
public class RestProducerConfig {

    //  协议配置
    @Bean(name="restProtocol")
    public ProtocolConfig protocolConfig(){
        ProtocolConfig protocolConfig= new ProtocolConfig();
        protocolConfig.setName("rest");
        protocolConfig.setPort(8080);
        return  protocolConfig;
    }

    //  注册中心配置
    @Bean("restRegistry")
    public RegistryConfig registryConfig(){
        RegistryConfig  registryConfig= new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        //  设置注册和消费
        registryConfig.setRegister(true);
        registryConfig.setSubscribe(true);
        registryConfig.setAddress("127.0.0.1");
        return   registryConfig;
    }


    //  设置服务实例信息
    @Bean(name="restApplication")
    public ApplicationConfig applicationConfig(){
        ApplicationConfig application = new ApplicationConfig();
        application.setName("dubbo-rest");
        return   application;
    }

   @Bean(name="providerConfig")
   public  ProviderConfig  providerConfig(){
       ProviderConfig  providerConfig= new ProviderConfig();
       providerConfig.setWait(20000);
       providerConfig.setDispatcher("all");
       providerConfig.setThreadpool("fixed");
       providerConfig.setIothreads(5);
       return   providerConfig;
   }

    @Bean
    @ConditionalOnClass({ProtocolConfig.class,RegistryConfig.class,ApplicationConfig.class,ProviderConfig.class})
   public ServiceConfig serviceConfig(@Qualifier("restProtocol") ProtocolConfig protocolConfig, @Qualifier("restRegistry") RegistryConfig  registryConfig, @Qualifier("restApplication") ApplicationConfig  applicationConfig,@Qualifier("providerConfig") ProviderConfig  providerConfig){
       ServiceConfig  serviceConfig= new ServiceConfig();
       serviceConfig.setProvider(providerConfig);
       //  设置对外提供服务的接口信息
        serviceConfig.setInterface(UserService.class);
        serviceConfig.setRegister(true);
        serviceConfig.setApplication(applicationConfig);
       return   serviceConfig;
   }

}
