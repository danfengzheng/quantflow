package com.quantflow.trading.websocket.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * WebSocket 消息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WSMessage {

    /** 消息类型 */
    private String type;

    /** 消息数据 */
    private Object data;

    /** 时间戳 */
    private Long timestamp;

    public static WSMessage create(String type, Object data) {
        return WSMessage.builder()
                .type(type)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .build();
    }
}