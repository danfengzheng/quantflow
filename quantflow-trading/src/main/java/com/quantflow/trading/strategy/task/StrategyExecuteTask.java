package com.quantflow.trading.strategy.task;

import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.mapper.StrategyMapper;
import com.quantflow.trading.strategy.service.StrategyExecuteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 策略执行定时任务
 */
@Slf4j
@Component
public class StrategyExecuteTask {

    @Autowired
    private StrategyMapper strategyMapper;

    @Autowired
    private StrategyExecuteService strategyExecuteService;

    /**
     * 每分钟执行一次策略
     * 只执行状态为"运行中"(status=2)的策略
     */
    @Scheduled(cron = "0 * * * * ?")
    @Async("strategyExecutor")
    public void executeStrategies() {
        log.debug("开始执行策略定时任务");

        // 查询所有运行中的策略
        Strategy query = new Strategy();
        query.setStatus(2);  // 运行中
        List<Strategy> strategies = strategyMapper.selectStrategyList(query);

        if (strategies.isEmpty()) {
            log.debug("没有运行中的策略");
            return;
        }

        log.info("找到 {} 个运行中的策略", strategies.size());

        // 执行每个策略
        for (Strategy strategy : strategies) {
            try {
                strategyExecuteService.executeStrategy(strategy);
            } catch (Exception e) {
                log.error("执行策略失败：strategyId={}, name={}",
                        strategy.getId(), strategy.getName(), e);
            }
        }

        log.debug("策略定时任务执行完成");
    }
}