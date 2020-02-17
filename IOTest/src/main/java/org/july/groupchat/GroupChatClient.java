package org.july.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class GroupChatClient {
    private final String HOST = "127.0.0.1";
    private final  int PORT = 6667;
    private Selector selecotr;
    private SocketChannel socketChannel;
    private String username;
    //构造器，初始化工作
    public GroupChatClient() throws IOException {
        selecotr = Selector.open();
        //连接服务器
        socketChannel= SocketChannel.open(new InetSocketAddress(HOST, PORT));
        socketChannel.configureBlocking(false);
        socketChannel.register(selecotr, SelectionKey.OP_READ);
        username = socketChannel.getLocalAddress().toString().substring(1);
        System.out.println(username + "is ok ...");
    }

    //向服务器发送消息
    public void sendMsg(String msg) {
        msg = username + "说: " + msg;
        try {
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));

        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //读取从服务端返回的消息
    public void readMsg() {
        try{
            int readChannels = selecotr.select();
            if(readChannels > 0) { //即通道有事件发生
                Iterator<SelectionKey> iterator = selecotr.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if(key.isReadable()) {
                        //得到相关通道
                        SocketChannel sc = (SocketChannel) key.channel();
                        //得到一个buffer
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        sc.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());

                    }
                }
                //清空，防止重复操作
                iterator.remove();
            } else{
                //System.out.println("没有可用的通道");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
        //启动客户端
        GroupChatClient chatClient = new GroupChatClient();

        //启动一个线程，每隔3s读取来自服务端的数据
        new Thread(){
            public void run(){
                while (true){
                     chatClient.readMsg();
                     try{
                         Thread.currentThread().sleep(3000);
                     }catch (Exception e){
                         e.printStackTrace();
                     }
                }
            }
        }.start();

        //发送数据给服务器
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            chatClient.sendMsg(s);
        }

    }
}
