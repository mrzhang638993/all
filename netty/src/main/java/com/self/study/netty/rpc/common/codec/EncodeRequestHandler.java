package com.self.study.netty.rpc.common.codec;


import com.self.study.netty.rpc.common.protocol.MessageProtocol;
import com.self.study.netty.rpc.common.protocol.Request;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码请求
 */
public class EncodeRequestHandler extends MessageToByteEncoder<Request> {
    private MessageProtocol protocol;

    public EncodeRequestHandler(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Request msg, ByteBuf out) throws Exception {
        byte[] data = protocol.marshallingRequest(msg);
        out.writeBytes(data);
    }
}
