package org.july.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

public class NIOClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost", 7000));
        String fileName = "test.zip";
        //得到一个文件Channel
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long startTime = System.currentTimeMillis();
        /**
         * 在linux下通过transferto方法就可以完成传输
         * 在win下通过transferto方法只能发送8m，需要分段传输
         * transferto底层使用到零拷贝
        **/
        long transferCount = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的总字节数=" + transferCount + " 耗时：" + (System.currentTimeMillis() - startTime ) );
        fileChannel.close();
    }
}
