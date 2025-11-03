package com.quantflow.trading.strategy.engine;

import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.engine.strategies.MACrossStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 策略工厂
 * 根据策略类型创建对应的策略实例
 */
@Slf4j
@Component
public class StrategyFactory {

    private final List<IStrategy> strategyList;
    Map<String, IStrategy> strategyMap = new HashMap<>();

    Map<String, String> strategies = new HashMap<>();

    public StrategyFactory(List<IStrategy> strategyList) {
        this.strategyList = strategyList;
        if (strategyMap == null) {
            strategyList.forEach(v -> {
                strategyMap.put(v.getStrategyType(), v);
                strategies.put(v.getStrategyType(),v.getStrategyName());
            });
        }
    }


    /**
     * 创建策略实例
     */
    public IStrategy createStrategy(Strategy strategy) {
        String type = strategy.getType();

        IStrategy strategyInstance = strategyMap.get(type);
        if (strategyInstance == null) {
            throw new UnsupportedOperationException("网格策略暂未实现");
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
        return strategies;
    }
}