package com.sellf.consumer.client;

import com.self.producer.rmi.Hello;
import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class HelloClient {

    public static void main(String[] args) throws IOException, NotBoundException {
        // 查找服务端地址
        Hello hello = (Hello) Naming.lookup("rmi://localhost:8888/hello");
        System.out.println(hello.helloWorld());
        System.in.read();
    }
}
