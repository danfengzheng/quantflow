package com.quantflow.trading.common.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

/**
 * 交易系统配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "quantflow.trading")
public class TradingConfig {

    /**
     * 是否启用模拟交易
     */
    private Boolean mockEnabled = false;

    /**
     * 默认交易所
     */
    private String defaultExchange = "binance";

    /**
     * 策略引擎线程池大小
     */
    private Integer strategyThreadPoolSize = 10;

    /**
     * 订单超时时间（秒）
     */
    private Integer orderTimeout = 60;

    /**
     * WebSocket重连间隔（毫秒）
     */
    private Integer wsReconnectInterval = 5000;
}