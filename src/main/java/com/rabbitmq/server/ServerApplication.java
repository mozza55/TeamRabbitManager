package com.rabbitmq.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ServerApplication.class, args);
		NettyServer nettyServer = context.getBean(NettyServer.class);
		nettyServer.start();
	}

}
