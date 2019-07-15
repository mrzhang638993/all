package com.self.study.netty.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    private    ByteBuf  byteBuf;


    public ServerHandler(){
        byteBuf= Unpooled.buffer(Client.SIZE);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
       /* System.out.println("channel  start  get  data ");
        super.channelRead(ctx, msg);*/
        System.out.println("收到客户端数据，还给客户端：" + msg);
        //  将客户端的数据发送到ChannelHandlerContext，并且完成相关的刷新操作实现的。
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel     finish  get  data ");
        //  数据读取完成清空数据的
        ctx.flush();
      /*  super.channelReadComplete(ctx);*/
    }


    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel  registered ");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel  unregistered ");
        super.channelUnregistered(ctx);
    }
}
