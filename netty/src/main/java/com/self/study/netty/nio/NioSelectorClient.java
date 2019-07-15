package com.self.study.netty.nio;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSelectorClient {

    private  static   ByteBuffer  byteBuffer=ByteBuffer.allocate(1024);

    public static void main(String[] args) throws IOException {
        //  获取客户端的连接信息
        SocketChannel  socketChannel= SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector open = Selector.open();
        SelectionKey register = socketChannel.register(open, 0);
        register.interestOps(SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress(10000));
        // 处理客户单的所有事件信息
        while (true){
            open.select();
            Set<SelectionKey> selectionKeys = open.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                handleReactor(key,open);
                iterator.remove();
            }
        }
    }

    private static void handleReactor(SelectionKey reactor, Selector open) throws IOException {
        if (reactor.isConnectable()){
            SocketChannel client = (SocketChannel) reactor.channel();
            if (client.finishConnect()){
                System.out.println("客户端和服务端成功的创建了连接"+client.toString());
            }
            client.register(open,SelectionKey.OP_WRITE);
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
            String response = "发信息通知服务端了";
            ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
            reactor.interestOps(SelectionKey.OP_WRITE);
        }else if (reactor.isWritable()){
            SocketChannel client = (SocketChannel) reactor.channel();
            // 响应结果 200
            String response = "客户端发来信息了";
            ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
            while (buffer.hasRemaining()) {
                client.write(buffer);
            }
            reactor.interestOps(SelectionKey.OP_READ);
        }
    }
}
