package com.self.study.study.netty.nio;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

//  多reactor线程模型
public class NioSelectorServerOptimize {

    public static void main(String[] args) throws IOException {
        //启动服务器
        ServerSocketChannel  serverSocketChannel= ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        Selector open = Selector.open();
        SelectionKey register = serverSocketChannel.register(open, 0);
        register.interestOps(SelectionKey.OP_ACCEPT);
        socket.bind(new InetSocketAddress(10000));

        while(true){
             open.select();
            Set<SelectionKey> selectionKeys = open.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            //  客户端连接进来
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                SelectableChannel channel = key.channel();

                Acceptor acceptor = new Acceptor(open,channel);
                acceptor.dealWithAccept();
            }
        }
    }

}
