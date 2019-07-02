package com.self.producer.rmi;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class HelloStart {

    public static void main(String[] args) throws IOException, AlreadyBoundException {
        //  绑定端口
        Registry registry = LocateRegistry.createRegistry(8888);

        Hello  hello= new HelloImpl();

        //  绑定服务
        registry.bind("hello",hello);

        System.in.read();
    }
}
