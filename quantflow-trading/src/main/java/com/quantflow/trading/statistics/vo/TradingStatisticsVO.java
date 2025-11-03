package com.quantflow.trading.statistics.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 交易统计VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradingStatisticsVO {

    /** 总交易次数 */
    private Integer totalTrades;

    /** 盈利次数 */
    private Integer profitTrades;

    /** 亏损次数 */
    private Integer lossTrades;

    /** 胜率 */
    private BigDecimal winRate;

    /** 总盈利 */
    private BigDecimal totalProfit;

    /** 总亏损 */
    private BigDecimal totalLoss;

    /** 净盈利 */
    private BigDecimal netProfit;

    /** 盈亏比 */
    private BigDecimal profitLossRatio;

    /** 平均盈利 */
    private BigDecimal avgProfit;

    /** 平均亏损 */
    private BigDecimal avgLoss;

    /** 最大单笔盈利 */
    private BigDecimal maxProfit;

    /** 最大单笔亏损 */
    private BigDecimal maxLoss;

    /** 连续盈利次数 */
    private Integer consecutiveWins;

    /** 连续亏损次数 */
    private Integer consecutiveLosses;

    /** 每日盈亏列表 */
    private List<DailyPnL> dailyPnLList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DailyPnL {
        private String date;
        private BigDecimal pnl;
        private Integer tradeCount;
    }
}