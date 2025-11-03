package com.quantflow.trading.strategy.engine.strategies;

import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.engine.IStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * RSI策略
 */
@Slf4j
@Component
public class RSIStrategy implements IStrategy {

    private Strategy strategy;
    private int period;
    private double oversoldLevel;
    private double overboughtLevel;

    @Override
    public void init(Strategy strategy) {
        this.strategy = strategy;

        String params = strategy.getParamsConfig();
        if (params != null && !params.isEmpty()) {
            String[] paramArray = params.split(",");
            if (paramArray.length >= 1) {
                this.period = Integer.parseInt(paramArray[0].trim());
            }
            if (paramArray.length >= 2) {
                this.oversoldLevel = Double.parseDouble(paramArray[1].trim());
            }
            if (paramArray.length >= 3) {
                this.overboughtLevel = Double.parseDouble(paramArray[2].trim());
            }
        }

        if (this.period == 0) this.period = 14;
        if (this.oversoldLevel == 0) this.oversoldLevel = 30;
        if (this.overboughtLevel == 0) this.overboughtLevel = 70;

        log.info("RSI策略初始化：周期={}, 超卖={}, 超买={}",
                period, oversoldLevel, overboughtLevel);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        if (klines.size() < period + 1) {
            log.debug("K线数据不足，需要至少 {} 根K线", period + 1);
            return null;
        }

        BigDecimal totalGain = BigDecimal.ZERO;
        BigDecimal totalLoss = BigDecimal.ZERO;

        for (int i = klines.size() - period; i < klines.size(); i++) {
            BigDecimal change = klines.get(i).getClose().subtract(klines.get(i - 1).getClose());

            if (change.compareTo(BigDecimal.ZERO) > 0) {
                totalGain = totalGain.add(change);
            } else {
                totalLoss = totalLoss.add(change.abs());
            }
        }

        BigDecimal avgGain = totalGain.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
        BigDecimal avgLoss = totalLoss.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);

        if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        BigDecimal rs = avgGain.divide(avgLoss, 8, RoundingMode.HALF_UP);
        BigDecimal rsi = BigDecimal.valueOf(100)
                .subtract(BigDecimal.valueOf(100)
                        .divide(BigDecimal.ONE.add(rs), 2, RoundingMode.HALF_UP));

        KLine currentKline = klines.get(klines.size() - 1);
        BigDecimal currentPrice = currentKline.getClose();

        // 超卖
        if (rsi.compareTo(BigDecimal.valueOf(oversoldLevel)) < 0) {
            log.info("检测到RSI超卖信号：RSI={}", rsi);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .reason(String.format("RSI超卖，RSI=%.2f", rsi.doubleValue()))
                    .build();
        }

        // 超买
        if (rsi.compareTo(BigDecimal.valueOf(overboughtLevel)) > 0) {
            log.info("检测到RSI超买信号：RSI={}", rsi);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .reason(String.format("RSI超买，RSI=%.2f", rsi.doubleValue()))
                    .build();
        }

        return null;
    }

    @Override
    public String getStrategyType() {
        return "RSI";
    }

    @Override
    public String getStrategyName() {
        return "RSI策略";
    }

    @Override
    public String getStrategyDescription() {
        return "基于RSI指标的超买超卖策略，RSI低于阈值时买入，高于阈值时卖出。参数格式：周期,超卖阈值,超买阈值（如：14,30,70）";
    }

    @Override
    public boolean validateParams(String params) {
        if (params == null || params.isEmpty()) {
            return false;
        }

        try {
            String[] paramArray = params.split(",");
            if (paramArray.length != 3) {
                return false;
            }

            int period = Integer.parseInt(paramArray[0].trim());
            double oversold = Double.parseDouble(paramArray[1].trim());
            double overbought = Double.parseDouble(paramArray[2].trim());

            return period > 0 && oversold > 0 && oversold < overbought && overbought < 100;
        } catch (Exception e) {
            return false;
        }
    }
}