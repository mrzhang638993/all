package com.self.study.netty.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerOptimize {

    private static  ExecutorService executor=Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        ServerSocket  serverSocket = new ServerSocket(10000);
        //  使用线程池进行操作的话，可以接受客户端的数量等于线程池的数量信息的。开启了大量的线程进行操作的，存在很大的问题的
        while(!serverSocket.isClosed()){
            Socket  accept = serverSocket.accept();
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream is = accept.getInputStream();
                        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(is));
                        //  阻塞方法，需要等待客户端的输入的，无输入的话，会一直等待的。
                        String msg=null;
                        //  readline对应的会阻塞的，导致对应的只能够接受到一个客户端的请求的。
                        while ((msg= bufferedReader.readLine())!=null){
                            System.out.println(msg);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
