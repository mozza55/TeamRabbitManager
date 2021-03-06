package com.rabbitmq.server.service;

import com.rabbitmq.server.dto.NettyMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    //private final MessageHandler messageHandler;
    //private final ChannelGroup channelGroup;
    Logger logger =  LoggerFactory.getLogger(this.getClass());

    public void request(ChannelHandlerContext ctx, NettyMessage msg) throws InterruptedException {
        String response = msg.getBody();
        logger.info("response : "+msg.getBody());
        //Thread.sleep(20000);
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)1,response.getBytes().length,response);
        ctx.channel().writeAndFlush(nettyMessage);
    }


}


