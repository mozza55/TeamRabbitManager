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

    private final MessageHandler messageHandler;
    private final ChannelGroup channelList;
    Logger logger =  LoggerFactory.getLogger(this.getClass());

    public void request(Message msg){
        String testMsg = msg.toString();
        Random random = new Random();
        int randomTask=random.nextInt(2);
        logger.info("msg (random task:"+(byte)randomTask+") : "+msg);
        NettyMessage nettyMessage = new NettyMessage((byte)1,(byte)randomTask,testMsg.getBytes().length,testMsg);

        for(Channel channel : channelList){

        }
    }


}


