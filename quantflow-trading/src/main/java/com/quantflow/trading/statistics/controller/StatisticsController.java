package com.quantflow.trading.statistics.controller;

import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.constant.TradingMessageKeys;
import com.quantflow.trading.statistics.service.StatisticsService;
import com.quantflow.trading.statistics.vo.TradingStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 统计Controller
 */
@Slf4j
@RestController
@RequestMapping("/trading/statistics")
public class StatisticsController extends BaseController {

    @Autowired
    private StatisticsService statisticsService;

    /**
     * 获取交易统计
     */
    @PreAuthorize("@ss.hasPermi('trading:statistics:query')")
    @GetMapping("/trading")
    public AjaxResult getTradingStatistics() {
        try {
            TradingStatisticsVO statistics = statisticsService.getTradingStatistics();
            return AjaxResult.success(statistics);
        } catch (Exception e) {
            log.error("获取交易统计失败", e);
            return AjaxResult.error(TradingMessageKeys.STATISTICS_GET_TRADING_STATISTICS_FAILED, e.getMessage());
        }
    }
}