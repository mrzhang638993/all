package com.self.study.netty.test;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler  extends ChannelInboundHandlerAdapter {

    //  对应的是入栈的相关的操作的
    //  对应的是客户端的handler信息的
     private  ByteBuf  byteBuf ;

     public   ClientHandler(){
         byteBuf= Unpooled.buffer(Client.SIZE);
         for (int i = 0; i < byteBuf.capacity(); i++) {
             byteBuf.writeByte((byte) i);
         }
     }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("向服务端发送数据"+byteBuf);
        ctx.writeAndFlush(byteBuf);
       // super.channelActive(ctx);
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel  register ");
        /*super.channelRegistered(ctx);*/
        //ctx.flush();
    }


    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel  unregister ");
        super.channelUnregistered(ctx);
    }


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("channel    start   read "+msg);
       /* super.channelRead(ctx, msg);*/
        //  客户端接收到数据，将数据重新的写入到ChannelHandlerContext，写操作完成之后，对应的刷新缓存操作的
        ctx.write(msg);
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel      read   finish ");
        /*super.channelReadComplete(ctx);*/
        ctx.flush();
    }
}
