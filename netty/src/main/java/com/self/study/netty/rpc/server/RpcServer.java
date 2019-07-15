package com.self.study.netty.rpc.server;

import com.self.study.netty.rpc.common.codec.DecodeRequestHandler;
import com.self.study.netty.rpc.common.codec.EncodeResponseHandler;
import com.self.study.netty.rpc.common.protocol.MessageProtocol;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.Closeable;
import java.io.IOException;

/**
 * RpcServer
 * RPC服务提供者
 */
public class RpcServer implements Closeable {

    private int port;

    private Class<MessageProtocol> protocol;

    private Channel channel;

    private RequestHandler handler;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();
    private EventLoopGroup bizExecutor = new NioEventLoopGroup(20, new DefaultThreadFactory("biz-thread-pool"));

    public RpcServer(Class<MessageProtocol> protocol, int port) {
        this.protocol = protocol;
        this.port = port;
    }

    public void start() {
        // 开启网络服务
        try {
            setup(port);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setup(int port) throws Exception {
        // 配置服务器
        try {
            MessageProtocol messageProtocol = protocol.newInstance();
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new JsonObjectDecoder());
                            p.addLast(new DecodeRequestHandler(messageProtocol));
                            p.addLast(new EncodeResponseHandler(messageProtocol));
//                            p.addLast(handler);
                            p.addLast(bizExecutor, handler);
                        }
                    });

            // 启动服务
            ChannelFuture f = b.bind(port).sync();
            System.out.println("完成服务端端口绑定与启动");
            channel = f.channel();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        System.out.println("关闭RPC服务");
        channel.close();
        // 释放线程组资源
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
        bizExecutor.shutdownGracefully();
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol.getName();
    }

    public void setProtocol(Class<MessageProtocol> protocol) {
        this.protocol = protocol;
    }

    public RequestHandler getHandler() {
        return handler;
    }

    public void setHandler(RequestHandler handler) {
        this.handler = handler;
    }

    @Override
    public void close() throws IOException {
        stop();
    }

}
