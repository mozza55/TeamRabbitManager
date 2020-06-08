package com.rabbitmq.server.service;

import com.rabbitmq.server.dto.Message;
import com.rabbitmq.server.dto.NettyMessage;
import com.rabbitmq.server.handler.MessageHandler;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@RequiredArgsConstructor
public class RequestHandler {

    //private final MessageHandler messageHandler;
    private final ChannelGroup channelGroup;
    Logger logger =  LoggerFactory.getLogger(this.getClass());

    public void request(NettyMessage msg) throws InterruptedException {
        String response = msg.getBody();
        logger.info("response : "+msg.getBody());

        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)1,response.getBytes().length,response);
        channelGroup.writeAndFlush(nettyMessage);
    }


}


