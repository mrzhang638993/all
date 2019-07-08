package com.self.study.study.netty.netty.echo.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;

import java.util.Arrays;

public class ByteBufDemo {

    public static void main(String[] args) {
        // 初始化256字节的bytebuf,对应的是非池化的bytebuf的
        ByteBuf buffer = Unpooled.buffer(10);
        byte []  input=new  byte[]{1,2,3,4,5};
        buffer.writeBytes(input);
        System.out.println("写入元素之后，对应的buffer的相关的信息"+Arrays.toString(buffer.array()) );
        //  获取到的是直接内存信息
        ByteBuf byteBuf = buffer.readBytes(3);
        for (int i = 0; i <byteBuf.capacity() ; i++) {
            System.out.println(byteBuf.getByte(i));
        }
        //  读取元素之后
        System.out.println("读取元素之后，对应的buffer的相关的信息"+Arrays.toString(buffer.array()) );
        // 丢失相关读取的数据
        buffer.discardReadBytes();
        System.out.println("元素清除之后，"+Arrays.toString(buffer.array()) );

        //  清空读写指针之后实现相关操作
        buffer.clear();
        System.out.println("清空读写指针之后，对应的元素信息如下："+Arrays.toString(buffer.array()) );
        byte[]  bytes= new  byte[]{5,6,7,8,9};
        buffer.writeBytes(bytes);
        System.out.println("继续写入数据之后的信息如下："+Arrays.toString(buffer.array()) );


        //  清空所有的元素信息
        buffer.setZero(0,buffer.capacity());
        System.out.println("全部元素设置为0之后："+Arrays.toString(buffer.array()) );
        byte[]   newInput= new  byte[]{11,12,13,14,15};
        buffer.writeBytes(newInput);
        System.out.println("数据插入之后的操作信息如下："+Arrays.toString(buffer.array()) );
    }
}
