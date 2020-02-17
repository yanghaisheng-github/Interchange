package org.july.groupchat;

import java.io.IOError;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    //构造器，初始化
    public GroupChatServer() {
        try{
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.configureBlocking(false);
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听
    public void listen() {
        try{
            while (true) {
                int count = selector.select();
                if (count>0) { //有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        //监听到OP_ACCEPT
                        if(key.isAcceptable()) {
                            SocketChannel sc =listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector, SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress() + "上线");
                        }
                        //监听到可读状态
                        if(key.isReadable()) {
                            readData(key);
                        }
                        iterator.remove();
                    }
                }else{
                    System.out.println("等待中...");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //读取客户端消息
    private void readData(SelectionKey key) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if (count>0) {
                String msg = new String(buffer.array());
                System.out.println("客户端发来消息：" + msg);
                //向其他客户端转发该消息
                sendMsg(msg, channel);
            }
        }catch (IOException e){
            try{
                System.out.println(channel.getRemoteAddress() + "离线了");
                //取消注册
                key.cancel();
                //关闭同道
                channel.close();
            }catch (IOException e2){
                e2.printStackTrace();
            }

        }
    }

    //转发消息给其他客户端
    private void sendMsg(String msg, SocketChannel self) throws IOException{
        //遍历所有注册到selector上的SocketChannel，并排除self
        for(SelectionKey key: selector.keys()) {
            Channel targetChannel = key.channel();
            //排除自己
            if(targetChannel instanceof SocketChannel && targetChannel != self){
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        //启动服务器
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }
}
