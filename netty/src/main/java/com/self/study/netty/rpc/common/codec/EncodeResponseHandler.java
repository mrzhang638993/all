package com.self.study.netty.rpc.common.codec;


import com.self.study.netty.rpc.common.protocol.MessageProtocol;
import com.self.study.netty.rpc.common.protocol.Response;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 编码响应
 */
public class EncodeResponseHandler extends MessageToByteEncoder<Response> {
    private MessageProtocol protocol;

    public EncodeResponseHandler(MessageProtocol protocol) {
        this.protocol = protocol;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Response msg, ByteBuf out) throws Exception {
        byte[] data = protocol.marshallingResponse(msg);
        out.writeBytes(data);
    }
}
