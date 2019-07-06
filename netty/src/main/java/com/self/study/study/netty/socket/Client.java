package com.self.study.study.netty.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket  socket= new Socket();
        socket.connect(new InetSocketAddress(10000));
        OutputStream os = socket.getOutputStream();
       // BufferedWriter  bufferedWriter= new BufferedWriter(new OutputStreamWriter(os));
        String msg="hello  good boy";
       // bufferedWriter.write(msg);
        //bufferedWriter.newLine();
        os.write(msg.getBytes());
        os.write(System.lineSeparator().getBytes());

        while (true){

        }
    }
}
