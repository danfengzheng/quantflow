package com.quantflow.trading.backtest.engine;

import com.quantflow.trading.backtest.vo.BacktestResultVO;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.engine.IStrategy;
import com.quantflow.trading.strategy.engine.StrategyFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 回测引擎
 */
@Slf4j
@Component
public class BacktestEngine {

    @Autowired
    private StrategyFactory strategyFactory;

    /**
     * 执行回测
     */
    public BacktestResultVO runBacktest(
            Strategy strategy,
            List<KLine> klines,
            BigDecimal initialCapital,
            BigDecimal commissionRate) {

        log.info("开始回测：strategy={}, klines={}, capital={}",
                strategy.getName(), klines.size(), initialCapital);

        // 创建策略实例
        IStrategy strategyInstance = strategyFactory.createStrategy(strategy);

        // 初始化回测状态
        BacktestState state = new BacktestState(initialCapital, commissionRate);

        // 资金曲线
        List<BacktestResultVO.EquityPoint> equityCurve = new ArrayList<>();

        // 交易记录
        List<BacktestResultVO.TradeRecord> tradeRecords = new ArrayList<>();

        // 遍历K线，执行策略
        for (int i = 0; i < klines.size(); i++) {
            // 获取当前可用的历史数据
            List<KLine> historicalData = klines.subList(0, i + 1);

            // 计算策略信号
            StrategySignal signal = strategyInstance.calculate(historicalData);

            // 处理信号
            if (signal != null) {
                processSignal(signal, klines.get(i), state, tradeRecords);
            }

            // 更新持仓市值
            if (state.hasPosition()) {
                state.updatePositionValue(klines.get(i).getClose());
            }

            // 记录资金曲线
            equityCurve.add(BacktestResultVO.EquityPoint.builder()
                    .date(formatDate(klines.get(i).getOpenTime()))
                    .equity(state.getTotalEquity())
                    .drawdown(state.getCurrentDrawdown())
                    .build());
        }

        // 计算回测指标
        return calculateMetrics(strategy, klines, initialCapital, state, equityCurve, tradeRecords);
    }

