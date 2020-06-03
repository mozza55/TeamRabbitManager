package com.rabbitmq.server.handler;

import com.rabbitmq.server.dto.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

    // LengthFieldBasedFrameDecoder 로 만들어진 frame 전체가 들어온다
    /*
     * header 2byte = 1byte(메세지 종류) + 1byte(task 종류)
     *  + length 4byte  = body 길이 정보
     *  + body (?) byte
     */
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte messageType = in.readByte();
        byte taskType = in.readByte();
        int length = in.readInt();
        String body = null;
        if(length >0){
            ByteBuf buf = in.readBytes(length);
            byte[] req = new byte[buf.readableBytes()];
            buf.readBytes(req);
            body = new String(req, "UTF-8");
        }
        NettyMessage nettyMessage = new NettyMessage(messageType,taskType,length,body);
        logger.info(nettyMessage.toString());
        out.add(nettyMessage);
    }
}
