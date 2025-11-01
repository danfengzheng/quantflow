package com.quantflow.trading.strategy.service.impl;

import com.quantflow.common.utils.SecurityUtils;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.mapper.StrategyMapper;
import com.quantflow.trading.strategy.service.IStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 策略Service业务层处理
 */
@Service
public class StrategyServiceImpl implements IStrategyService {

    @Autowired
    private StrategyMapper strategyMapper;

    /**
     * 查询策略列表
     */
    @Override
    public List<Strategy> selectStrategyList(Strategy strategy) {
        // 只查询当前用户的策略
        strategy.setUserId(SecurityUtils.getUserId());
        return strategyMapper.selectStrategyList(strategy);
    }

    /**
     * 查询策略详情
     */
    @Override
    public Strategy selectStrategyById(Long id) {
        Strategy strategy = strategyMapper.selectStrategyById(id);
        // 验证权限
        if (strategy != null && !strategy.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限访问此策略");
        }
        return strategy;
    }

    /**
     * 新增策略
     */
    @Override
    public int insertStrategy(Strategy strategy) {
        // 设置当前用户ID
        strategy.setUserId(SecurityUtils.getUserId());
        // 默认状态为停用
        if (strategy.getStatus() == null) {
            strategy.setStatus(0);
        }
        return strategyMapper.insertStrategy(strategy);
    }

    /**
     * 修改策略
     */
    @Override
    public int updateStrategy(Strategy strategy) {
        // 验证权限
        Strategy existStrategy = strategyMapper.selectStrategyById(strategy.getId());
        if (existStrategy == null || !existStrategy.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限修改此策略");
        }

        // 运行中的策略不允许修改关键参数
        if (existStrategy.getStatus() == 2) {
            throw new RuntimeException("策略正在运行中，请先停止策略");
        }

        return strategyMapper.updateStrategy(strategy);
    }

    /**
     * 修改策略
     */
    @Override
    public int stopStrategy(Strategy strategy) {
        // 验证权限
        Strategy existStrategy = strategyMapper.selectStrategyById(strategy.getId());
        if (existStrategy == null || !existStrategy.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限修改此策略");
        }

        // 运行中的策略不允许修改关键参数
        if (existStrategy.getStatus() == 2) {
            return strategyMapper.updateStrategy(strategy);
        }

        throw new RuntimeException("策略未运行无需停止");
    }

    /**
     * 删除策略
     */
    @Override
    public int deleteStrategyById(Long id) {
        // 验证权限
        Strategy strategy = strategyMapper.selectStrategyById(id);
        if (strategy == null || !strategy.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限删除此策略");
        }

        // 运行中的策略不允许删除
        if (strategy.getStatus() == 2) {
            throw new RuntimeException("策略正在运行中，无法删除");
        }

        return strategyMapper.deleteStrategyById(id);
    }

    /**
     * 批量删除策略
     */
    @Override
    public int deleteStrategyByIds(Long[] ids) {
        for (Long id : ids) {
            deleteStrategyById(id);
        }
        return ids.length;
    }
}