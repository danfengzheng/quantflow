package com.quantflow.trading.common.controller;

import com.quantflow.common.annotation.Anonymous;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.trading.common.config.TradingConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统健康检查
 */
@RestController
@RequestMapping("/api/trading/health")
public class HealthController {

    @Autowired
    private TradingConfig tradingConfig;

    /**
     * 健康检查
     */
    @Anonymous
    @GetMapping("/check")
    public AjaxResult check() {
        Map<String, Object> data = new HashMap<>();
        data.put("status", "ok");
        data.put("module", "quantflow-trading");
        data.put("version", "1.0.0");
        data.put("timestamp", LocalDateTime.now());
        data.put("mockEnabled", tradingConfig.getMockEnabled());
        data.put("defaultExchange", tradingConfig.getDefaultExchange());

        return AjaxResult.success("QuantFlow Trading Module is running!", data);
    }

    /**
     * 获取配置信息
     */
    @Anonymous
    @GetMapping("/config")
    public AjaxResult getConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put("mockEnabled", tradingConfig.getMockEnabled());
        config.put("defaultExchange", tradingConfig.getDefaultExchange());
        config.put("strategyThreadPoolSize", tradingConfig.getStrategyThreadPoolSize());
        config.put("orderTimeout", tradingConfig.getOrderTimeout());
        config.put("wsReconnectInterval", tradingConfig.getWsReconnectInterval());

        return AjaxResult.success(config);
    }
}