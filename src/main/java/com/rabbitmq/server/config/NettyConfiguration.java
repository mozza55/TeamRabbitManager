package com.rabbitmq.server.config;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfiguration {

    @Bean
    public ChannelGroup channelGroup(){
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
    @Bean
    public AttributeKey<Integer> taskType (){
        return AttributeKey.newInstance("taskType");
    }


}
