package com.rabbitmq.server;

import com.rabbitmq.server.handler.MessageDecoder;
import com.rabbitmq.server.handler.MessageEncoder;
import com.rabbitmq.server.handler.MessageHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.AttributeKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Component
@PropertySource(value = "classpath:/application.properties")
public class NettyServer {

    @Value("${tcp.port}")
    private int tcpPort;
    @Value("${boss.thread.count}")
    private int bossCount;
    @Value("${worker.thread.count}")
    private int workerCount;
    private final int MAX_FRAME_SIZE =1024;

    @Autowired private CustomInitializer customInitializer;
    @Autowired public AttributeKey<Integer> taskType;
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossCount); //연결
        EventLoopGroup workerGroup = new NioEventLoopGroup(4); //입출력  내부 설정에 의해 cpu 코어 수에 따라 설정
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)//서버 소켓 입출력 모드를 NIO로 설정
                    .handler(new LoggingHandler(LogLevel.INFO))//서버 소켓 채널의 이벤트 핸들러 등록
                    .childHandler(customInitializer);
            //ChannelFuture : 비동기 방식의 작업 처리 후 결과를 제어

            List<Integer> ports = Arrays.asList(8080,8081);
            Collection<ChannelFuture> channelFutures  = new ArrayList<>(ports.size());
            for(int port : ports){
                ChannelFuture future = b.childAttr(taskType,port).bind(port).sync();
                channelFutures.add(future);
            }
            for(ChannelFuture future : channelFutures){
                future.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
