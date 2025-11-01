package com.quantflow.trading.strategy.mapper;

import com.quantflow.trading.strategy.domain.StrategySignal;

import java.util.List;

/**
 * 策略信号Mapper接口
 */
public interface StrategySignalMapper {

    /**
     * 查询策略信号列表
     */
    List<StrategySignal> selectStrategySignalList(StrategySignal strategySignal);

    /**
     * 查询策略信号详情
     */
    StrategySignal selectStrategySignalById(Long id);

    /**
     * 新增策略信号
     */
    int insertStrategySignal(StrategySignal strategySignal);

    /**
     * 修改策略信号
     */
    int updateStrategySignal(StrategySignal strategySignal);

    /**
     * 删除策略信号
     */
    int deleteStrategySignalById(Long id);
}