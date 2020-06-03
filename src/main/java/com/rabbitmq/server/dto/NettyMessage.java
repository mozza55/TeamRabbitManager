package com.rabbitmq.server.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class NettyMessage {

    private byte messageType;
    private byte taskType;
    private int length;
    private String body;
}
