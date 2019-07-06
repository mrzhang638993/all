package com.self.study.study.netty.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket  serverSocket= new ServerSocket(10000);
        while (!serverSocket.isClosed()){
            //  接口客户端连接信息
            Socket accept = serverSocket.accept();
            InputStream is = accept.getInputStream();
            BufferedReader  bufferedReader= new BufferedReader(new InputStreamReader(is));
            //  阻塞方法，需要等待客户端的输入的，无输入的话，会一直等待的。
            String msg=null;
            while ((msg= bufferedReader.readLine())!=null){
                System.out.println(msg);
            }
        }
        serverSocket.close();
    }
}
