package com.self.study.netty.nio;

import javax.print.DocFlavor;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class Buffer {

    public static void main(String[] args) {
        //  分配堆内内存
        ByteBuffer  byteBuffer= ByteBuffer.allocate(20);
        byteBuffer.put((byte) 1);
        byteBuffer.put((byte) 2);
        byteBuffer.put((byte) 3);
        System.out.println(byteBuffer.capacity()+":"+byteBuffer.position()+":"+byteBuffer.limit());

        //  开始从bytebuffer中读取数据
        byteBuffer.flip();
        System.out.println(byteBuffer.get(1));
        System.out.println(byteBuffer.get(1));
        System.out.println(byteBuffer.capacity()+""+byteBuffer.position()+":"+byteBuffer.limit());

        //  开始执行写模式操作
        byteBuffer.compact();
        byteBuffer.put((byte) 7);
        byteBuffer.put((byte) 8);
        byteBuffer.put((byte) 9);
        byteBuffer.put((byte) 10);
        System.out.println(byteBuffer.capacity()+":"+byteBuffer.position()+":"+byteBuffer.limit());

        byte[] array = byteBuffer.array();
        System.out.println(array[1]);


    }
}
