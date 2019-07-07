package com.self.study.study.netty.nio;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Handler  {

    private   final   int MAXIN=2048;

    private   final   int MAXOUT=4096;

    final SocketChannel socket;
    final SelectionKey sk;
    ByteBuffer input = ByteBuffer.allocate(MAXIN);
    ByteBuffer output = ByteBuffer.allocate(MAXOUT);
    static final int READING = 0, SENDING = 1;
    int state = READING;


    public    Handler(Selector selector, SelectionKey selectionKey)
            throws IOException {
        socket= (SocketChannel) selectionKey.channel();
        socket.configureBlocking(false);
        sk = socket.register(selector, 0);
        sk.attach(this);
        sk.interestOps(SelectionKey.OP_READ);
        //  开始第一次轮询操作实现
       // selector.wakeup();
    }




    boolean inputIsComplete() throws IOException {
        int  len=0;
        while((len=socket.read(input))>0){
        }
        return    true;
    }

    boolean outputIsComplete() throws IOException {
         return   false;
    }


    void process() throws IOException {
        //  开始执行业务逻辑实现，从获取到的信息中实现信息的存储操作实现的。
        byte[] content = new byte[input.remaining()];
        System.out.println(new String(input.array(), CharsetUtil.UTF_8));
    }


    public void run() {
        try {
            if (state == READING) read();
            else if (state == SENDING) send();
        } catch (IOException ex) { /* ... */ }
    }
    void read() throws IOException {
        if (inputIsComplete()) {
            process();
            state = SENDING;
            sk.interestOps(SelectionKey.OP_WRITE);
        }
    }
    void send() throws IOException {
        if (outputIsComplete()) {
            processWrite();
            state = READING;
            sk.interestOps(SelectionKey.OP_READ);
        }
    }

    private void processWrite() {
        // 完成信息发送操作

    }
}






