package com.self.study.producer.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloImpl extends UnicastRemoteObject implements Hello {
    protected HelloImpl() throws RemoteException {
    }

    @Override
    public String helloWorld() throws RemoteException {
        return "这是服务端的rmi服务提供者，调用的是服务端的服务的";
    }
}
