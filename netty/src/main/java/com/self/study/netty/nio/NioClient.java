package com.self.study.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class NioClient {
    public static void main(String[] args) throws IOException {
        //  创建客户端的socket
        SocketChannel  socketChannel= SocketChannel.open();
        //   连接服务端的socket
        socketChannel.connect(new InetSocketAddress(10000));

        //  客户端等到，直到连接上了服务端的
        while (!socketChannel.finishConnect()){
            // 线程等待，直到完成相关的连接
            Thread.currentThread().yield();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入：");
        // 发送内容
        String msg = scanner.nextLine();
        ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
        while (buffer.hasRemaining()) {
            socketChannel.write(buffer);
        }
        // 读取响应
        System.out.println("收到服务端响应:");
        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

        while (socketChannel.isOpen() && socketChannel.read(requestBuffer) != -1) {
            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
            if (requestBuffer.position() > 0) break;
        }
        requestBuffer.flip();
        byte[] content = new byte[requestBuffer.limit()];
        requestBuffer.get(content);
        System.out.println(new String(content));
        scanner.close();
        socketChannel.close();
    }

}
