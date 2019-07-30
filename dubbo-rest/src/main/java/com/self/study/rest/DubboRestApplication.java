package com.self.study.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class DubboRestApplication {

    public static void main(String[] args) throws IOException {

        SpringApplication.run(DubboRestApplication.class, args);
        System.in.read();
    }

}
