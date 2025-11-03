package com.quantflow.trading.websocket;

import com.alibaba.fastjson2.JSON;
import com.quantflow.trading.websocket.message.WSMessage;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 交易 WebSocket 服务
 */
@Slf4j
@Component
@ServerEndpoint("/ws/trading/{userId}")
public class TradingWebSocketServer {

    /** 静态变量，用来记录当前在线连接数 */
    private static int onlineCount = 0;

    /** concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象 */
    private static CopyOnWriteArraySet<TradingWebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();

    /** 用户ID到WebSocket的映射 */
    private static Map<String, TradingWebSocketServer> userWebSocketMap = new ConcurrentHashMap<>();

    /** 与某个客户端的连接会话，需要通过它来给客户端发送数据 */
    private Session session;

    /** 用户ID */
    private String userId;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        this.session = session;
        this.userId = userId;

        webSocketSet.add(this);
        userWebSocketMap.put(userId, this);

        addOnlineCount();
        log.info("用户连接：userId={}, 当前在线人数={}", userId, getOnlineCount());

        try {
            sendMessage(WSMessage.create("connected", "连接成功"));
        } catch (IOException e) {
            log.error("发送消息失败", e);
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);
        userWebSocketMap.remove(userId);

        subOnlineCount();
        log.info("用户断开：userId={}, 当前在线人数={}", userId, getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("收到客户端消息：userId={}, message={}", userId, message);

        try {
            // 可以根据客户端消息做相应处理
            // 例如：订阅特定交易对的行情
        } catch (Exception e) {
            log.error("处理客户端消息失败", e);
        }
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("WebSocket错误：userId={}", userId, error);
    }

    /**
     * 发送消息
     */
    public void sendMessage(WSMessage message) throws IOException {
        this.session.getBasicRemote().sendText(JSON.toJSONString(message));
    }

    /**
     * 群发消息
     */
    public static void sendToAll(WSMessage message) {
        for (TradingWebSocketServer item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
                log.error("群发消息失败", e);
            }
        }
    }

    /**
     * 发送消息给指定用户
     */
    public static void sendToUser(String userId, WSMessage message) {
        TradingWebSocketServer webSocket = userWebSocketMap.get(userId);
        if (webSocket != null) {
            try {
                webSocket.sendMessage(message);
            } catch (IOException e) {
                log.error("发送消息给用户失败：userId={}", userId, e);
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        TradingWebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        TradingWebSocketServer.onlineCount--;
    }
}