package com.self.study.study.netty.nio;

import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

//  主要的逻辑是负责创建客户端的连接，将客户端的连接交给相关的Acceptor
public class MainReactor extends  Thread {

    @Override
    public void run() {
        super.run();
    }

    private Selector  selector;

    public MainReactor(ServerSocketChannel serverSocketChannel) {

    }
}
