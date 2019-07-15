package com.self.study.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class NioServerOptimize {

    private  static List<SocketChannel>  channels= new ArrayList<>();

    public static void main(String[] args) throws IOException {
        //  创建服务端的socketchannel
        ServerSocketChannel serverSocketChannel=  ServerSocketChannel.open();
        //  指定服务端的绑定端口信息
        serverSocketChannel.bind(new InetSocketAddress(10000));
        //  设置非阻塞模式
        while (true) {
            // 服务端循环等待客户端的连接
            SocketChannel socketChannel = serverSocketChannel.accept(); // 获取新tcp连接通道
            if (socketChannel != null) {
                //  创建新连接，将连接加入到集合中进行管理的
                socketChannel.configureBlocking(false);
                channels.add(socketChannel);
            }else {
                // 没有新连接的情况下,就去处理现有连接的数据,处理完的就删除掉
                Iterator<SocketChannel> iterator = channels.iterator();
                while (iterator.hasNext()) {
                    SocketChannel ch = iterator.next();
                    try {
                        ByteBuffer requestBuffer = ByteBuffer.allocate(1024);

                        if (ch.read(requestBuffer) == 0) {
                            // 等于0,代表这个通道没有数据需要处理,那就待会再处理
                            continue;
                        }
                        while (ch.isOpen() && ch.read(requestBuffer) != -1) {
                            // 长连接情况下,需要手动判断数据有没有读取结束 (此处做一个简单的判断: 超过0字节就认为请求结束了)
                            if (requestBuffer.position() > 0) break;
                        }
                        if (requestBuffer.position() == 0) continue; // 如果没数据了, 则不继续后面的处理
                        requestBuffer.flip();
                        byte[] content = new byte[requestBuffer.limit()];
                        requestBuffer.get(content);
                        System.out.println(new String(content));
                        System.out.println("收到数据,来自：" + ch.getRemoteAddress());

                        // 响应结果 200
                        String response = "HTTP/1.1 200 OK\r\n" +
                                "Content-Length: 11\r\n\r\n" +
                                "Hello World";
                        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
                        while (buffer.hasRemaining()) {
                            ch.write(buffer);
                        }
                        iterator.remove();
                    } catch (IOException e) {
                        e.printStackTrace();
                        iterator.remove();
                    }
                }
            }
        }
    }
}
