package org.july.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //HttpServerCodec是netty提供的编码、解码器
        pipeline.addLast("MyHttpServerCodeC", new HttpServerCodec());
        //增加自定义Handler
        pipeline.addLast("MyHttpHandler", new TestHttpServerHandler());

    }
}
