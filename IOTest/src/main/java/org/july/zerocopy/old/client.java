package org.july.zerocopy.old;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.net.Socket;
import java.util.logging.SocketHandler;

//传统java IO发送文件
public class client {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 7000);
        String fileName = "test.zip";
        FileInputStream fileInputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readcount;
        long total = 0;
        long startTime = System.currentTimeMillis();
        while ((readcount = fileInputStream.read(buffer)) >= 0) {
            total += readcount;
            dataOutputStream.write(buffer);
        }
        System.out.println("发送总字节数：" + total + ", 耗时：" + (System.currentTimeMillis() - startTime) );
        dataOutputStream.close();
        socket.close();
        fileInputStream.close();
    }
}
