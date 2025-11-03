package com.quantflow.trading.websocket;

import com.quantflow.trading.order.domain.Order;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.websocket.message.WSMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * WebSocket 推送服务
 */
@Slf4j
@Service
public class WebSocketPushService {

    /**
     * 推送新信号
     */
    public void pushSignal(StrategySignal signal, Long userId) {
        log.info("推送信号给用户：userId={}, signalType={}, symbol={}",
                userId, signal.getSignalType(), signal.getSymbol());

        WSMessage message = WSMessage.create("new_signal", signal);
        TradingWebSocketServer.sendToUser(userId.toString(), message);
    }

    /**
     * 推送新订单
     */
    public void pushOrder(Order order, Long userId) {
        log.info("推送订单给用户：userId={}, orderNo={}, status={}",
                userId, order.getOrderNo(), order.getStatus());

        WSMessage message = WSMessage.create("new_order", order);
        TradingWebSocketServer.sendToUser(userId.toString(), message);
    }

    /**
     * 推送订单状态变更
     */
    public void pushOrderStatusChange(Order order, Long userId) {
        log.info("推送订单状态变更：userId={}, orderNo={}, status={}",
                userId, order.getOrderNo(), order.getStatus());

        WSMessage message = WSMessage.create("order_status_change", order);
        TradingWebSocketServer.sendToUser(userId.toString(), message);
    }

    /**
     * 推送持仓变化
     */
    public void pushPositionChange(Long userId) {
        log.info("推送持仓变化通知：userId={}", userId);

        WSMessage message = WSMessage.create("position_change", null);
        TradingWebSocketServer.sendToUser(userId.toString(), message);
    }

    /**
     * 推送风控告警
     */
    public void pushRiskAlert(String alertMessage, Long userId) {
        log.warn("推送风控告警：userId={}, message={}", userId, alertMessage);

        WSMessage message = WSMessage.create("risk_alert", alertMessage);
        TradingWebSocketServer.sendToUser(userId.toString(), message);
    }
}