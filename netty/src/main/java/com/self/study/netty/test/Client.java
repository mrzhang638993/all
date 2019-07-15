package com.self.study.netty.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8008"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));


    public static void main(String[] args) throws InterruptedException {
            //  客户端启动设置信息
        EventLoopGroup  client= new NioEventLoopGroup();
        //  netty启动类
        Bootstrap  bootstrap= new Bootstrap();
        // 启动类进行相关配置
            bootstrap.group(client).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        //  创建客户端的handler处理信息,选择对应的处理器信息
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new ClientHandler());
                        }
                    });
            // 采用同步阻塞的方法来实现相关的资源的查找操作的，确保连接的通常和顺利的。
        ChannelFuture f = bootstrap.connect(HOST, PORT).sync();
        //  通道关闭触发的操作,客户端持续的存在，知道对应的客户端的通道关闭了。
        f.channel().closeFuture().sync();
    }
}
