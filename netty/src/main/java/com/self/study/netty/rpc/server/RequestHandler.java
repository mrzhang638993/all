package com.self.study.netty.rpc.server;


import com.self.study.netty.rpc.common.protocol.Request;
import com.self.study.netty.rpc.common.protocol.Response;
import com.self.study.netty.rpc.common.protocol.Status;
import com.self.study.netty.rpc.server.register.ServiceObject;
import com.self.study.netty.rpc.server.register.ServiceRegister;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Sharable
public class RequestHandler extends SimpleChannelInboundHandler<Request> {

    private ServiceRegister serviceRegister;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(Thread.currentThread().getName() + " 激活");
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Request request) throws Exception {
        Response response = handleRequest(request);
        ctx.write(response);
        ctx.fireChannelRead(request);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.err.println(Thread.currentThread().getName() + " 发生异常：" + cause.getMessage());
        cause.printStackTrace();
        ctx.close();
    }

    public Response handleRequest(Request request) {

        // 2、查找服务对象
        ServiceObject so = this.serviceRegister.getServiceObject(request.getServiceName());

        Response rsp = null;

        if (so == null) {
            rsp = new Response(Status.NOT_FOUND);
            rsp.setRequestId(request.getRequestId());
        } else {
            // 3、反射调用对应的过程方法
            try {
                Method m = so.getInterf().getMethod(request.getMethod(), request.getPrameterTypes());
                Object returnValue = m.invoke(so.getObj(), request.getParameters());
                rsp = new Response(Status.SUCCESS);
                rsp.setReturnValue(returnValue);
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException e) {
                rsp = new Response(Status.ERROR);
                rsp.setException(e);
            }
            rsp.setRequestId(request.getRequestId());
        }

        // 4、编组响应消息
        return rsp;
    }

    public ServiceRegister getServiceRegister() {
        return serviceRegister;
    }

    public void setServiceRegister(ServiceRegister serviceRegister) {
        this.serviceRegister = serviceRegister;
    }

}
