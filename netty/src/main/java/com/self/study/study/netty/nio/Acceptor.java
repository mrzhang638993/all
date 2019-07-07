package com.self.study.study.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Random;
import java.util.Set;


//  主 Reactor，负责连接的处理
public class Acceptor  extends   Thread {

    private Selector selector;

    //  创建从reactor线程使用
    private Thread[] threads = new Thread[4];
    private IOReactor[] dispatchers = new IOReactor[4];



    public Acceptor() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.socket().bind(new InetSocketAddress(8888));
        ssc.configureBlocking(false);
        selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void startup() throws Exception {
        for (int i = 0; i < 4; i++) {
            dispatchers[i] = new IOReactor();
        }
        for (int i = 0; i < 4; i++) {
            //  reactor从线程启动，开始处理监听到的事件信息
            threads[i] = new Thread(dispatchers[i]);
            threads[i].start();
        }
        while (true) {
            try {
                // 此处阻塞
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey selectionKey : selectionKeys) {
                    handle(selectionKey);
                }
                selectionKeys.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void handle(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isAcceptable()) {
            System.out.println("accept");
            ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
            SocketChannel socketChannel = ssc.accept();
            System.out.println("开始连接：" + socketChannel);
            // 随机挑选一个I/O线程
            int i = new Random().nextInt(4);
            dispatchers[i].addChannel(socketChannel);
        }
    }
}
