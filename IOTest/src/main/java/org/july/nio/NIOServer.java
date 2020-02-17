package org.july.nio;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {
    public static void main(String[] args) throws Exception{
        //创建ServerSocketChannel -> ServerSocket，并绑定监听端口
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //创建一个Selector对象
        Selector selector = Selector.open();
        //将ServerSocketChannel注册到Selector对象，关心事件为OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端连接
        while (true) {
            //等待1s，若没事件发生，返回
            if(selector.select(1000) == 0) { //没有事件发生
                continue;
            }
            //如果返回值大于0，则获取关注事件的集合(即selectionKey集合)
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历事件集合
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                //根据key对应的通道发生的事件做相应处理
                if (selectionKey.isAcceptable()) { //如果是OP_ACCEPT，即有新的客户端连接
                    //生成对应的SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("客户端连接成功，生成了一个SocketChannel：" + socketChannel.hashCode());
                    //将当前的SocketChannel注册到selector上
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (selectionKey.isReadable()) { //发生OP_READ
                    //通过key获取对应的channel
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    //通过channel获取对应的buffer
                    ByteBuffer buffer = (ByteBuffer)selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("from客户端"+ new String(buffer.array()));
                }
                //手动移除当前的SelectionKey，防止重复操作
                keyIterator.remove();
            }

        }


    }
}
