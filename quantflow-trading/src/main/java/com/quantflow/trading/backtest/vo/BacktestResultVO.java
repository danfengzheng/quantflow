package com.quantflow.trading.backtest.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * 回测结果VO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BacktestResultVO {

    /** 基本信息 */
    private BasicInfo basicInfo;

    /** 收益指标 */
    private ReturnMetrics returnMetrics;

    /** 风险指标 */
    private RiskMetrics riskMetrics;

    /** 交易统计 */
    private TradeStats tradeStats;

    /** 资金曲线 */
    private List<EquityPoint> equityCurve;

    /** 交易记录 */
    private List<TradeRecord> tradeRecords;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BasicInfo {
        private String strategyName;
        private String symbol;
        private String interval;
        private String startDate;
        private String endDate;
        private BigDecimal initialCapital;
        private Integer tradingDays;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReturnMetrics {
        /** 总收益率 */
        private BigDecimal totalReturn;
        /** 年化收益率 */
        private BigDecimal annualReturn;
        /** 累计收益 */
        private BigDecimal cumulativeProfit;
        /** 最终资金 */
        private BigDecimal finalCapital;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RiskMetrics {
        /** 最大回撤 */
        private BigDecimal maxDrawdown;
        /** 最大回撤期间 */
        private String maxDrawdownPeriod;
        /** 夏普比率 */
        private BigDecimal sharpeRatio;
        /** 波动率 */
        private BigDecimal volatility;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TradeStats {
        /** 总交易次数 */
        private Integer totalTrades;
        /** 盈利次数 */
        private Integer profitTrades;
        /** 亏损次数 */
        private Integer lossTrades;
        /** 胜率 */
        private BigDecimal winRate;
        /** 盈亏比 */
        private BigDecimal profitLossRatio;
        /** 平均盈利 */
        private BigDecimal avgProfit;
        /** 平均亏损 */
        private BigDecimal avgLoss;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EquityPoint {
        private String date;
        private BigDecimal equity;
        private BigDecimal drawdown;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TradeRecord {
        private String entryDate;
        private String exitDate;
        private String side;
        private BigDecimal entryPrice;
        private BigDecimal exitPrice;
        private BigDecimal quantity;
        private BigDecimal profit;
        private BigDecimal profitRatio;
    }
}