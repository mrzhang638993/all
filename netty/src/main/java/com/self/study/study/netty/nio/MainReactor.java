package com.self.study.study.netty.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

//  处理客户端连接的所有的请求信息
public class MainReactor extends Thread {

    private ServerSocketChannel serverSocketChannel;

    private Selector selector;

    //  提高acceptor的利用率信息
     private    ConcurrentHashMap<SelectableChannel,Acceptor>  acceptors  = new ConcurrentHashMap();

    public MainReactor() throws IOException {
        serverSocketChannel = ServerSocketChannel.open();
        //  配置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //  获取到对应的套接字信息
        ServerSocket socket = serverSocketChannel.socket();
        //  获取selector
        selector = Selector.open();
        //  将事件注册到selector
        SelectionKey register = serverSocketChannel.register(selector, 0);
        register.interestOps(SelectionKey.OP_ACCEPT);
        //  套接字绑定端口信息，开启连接模式
        socket.bind(new InetSocketAddress(10000));
        //  执行的具体的逻辑交给后面的Acceptor进行处理
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> it = selectionKeys.iterator();
                while (it.hasNext()) {
                SelectionKey next = it.next();
                    SelectableChannel channel = next.channel();
                    Acceptor acceptor = acceptors.get(channel);
                    if (acceptor==null){
                        acceptor = new Acceptor(next, selector);
                        next.attach(acceptor);
                        acceptors.put(channel,acceptor);
                    }
                dispatch(next);
                it.remove();
            }
        }
        } catch (IOException ex) { /* ... */ }
    }

    private void dispatch(SelectionKey next) {
        Runnable r = (Runnable) (next.attachment());
        if (r != null) {
            r.run();
        }
    }
}
