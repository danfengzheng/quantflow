package com.quantflow.trading.strategy.engine;

import com.quantflow.trading.strategy.domain.Strategy;
import lombok.extern.slf4j.Slf4j;
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
        this.strategyList.forEach(v -> {
            this.strategyMap.put(v.getStrategyType(), v);
            this.strategies.put(v.getStrategyType(),v.getStrategyName());
            log.info("加载策略 :{}",v.getStrategyType());
        });
    }


    /**
     * 创建策略实例
     */
    public IStrategy createStrategy(Strategy strategy) {
        String type = strategy.getType();

        IStrategy strategyInstance = strategyMap.get(type);
        if (strategyInstance == null) {
            log.warn("策略未实现:{}",type);
            throw new UnsupportedOperationException("策略暂未实现");
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