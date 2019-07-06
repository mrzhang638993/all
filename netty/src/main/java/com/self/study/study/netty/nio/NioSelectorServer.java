package com.self.study.study.netty.nio;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorServer {

    //  对应的只能够在单个cpu上面执行的，需要进行相关的优化的，需要使用多线程才可以充分使用到多核和多线程的优势的。
    private  static   ByteBuffer  byteBuffer=ByteBuffer.allocate(1024);
    public static void main(String[] args) throws IOException {
        // 创建服务端的serversocketchannel
        ServerSocketChannel  serverSocketChannel= ServerSocketChannel.open();
        //  配置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //  获取到对应的套接字信息
        ServerSocket socket = serverSocketChannel.socket();
        //  获取selector
        Selector open = Selector.open();
        //  将事件注册到selector
        SelectionKey register = serverSocketChannel.register(open, 0);
        register.interestOps(SelectionKey.OP_ACCEPT);
        //  套接字绑定端口信息，开启连接模式
        socket.bind(new InetSocketAddress(10000));
        while(true){
            // 开启selector信息,阻塞模式，事件触发，对应的启动
            open.select();
            //  获取触发到的事件信息
            Set<SelectionKey> selectionKeys = open.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey reactor = iterator.next();
                handleReactor(reactor,open);
                iterator.remove();
            }
        }
    }

    private static void handleReactor(SelectionKey reactor,Selector open) throws IOException {
        if (reactor.isAcceptable()) {
            ServerSocketChannel channel = (ServerSocketChannel) reactor.channel();
            //  阻塞模式，连接成功，终止阻塞操作
            SocketChannel accept = channel.accept();
            accept.configureBlocking(false);
            // 服务端等待read事件的触发
           accept.register(open,SelectionKey.OP_READ);
            System.out.println("接受到客户端的连接" + accept.toString());
          //  reactor.interestOps(SelectionKey.OP_READ);
        }else if (reactor.isReadable()){
            //  服务端接受读取数据事件
            SocketChannel client = (SocketChannel) reactor.channel();
            int len=0;
            //  对应的是阻塞的操作方式的。
            while((len=client.read(byteBuffer))>0){
                //  确保数据可以读写完成
            }
            byteBuffer.flip();
            byte[] content = new byte[byteBuffer.remaining()];
            System.out.println(new String(byteBuffer.array(), CharsetUtil.UTF_8));
           // reactor.interestOps(SelectionKey.OP_WRITE);
            client.register(open,SelectionKey.OP_WRITE);
        }else if (reactor.isWritable()){
            SocketChannel client = (SocketChannel) reactor.channel();
            // 响应结果 200
            String response = "服务端发来贺电了";
            ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
           // reactor.interestOps(SelectionKey.OP_READ);
            client.register(open,SelectionKey.OP_READ);
        }
    }
}
