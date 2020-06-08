package com.rabbitmq.server.channel.handler;

import com.rabbitmq.server.dto.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageEncoder extends MessageToByteEncoder<NettyMessage> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void encode(ChannelHandlerContext ctx, NettyMessage msg, ByteBuf out) throws Exception {
        if(msg == null){
            throw new Exception("message is null");
        }

        out.writeByte(msg.getMessageType());
        out.writeByte(msg.getTaskType());
        out.writeInt(msg.getLength());
        out.writeBytes(msg.getBody().getBytes());

    }
}
