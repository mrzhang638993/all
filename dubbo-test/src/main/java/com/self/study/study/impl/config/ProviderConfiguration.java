package com.self.study.study.impl.config;

import com.alibaba.dubbo.config.ProviderConfig;
import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableDubbo(scanBasePackages = {"com.self.study.impl.services"})
@PropertySource("classpath:dubbo/provider/dubbo-provider.properties")
public class ProviderConfiguration {

    @Bean
    public ProviderConfig providerConfig() {
        ProviderConfig providerConfig = new ProviderConfig();
        providerConfig.setTimeout(1000);
        // 增加服务端的校验功能
        providerConfig.setValidation("jvalidation");
        return providerConfig;
    }

    public static void main(String[] args) throws Exception {
        new EmbeddedZooKeeper(2183, false).start();
        Thread.sleep(1000);

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ProviderConfiguration.class);
        context.start();
        System.out.println("dubbo service started.");
        System.in.read();
    }

}
