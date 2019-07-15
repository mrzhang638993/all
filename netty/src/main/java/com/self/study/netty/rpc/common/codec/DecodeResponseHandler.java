package com.self.study.netty.rpc.common.codec;


import com.self.study.netty.rpc.common.protocol.MessageProtocol;
import com.self.study.netty.rpc.common.protocol.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * 解码响应
 */
public class DecodeResponseHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private MessageProtocol protocol;

    public DecodeResponseHandler(MessageProtocol protocol) {
        this.protocol = protocol;
    }

//    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        channelRead0(ctx, ((ByteBuf) msg));
//    }

    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        byte[] data = new byte[msg.readableBytes()];
        msg.readBytes(data);
//        msg.release();
        Response response = protocol.unmarshallingResponse(data);
        ctx.fireChannelRead(response);
    }
}
