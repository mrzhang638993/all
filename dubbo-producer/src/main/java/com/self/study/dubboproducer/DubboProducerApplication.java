package com.self.study.dubboproducer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DubboProducerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(DubboProducerApplication.class, args);
        System.in.read();
    }

}
