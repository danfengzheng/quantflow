package com.quantflow.trading.order.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单号生成器
 */
public class OrderNoGenerator {

    private static final AtomicInteger sequence = new AtomicInteger(0);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 生成订单号
     * 格式：QF + 时间戳(14位) + 序列号(4位)
     * 例如：QF202501011230001234
     */
    public static String generate() {
        String timestamp = LocalDateTime.now().format(formatter);
        int seq = sequence.incrementAndGet() % 10000;
        return String.format("QF%s%04d", timestamp, seq);
    }
}