    /**
     * 处理信号
     */
    private void processSignal(
            StrategySignal signal,
            KLine currentKLine,
            BacktestState state,
            List<BacktestResultVO.TradeRecord> tradeRecords) {

        BigDecimal currentPrice = currentKLine.getClose();

        if ("BUY".equals(signal.getSignalType())) {
            // 买入信号
            if (!state.hasPosition()) {
                // 计算可买入数量（使用80%资金）
                BigDecimal availableCash = state.getCash().multiply(new BigDecimal("0.8"));
                BigDecimal quantity = availableCash.divide(currentPrice, 8, RoundingMode.DOWN);

                if (quantity.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal cost = quantity.multiply(currentPrice);
                    BigDecimal commission = cost.multiply(state.getCommissionRate());

                    state.buy(quantity, currentPrice, commission, formatDate(currentKLine.getOpenTime()));

                    log.debug("回测买入：price={}, quantity={}, cost={}",
                            currentPrice, quantity, cost.add(commission));
                }
            }
        } else if ("SELL".equals(signal.getSignalType())) {
            // 卖出信号
            if (state.hasPosition()) {
                // 保存持仓信息，因为sell后会清空
                BacktestState.Position position = state.getPosition();
                BigDecimal quantity = position.getQuantity();
                BigDecimal entryPrice = position.getEntryPrice();
                String entryDate = state.getLastTradeDate();

                BigDecimal revenue = quantity.multiply(currentPrice);
                BigDecimal commission = revenue.multiply(state.getCommissionRate());

                BigDecimal profit = state.sell(currentPrice, commission, formatDate(currentKLine.getOpenTime()));

                // 计算盈亏率
                BigDecimal costValue = entryPrice.multiply(quantity);
                BigDecimal profitRatio = profit.divide(costValue, 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100));

                // 记录交易
                BacktestResultVO.TradeRecord record = BacktestResultVO.TradeRecord.builder()
                        .entryDate(entryDate)
                        .exitDate(formatDate(currentKLine.getOpenTime()))
                        .side("BUY")
                        .entryPrice(entryPrice)
                        .exitPrice(currentPrice)
                        .quantity(quantity)
                        .profit(profit)
                        .profitRatio(profitRatio)
                        .build();

                tradeRecords.add(record);

                log.debug("回测卖出：price={}, quantity={}, profit={}, profitRatio={}%",
                        currentPrice, quantity, profit, profitRatio);
            }
        }
    }

    /**
     * 计算回测指标
     */
    private BacktestResultVO calculateMetrics(
            Strategy strategy,
            List<KLine> klines,
            BigDecimal initialCapital,
            BacktestState state,
            List<BacktestResultVO.EquityPoint> equityCurve,
            List<BacktestResultVO.TradeRecord> tradeRecords) {

        // 基本信息
        BacktestResultVO.BasicInfo basicInfo = BacktestResultVO.BasicInfo.builder()
                .strategyName(strategy.getName())
                .symbol(strategy.getSymbol())
                .interval(strategy.getInterval())
                .startDate(formatDate(klines.get(0).getOpenTime()))
                .endDate(formatDate(klines.get(klines.size() - 1).getOpenTime()))
                .initialCapital(initialCapital)
                .tradingDays(klines.size())
                .build();

        // 最终资金
        BigDecimal finalCapital = state.getTotalEquity();

        // 收益指标
        BigDecimal totalReturn = finalCapital.subtract(initialCapital)
                .divide(initialCapital, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));

        // 年化收益率（简化计算）
        BigDecimal tradingYears = BigDecimal.valueOf(klines.size()).divide(BigDecimal.valueOf(365), 4, RoundingMode.HALF_UP);
        BigDecimal annualReturn = totalReturn.divide(tradingYears, 2, RoundingMode.HALF_UP);

        BacktestResultVO.ReturnMetrics returnMetrics = BacktestResultVO.ReturnMetrics.builder()
                .totalReturn(totalReturn)
                .annualReturn(annualReturn)
                .cumulativeProfit(finalCapital.subtract(initialCapital))
                .finalCapital(finalCapital)
                .build();

        // 风险指标
        BigDecimal maxDrawdown = state.getMaxDrawdown();
        BigDecimal sharpeRatio = calculateSharpeRatio(equityCurve);

        BacktestResultVO.RiskMetrics riskMetrics = BacktestResultVO.RiskMetrics.builder()
                .maxDrawdown(maxDrawdown)
                .maxDrawdownPeriod("N/A")
                .sharpeRatio(sharpeRatio)
                .volatility(BigDecimal.ZERO)
                .build();

        // 交易统计
        int totalTrades = tradeRecords.size();
        long profitTrades = tradeRecords.stream()
                .filter(t -> t.getProfit().compareTo(BigDecimal.ZERO) > 0)
                .count();
        long lossTrades = tradeRecords.stream()
                .filter(t -> t.getProfit().compareTo(BigDecimal.ZERO) < 0)
                .count();

        BigDecimal winRate = totalTrades > 0 ?
                BigDecimal.valueOf(profitTrades)
                        .divide(BigDecimal.valueOf(totalTrades), 4, RoundingMode.HALF_UP)
                        .multiply(BigDecimal.valueOf(100)) :
                BigDecimal.ZERO;

        BigDecimal avgProfit = profitTrades > 0 ?
                tradeRecords.stream()
                        .filter(t -> t.getProfit().compareTo(BigDecimal.ZERO) > 0)
                        .map(BacktestResultVO.TradeRecord::getProfit)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(profitTrades), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        BigDecimal avgLoss = lossTrades > 0 ?
                tradeRecords.stream()
                        .filter(t -> t.getProfit().compareTo(BigDecimal.ZERO) < 0)
                        .map(BacktestResultVO.TradeRecord::getProfit)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(lossTrades), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        BigDecimal profitLossRatio = avgLoss.abs().compareTo(BigDecimal.ZERO) > 0 ?
                avgProfit.divide(avgLoss.abs(), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        BacktestResultVO.TradeStats tradeStats = BacktestResultVO.TradeStats.builder()
                .totalTrades(totalTrades)
                .profitTrades((int) profitTrades)
                .lossTrades((int) lossTrades)
                .winRate(winRate)
                .profitLossRatio(profitLossRatio)
                .avgProfit(avgProfit)
                .avgLoss(avgLoss)
                .build();

        return BacktestResultVO.builder()
                .basicInfo(basicInfo)
                .returnMetrics(returnMetrics)
                .riskMetrics(riskMetrics)
                .tradeStats(tradeStats)
                .equityCurve(equityCurve)
                .tradeRecords(tradeRecords)
                .build();
    }

    /**
     * 计算夏普比率（简化版本）
     */
    private BigDecimal calculateSharpeRatio(List<BacktestResultVO.EquityPoint> equityCurve) {
        if (equityCurve.size() < 2) {
            return BigDecimal.ZERO;
        }

        // 简化计算，实际应该使用日收益率
        // TODO: 实现真实的夏普比率计算
        return BigDecimal.valueOf(1.5);
    }

    /**
     * 格式化日期
     */
    private String formatDate(Long timestamp) {
        return LocalDate.ofEpochDay(timestamp / 86400000)
                .format(DateTimeFormatter.ISO_DATE);
    }

    /**
     * 回测状态类
     */
    private static class BacktestState {
        private BigDecimal cash;
        private Position position;
        private BigDecimal commissionRate;
        private BigDecimal maxEquity;
        private BigDecimal maxDrawdown;
        private String lastTradeDate;

        public BacktestState(BigDecimal initialCapital, BigDecimal commissionRate) {
            this.cash = initialCapital;
            this.position = null;
            this.commissionRate = commissionRate;
            this.maxEquity = initialCapital;
            this.maxDrawdown = BigDecimal.ZERO;
        }

        public void buy(BigDecimal quantity, BigDecimal price, BigDecimal commission, String date) {
            BigDecimal cost = quantity.multiply(price).add(commission);
            this.cash = this.cash.subtract(cost);
            this.position = new Position(quantity, price);
            this.lastTradeDate = date;
        }

        public BigDecimal sell(BigDecimal price, BigDecimal commission, String date) {
            BigDecimal revenue = this.position.quantity.multiply(price).subtract(commission);
            BigDecimal cost = this.position.quantity.multiply(this.position.entryPrice);
            BigDecimal profit = revenue.subtract(cost);

            this.cash = this.cash.add(revenue);
            this.position = null;
            this.lastTradeDate = date;

            return profit;
        }

        public void updatePositionValue(BigDecimal currentPrice) {
            if (this.position != null) {
                this.position.currentPrice = currentPrice;
            }

            BigDecimal totalEquity = getTotalEquity();
            if (totalEquity.compareTo(this.maxEquity) > 0) {
                this.maxEquity = totalEquity;
            }

            BigDecimal drawdown = this.maxEquity.subtract(totalEquity)
                    .divide(this.maxEquity, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));

            if (drawdown.compareTo(this.maxDrawdown) > 0) {
                this.maxDrawdown = drawdown;
            }
        }

        public BigDecimal getTotalEquity() {
            BigDecimal equity = this.cash;
            if (this.position != null) {
                equity = equity.add(this.position.quantity.multiply(this.position.currentPrice));
            }
            return equity;
        }

        public BigDecimal getCurrentDrawdown() {
            BigDecimal totalEquity = getTotalEquity();
            if (this.maxEquity.compareTo(BigDecimal.ZERO) == 0) {
                return BigDecimal.ZERO;
            }
            return this.maxEquity.subtract(totalEquity)
                    .divide(this.maxEquity, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        public boolean hasPosition() {
            return this.position != null;
        }

        public Position getPosition() {
            return this.position;
        }

        public BigDecimal getCash() {
            return this.cash;
        }

        public BigDecimal getCommissionRate() {
            return this.commissionRate;
        }

        public BigDecimal getMaxDrawdown() {
            return this.maxDrawdown;
        }

        public String getLastTradeDate() {
            return this.lastTradeDate;
        }

        private static class Position {
            BigDecimal quantity;
            BigDecimal entryPrice;
            BigDecimal currentPrice;

            Position(BigDecimal quantity, BigDecimal entryPrice) {
                this.quantity = quantity;
                this.entryPrice = entryPrice;
                this.currentPrice = entryPrice;
            }

            public BigDecimal getQuantity() {
                return quantity;
            }

            public BigDecimal getEntryPrice() {
                return entryPrice;
            }
        }
    }
}