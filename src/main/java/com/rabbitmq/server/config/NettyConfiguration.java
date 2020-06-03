package com.rabbitmq.server.config;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyConfiguration {

    @Bean
    public ChannelGroup ChannelList(){
        return new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }
}
