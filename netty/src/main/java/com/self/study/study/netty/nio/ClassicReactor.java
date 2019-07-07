package com.self.study.study.netty.nio;

import java.io.IOException;


public class ClassicReactor {
    public static void main(String[] args) throws IOException {
       MainReactor   mainReactor= new MainReactor();
       mainReactor.start();
    }
}
