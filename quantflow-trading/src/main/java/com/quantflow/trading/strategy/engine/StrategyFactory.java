package com.quantflow.trading.strategy.engine;

import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.engine.strategies.MACrossStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 策略工厂
 * 根据策略类型创建对应的策略实例
 */
@Slf4j
@Component
public class StrategyFactory {

    @Autowired
    private MACrossStrategy maCrossStrategy;

    /**
     * 创建策略实例
     */
    public IStrategy createStrategy(Strategy strategy) {
        String type = strategy.getType();

        IStrategy strategyInstance = null;

        switch (type) {
            case "MA_CROSS":
                strategyInstance = maCrossStrategy;
                break;

            case "GRID":
                // TODO: 实现网格策略
                throw new UnsupportedOperationException("网格策略暂未实现");

            case "ARBITRAGE":
                // TODO: 实现套利策略
                throw new UnsupportedOperationException("套利策略暂未实现");

            default:
                throw new IllegalArgumentException("不支持的策略类型：" + type);
        }

        // 初始化策略
        strategyInstance.init(strategy);

        log.info("创建策略实例：{}", strategyInstance.getStrategyName());

        return strategyInstance;
    }

    /**
     * 获取所有支持的策略类型
     */
    public Map<String, String> getSupportedStrategies() {
        Map<String, String> strategies = new HashMap<>();
        strategies.put("MA_CROSS", "均线交叉策略");
        strategies.put("GRID", "网格策略（待实现）");
        strategies.put("ARBITRAGE", "套利策略（待实现）");
        return strategies;
    }
}