package com.self.study.producer.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;


//   服务端提供rmi服务信息
public interface Hello extends Remote {
    public String helloWorld() throws RemoteException;
}
