package com.quantflow.trading.strategy.engine;

import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;

import java.util.List;

/**
 * 策略接口
 * 所有策略都需要实现此接口
 */
public interface IStrategy {

    /**
     * 初始化策略
     * @param strategy 策略配置
     */
    void init(Strategy strategy);

    /**
     * 计算策略信号
     * @param klines K线数据（历史数据）
     * @return 交易信号，如果没有信号返回null
     */
    StrategySignal calculate(List<KLine> klines);

    /**
     * 获取策略类型
     */
    String getStrategyType();

    /**
     * 获取策略名称
     */
    String getStrategyName();

    /**
     * 获取策略描述
     */
    String getStrategyDescription();

    /**
     * 验证策略参数
     * @param params 参数JSON字符串
     * @return 是否有效
     */
    boolean validateParams(String params);
}