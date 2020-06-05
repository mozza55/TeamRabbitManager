package com.rabbitmq.server.handler;

import com.rabbitmq.server.dto.NettyMessage;
import com.rabbitmq.server.service.ResponseHandler;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.SynchronousQueue;

@Component
@RequiredArgsConstructor
@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    public final   AttributeKey<Integer> taskType;

    private final  ResponseHandler responseHandler;

    private SynchronousQueue synchronousQueue;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Integer port = ctx.channel().attr(taskType).get();
        logger.info("port"+port+"channel active");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        logger.info("channel read:" +msg.toString());
        String str ="샷";
        NettyMessage nettyMessage = new NettyMessage((byte)1, (byte)1,str.getBytes().length,str);

        Thread.sleep(10000);
        ChannelFuture future = ctx.writeAndFlush(nettyMessage);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
