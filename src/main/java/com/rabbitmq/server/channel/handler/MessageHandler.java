package com.rabbitmq.server.channel.handler;

import com.rabbitmq.server.config.BeanUtils;
import com.rabbitmq.server.dto.NettyMessage;
import com.rabbitmq.server.service.RequestHandler;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Component
//@RequiredArgsConstructor
//@ChannelHandler.Sharable
public class MessageHandler extends SimpleChannelInboundHandler<NettyMessage> {

    Logger logger;
    public final  AttributeKey<Integer> taskType = AttributeKey.valueOf("taskType");
    ChannelGroup channelGroup;
    RequestHandler requestHandler;
    public MessageHandler() {
        logger =  LoggerFactory.getLogger(this.getClass());
        channelGroup =(ChannelGroup) BeanUtils.getBean("channelGroup");
        requestHandler =(RequestHandler) BeanUtils.getBean("requestHandler");
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Integer port = ctx.channel().attr(taskType).get();
        logger.info("port"+port+"channel active");
        channelGroup.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, NettyMessage msg) throws Exception {
        logger.info("channel read:" +msg.toString());
        requestHandler.request(msg);


    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
