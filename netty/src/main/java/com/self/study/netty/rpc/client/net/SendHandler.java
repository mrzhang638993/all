package com.self.study.netty.rpc.client.net;


import com.self.study.netty.rpc.common.protocol.Response;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * SendHandler
 */
public class SendHandler extends SimpleChannelInboundHandler<Response> {

    private final Map<String, CompletableFuture<Response>> futureMap;

    public SendHandler(Map<String, CompletableFuture<Response>> futureMap) {
        this.futureMap = futureMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接服务端成功：" + ctx);

        super.channelActive(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Response response) throws Exception {
        // 将结果返回出去
        CompletableFuture<Response> future = futureMap.get(response.getRequestId());
        future.complete(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
