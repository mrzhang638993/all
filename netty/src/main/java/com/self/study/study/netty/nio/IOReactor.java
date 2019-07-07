package com.self.study.study.netty.nio;


import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//  从 reactor
public class IOReactor  implements Runnable{

    private Selector selector;

    //  管理新加入的SocketChannel使用
    private Queue<SocketChannel> newChannels = new ConcurrentLinkedQueue<>();

    private static Charset charset = Charset.defaultCharset();

    //  执行相关的业务请求使用的。
    private static ExecutorService workerPool = Executors.newFixedThreadPool(200);

    public IOReactor() throws Exception {
        selector = Selector.open();
    }

    @Override
    public void run() {
        while (true) {
            try {
                int readyCount = selector.select(1000);
                if (readyCount > 0) {
                    // 处理读写事件
                    processEvents(selector.selectedKeys());
                }
                // 处理新的连接
                processNewChannels();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processNewChannels() throws Exception {
        SocketChannel channel;
        while ((channel = newChannels.poll()) != null) {
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
        }
    }

    private void processEvents(Set<SelectionKey> selectionKeys) throws Exception {
        for (SelectionKey selectionKey : selectionKeys) {
            handle(selectionKey);
        }
        selectionKeys.clear();
    }

    private void handle(SelectionKey selectionKey) throws Exception {
        if (selectionKey.isReadable()) {
            System.out.println("read");
            handleRead(selectionKey);
        } else if (selectionKey.isWritable()) {
            System.out.println("write");
            handleWrite(selectionKey);
        }
    }

    private void handleWrite(SelectionKey selectionKey) throws Exception {
        Object attachment = selectionKey.attachment();
        selectionKey.attach(null);
        String resp = (String) attachment;
        ByteBuffer buf = charset.encode(resp);
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        socketChannel.write(buf);
        int ops = selectionKey.interestOps();
        // 取消对写事件的监听
        selectionKey.interestOps(ops ^ SelectionKey.OP_WRITE);
    }

    private void handleRead(SelectionKey selectionKey) throws Exception {
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int len = socketChannel.read(buffer);
        if (len > 0) {
            buffer.flip();
            String req = charset.decode(buffer).toString();
            // 交给工作线程处理，处理对应的业务逻辑实现的
            workerPool.execute(new Worker(req, selectionKey));
        } else if (len < 0) {
            System.out.println("关闭连接：" + socketChannel);
            selectionKey.cancel();
            socketChannel.close();
        }
    }

    public void addChannel(SocketChannel socketChannel) {
        //  传入新增加的SocketChannel信息的，客户端对于新增加的channel执行的是accept请求信息的。
        newChannels.add(socketChannel);
        selector.wakeup();
    }
}
