package com.self.study.study.netty.nio;

import java.nio.channels.SelectableChannel;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Acceptor {

    private   Selector  open;

    private SelectableChannel   key;


    public Acceptor(Selector open, SelectableChannel key) {
        this.open=open;
        this.key=key;
    }

    public void dealWithAccept() {
        // 处理连接信息事件的

    }


    //  对应的逻辑实现

}
