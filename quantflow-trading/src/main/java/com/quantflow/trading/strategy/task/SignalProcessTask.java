package com.quantflow.trading.strategy.task;

import com.quantflow.trading.strategy.service.SignalProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 信号处理定时任务
 */
@Slf4j
@Component
public class SignalProcessTask {

    @Autowired
    private SignalProcessService signalProcessService;

    /**
     * 每30秒处理一次待处理的信号
     */
    @Scheduled(cron = "0/30 * * * * ?")
    @Async("orderExecutor")
    public void processSignals() {
        log.debug("开始处理交易信号");

        try {
            signalProcessService.processSignals();
        } catch (Exception e) {
            log.error("处理信号失败", e);
        }

        log.debug("信号处理完成");
    }
}