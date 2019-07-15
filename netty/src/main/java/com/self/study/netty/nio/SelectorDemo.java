package com.self.study.netty.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    public static void main(String[] args) throws IOException {
        //  创建服务端的socket
        ServerSocketChannel  serverSocketChannel=ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(10000));
        //  创建selector，将selector注册到socket上，进行socket管理的
        Selector  selector= Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            //  阻塞方法，当存在事件的时候会开启的
            int select = selector.select();
            if (select==0) continue;
            //  获取所有触发的事件信息
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                //  对应的触发的事件
                SelectionKey key = iterator.next();
                if (key.isAcceptable()){
                    SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
                    System.out.println("有客户端连接进入到服务器"+channel.getRemoteAddress());
                    channel.register(selector,SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    //  触发服务端的读事件的
                }else if (key.isWritable()){
                    //  触发服务端的写事件
                }
                iterator.remove();
            }
        }
    }
}
