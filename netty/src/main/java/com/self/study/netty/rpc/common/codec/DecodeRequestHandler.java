package com.self.study.netty.rpc.common.codec;


import com.self.study.netty.rpc.common.protocol.MessageProtocol;
import com.self.study.netty.rpc.common.protocol.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Executor;

/**
 * 解码请求
 */
public class DecodeRequestHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private MessageProtocol protocol;
    private static Executor executor ;//

    public DecodeRequestHandler(MessageProtocol protocol) {
        this.protocol = protocol;
    }

//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        channelRead0(ctx, ((ByteBuf) msg));
//    }

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);
        Request request = protocol.unmarshallingRequest(data);
        ctx.fireChannelRead(request);
    }
}
