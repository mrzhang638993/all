package com.self.study.study.netty.nio;

import java.nio.channels.SelectionKey;

//  实现业务处理的线程
public class Worker implements Runnable {
    private String req;

    private SelectionKey selectionKey;

    public Worker(String req, SelectionKey selectionKey) {
        this.req = req;
        this.selectionKey = selectionKey;
    }

    @Override
    public void run() {
        String resp = "echo:" + req;
        selectionKey.attach(resp);
        fireWrite();
    }

    private void fireWrite() {
        int ops = selectionKey.interestOps();
        // 添加对写事件的监听
        selectionKey.interestOps(ops | SelectionKey.OP_WRITE);
        // 唤醒selector
        selectionKey.selector().wakeup();
    }
}
