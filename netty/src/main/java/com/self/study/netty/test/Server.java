package com.self.study.netty.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class Server {


    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8008"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));


    public static void main(String[] args) throws InterruptedException {
        //  创建boss线程
        EventLoopGroup  boss= new NioEventLoopGroup(1);
        //  创建worker线程
        EventLoopGroup  worker=new NioEventLoopGroup(16);
        //
        ServerBootstrap  server= new ServerBootstrap();

        //  创建处理器
        ServerHandler  serverHandler= new ServerHandler();

        server.group(boss,worker).channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG,100)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>(){

                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(serverHandler);
                    }
                });
        // 绑定端口等待端口服务
        ChannelFuture f = server.bind(PORT).sync();

        // 服务持续到通道关闭
        f.channel().closeFuture().sync();

    }
}
