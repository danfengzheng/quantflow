package com.quantflow.trading.dashboard.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 仪表盘数据VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardVO {

    /** 账户总览 */
    private AccountOverview accountOverview;

    /** 今日统计 */
    private TodayStats todayStats;

    /** 策略统计 */
    private StrategyStats strategyStats;

    /** 持仓列表 */
    private List<PositionSummary> positions;

    /** 最近订单 */
    private List<RecentOrder> recentOrders;

    /** 最近信号 */
    private List<RecentSignal> recentSignals;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccountOverview {
        /** 总资产 */
        private BigDecimal totalAsset;
        /** 可用余额 */
        private BigDecimal availableBalance;
        /** 持仓市值 */
        private BigDecimal positionValue;
        /** 总盈亏 */
        private BigDecimal totalPnl;
        /** 总盈亏率 */
        private BigDecimal totalPnlRatio;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TodayStats {
        /** 今日交易次数 */
        private Integer tradeCount;
        /** 今日盈亏 */
        private BigDecimal dailyPnl;
        /** 今日盈亏率 */
        private BigDecimal dailyPnlRatio;
        /** 今日信号数 */
        private Integer signalCount;
        /** 运行中的策略数 */
        private Integer runningStrategyCount;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StrategyStats {
        /** 策略总数 */
        private Integer totalCount;
        /** 运行中 */
        private Integer runningCount;
        /** 已停止 */
        private Integer stoppedCount;
        /** 策略胜率 */
        private Map<Long, BigDecimal> strategyWinRates;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PositionSummary {
        private String symbol;
        private BigDecimal quantity;
        private BigDecimal avgPrice;
        private BigDecimal currentPrice;
        private BigDecimal unrealizedPnl;
        private BigDecimal unrealizedPnlRatio;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentOrder {
        private String orderNo;
        private String symbol;
        private String side;
        private BigDecimal quantity;
        private BigDecimal price;
        private String status;
        private String createTime;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecentSignal {
        private String symbol;
        private String signalType;
        private BigDecimal price;
        private String reason;
        private String createTime;
    }
}