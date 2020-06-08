package com.rabbitmq.server;

import com.rabbitmq.server.handler.MessageDecoder;
import com.rabbitmq.server.handler.MessageEncoder;
import com.rabbitmq.server.handler.MessageHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomInitializer extends ChannelInitializer<SocketChannel> {

    private final int MAX_FRAME_SIZE =2048;
    //private final MessageHandler messageHandler;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
        //코덱
        pipeline.addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_SIZE,2,4));
        pipeline.addLast(new MessageDecoder());;
        pipeline.addLast(new MessageEncoder());
        //메세지 핸들러 추가
        pipeline.addLast(new MessageHandler());
    }
}
