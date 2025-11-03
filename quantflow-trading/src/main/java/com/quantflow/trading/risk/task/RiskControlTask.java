package com.quantflow.trading.risk.task;

import com.quantflow.trading.risk.service.RiskControlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 风控定时任务
 */
@Slf4j
@Component
public class RiskControlTask {

    @Autowired
    private RiskControlService riskControlService;

    /**
     * 每分钟检查一次止损止盈
     */
    @Scheduled(cron = "0 * * * * ?")
    public void checkStopLoss() {
        log.debug("执行止损止盈检查");
        try {
            riskControlService.checkStopLossAndTakeProfit();
        } catch (Exception e) {
            log.error("止损止盈检查失败", e);
        }
    }
}