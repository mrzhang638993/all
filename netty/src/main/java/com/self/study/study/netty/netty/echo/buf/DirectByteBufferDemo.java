package com.self.study.study.netty.netty.echo.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;


public class DirectByteBufferDemo {

    public static void main(String[] args) {
        //  分配直接内存进行操作，分配的是池化的直接内存的
        ByteBuf buffer = Unpooled.directBuffer(10);
        byte []  input=new  byte[]{1,2,3,4,5};
        buffer.writeBytes(input);
        //  直接内存不存在相关的array方法的
        /*System.out.println("写入元素之后，对应的buffer的相关的信息"+ Arrays.toString(buffer.array()) );*/
        //  对应的是包私有的。
       // PooledDirectByteBuf  byteBuf=  PooledDirectByteBuf.newInstance(10);
    }
}
