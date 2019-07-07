package com.self.study.study.netty.nio;

import java.io.IOException;
import java.nio.channels.*;
import java.util.concurrent.ConcurrentHashMap;

public class Acceptor  extends   Thread {

    private   Selector  selector;

    private   SelectionKey   selectionKey;

    //  将一个socket的所有的事件全部交给一个对应的类来进行管理操作。
    private ConcurrentHashMap<SocketChannel,Handler> handlers=new ConcurrentHashMap();

    public Acceptor(SelectionKey selectionKey, Selector selector) {
        this.selectionKey=selectionKey;
        this.selector=selector;
    }

    @Override
    public void run() {
        try {
            //  对应的而连接处理的是同样的一个socket请求的。
            if(selectionKey.isAcceptable()){
                SocketChannel socket=((ServerSocketChannel)selectionKey.channel()).accept();
                if (socket.finishConnect()){
                    //  首次连接的时候将相关的handler放置到map中进行保存，减少对象的创建的次数信息。
                    Handler handler =new Handler(selector, selectionKey);
                    handlers.put(socket,handler);
                }
            }else {
                SocketChannel channel = (SocketChannel) selectionKey.channel();
                Handler handler = handlers.get(channel);
                handler.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
