package com.rabbitmq.server.dto;

import lombok.*;

@Data
@AllArgsConstructor
public class NettyMessage {

    private byte messageType;
    private byte taskType;
    private int length;
    private String body;
}
