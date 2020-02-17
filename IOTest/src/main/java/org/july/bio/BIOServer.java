package org.july.bio;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BIOServer {
    public static void main(String[] args) throws Exception {
        //创建一个线程池，
        //如果有客户端连接，就创建一个线程与之连接
        ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();
        //创建ServerSocket
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了。。");
        while (true) {
            System.out.println("阻塞，监听等待客户端连接。。。");
            final Socket socket = serverSocket.accept();
            System.out.println("监听到一个客户端请求连接...");
            //从线程池中创建一个客户端与之连接
            newCachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }
    }

    //与客户端通讯
    public static void handler(Socket socket) {
        try{
            System.out.println("线程信息：id= " + Thread.currentThread().getId() + "名字= " + Thread.currentThread().getName());
            byte[] bytes = new byte[1024];
            //通过socket获取输入流
            InputStream inputStream = socket.getInputStream();
            //循环读取客户端数据
            while (true){
                System.out.println("阻塞，等待读取数据");
                System.out.println("线程信息：id= " + Thread.currentThread().getId() + "名字= " + Thread.currentThread().getName());
                int read = inputStream.read(bytes);
                if(read != -1){
                    System.out.println(new String(bytes, 0 , read));
                }else
                    break;
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("关闭与client的连接");
            try{
                socket.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }
}
