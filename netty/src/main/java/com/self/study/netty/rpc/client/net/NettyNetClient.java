package com.self.study.netty.rpc.client.net;


import com.self.study.netty.rpc.common.codec.DecodeResponseHandler;
import com.self.study.netty.rpc.common.codec.EncodeRequestHandler;
import com.self.study.netty.rpc.common.protocol.MessageProtocol;
import com.self.study.netty.rpc.common.protocol.Request;
import com.self.study.netty.rpc.common.protocol.Response;
import com.self.study.netty.rpc.common.protocol.Status;
import com.self.study.netty.rpc.discovery.ServiceInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.json.JsonObjectDecoder;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.*;

public class NettyNetClient implements NetClientV2 {

    private Map<String, Future<Channel>> channels = new ConcurrentHashMap<>();

    private Map<String, CompletableFuture<Response>> futureMap = new ConcurrentHashMap();

    @Override
    public Response sendRequest(Request data, ServiceInfo sinfo) {
        try {
            // 获取客户端连接
            Channel channel = getChannel(sinfo);
            CompletableFuture<Response> future = new CompletableFuture();
            futureMap.put(data.getRequestId(), future);

            // 写入数据
            channel.writeAndFlush(data);
            // 获取返回结果
            return future.get();
        } catch (Exception e) {
            Response response = new Response(Status.ERROR);
            response.setRequestId(data.getRequestId());
            response.setException(e);
            return response;
        } finally {
            futureMap.remove(data.getRequestId());
        }
    }

    /**
     * 根据服务信息获取Channel
     *
     * @param sinfo
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private Channel getChannel(ServiceInfo sinfo) throws ExecutionException, InterruptedException {
        // 获取已有的连接
        Future<Channel> future = channels.get(sinfo.toString());
        if (future == null) {
            // 如果连接的future不存在就创建一个新的。可能会有多个线程都创建一个future，但真正被用到的只会有一个

            // 创建连接的call
            Callable<Channel> call = new Callable<Channel>() {
                @Override
                public Channel call() throws Exception {
                    return connect(sinfo);
                }
            };

            FutureTask<Channel> futureTask = new FutureTask<Channel>(call);

            // 从Map中获取，只会有一个线程获取到空的
            future = channels.putIfAbsent(sinfo.toString(), futureTask);
            if (future == null) {
                future = futureTask;
                // 执行创建连接的方法
                futureTask.run();
            }
        }

        // 返回future中的Channel
        return future.get();
    }

    /**
     * 连接服务器
     *
     * @param sinfo
     * @throws InterruptedException
     */
    private Channel connect(ServiceInfo sinfo) throws InterruptedException {
        List<String> addressList = sinfo.getAddress();
        int randNum = new Random().nextInt(addressList.size());
        String address = addressList.get(randNum);
        String[] addInfoArray = address.split(":");

        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        MessageProtocol messageProtocol = sinfo.getMessageProtocol();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new JsonObjectDecoder());// TODO 添加Json解码器，偷个懒，这里应该是具体的协议类去实现，才能做到动态切换
                        p.addLast(new EncodeRequestHandler(messageProtocol));// 添加request编码器
                        p.addLast(new DecodeResponseHandler(messageProtocol));// 添加response解码器
                        p.addLast(new SendHandler(futureMap));
                    }
                });

        Channel channel = bootstrap.connect(addInfoArray[0], Integer.valueOf(addInfoArray[1])).sync().channel();
        // 添加关闭通道时的事件
        channel.closeFuture().addListeners(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                // 释放线程组资源
                group.shutdownGracefully();
            }
        });
        return channel;
    }
}
