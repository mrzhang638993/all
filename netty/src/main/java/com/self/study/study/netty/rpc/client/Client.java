package com.self.study.study.netty.rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class Client {

    public static void main(String[] args) {
        //  客户端对应的是存在相关的很多的线程的知识逻辑的特色的。
        EventLoopGroup work= new NioEventLoopGroup(1);
        Bootstrap   bootStrap= new Bootstrap();
        bootStrap.group(work);

    }
}
