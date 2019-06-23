package com.self.study.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

    static final int PORT = Integer.parseInt(System.getProperty("port", "8008"));

    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossloop= new NioEventLoopGroup(0);
        EventLoopGroup  workloop=  new  NioEventLoopGroup(16);
        final   EchoServerHandler  echoServerHandler= new EchoServerHandler();
        ServerBootstrap  serverBootstrap= new ServerBootstrap();
        serverBootstrap
                .group(bossloop,workloop).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        //p.addLast(new LoggingHandler(LogLevel.INFO));
                        p.addLast(echoServerHandler);
                    }
                });
        ChannelFuture sync = serverBootstrap.bind(PORT).sync();
        sync.channel().closeFuture().sync();
    }
}
