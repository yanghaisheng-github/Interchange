package org.july.zerocopy.old;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

// 传统java IO接收文件的方法
public class server {
    public static void main(String[] args) throws Exception{
        ServerSocket serverSocket = new ServerSocket(7000);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int read = dataInputStream.read(bytes, 0, bytes.length);
                    if (read == -1) {
                        break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
