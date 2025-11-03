package com.quantflow.trading.strategy.engine.strategies;

import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.engine.IStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 突破策略
 */
@Slf4j
@Component
public class BreakoutStrategy implements IStrategy {

    private Strategy strategy;
    private int period;

    @Override
    public void init(Strategy strategy) {
        this.strategy = strategy;

        String params = strategy.getParamsConfig();
        if (params != null && !params.isEmpty()) {
            this.period = Integer.parseInt(params.trim());
        }

        if (this.period == 0) this.period = 20;

        log.info("突破策略初始化：回看周期={}", period);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        if (klines.size() < period + 1) {
            log.debug("K线数据不足，需要至少 {} 根K线", period + 1);
            return null;
        }

        List<KLine> recentKlines = klines.subList(klines.size() - period - 1, klines.size() - 1);

        BigDecimal highestHigh = recentKlines.get(0).getHigh();
        BigDecimal lowestLow = recentKlines.get(0).getLow();

        for (KLine kline : recentKlines) {
            if (kline.getHigh().compareTo(highestHigh) > 0) {
                highestHigh = kline.getHigh();
            }
            if (kline.getLow().compareTo(lowestLow) < 0) {
                lowestLow = kline.getLow();
            }
        }

        KLine currentKline = klines.get(klines.size() - 1);
        BigDecimal currentPrice = currentKline.getClose();
        BigDecimal currentHigh = currentKline.getHigh();
        BigDecimal currentLow = currentKline.getLow();

        // 向上突破
        if (currentHigh.compareTo(highestHigh) > 0) {
            log.info("检测到向上突破信号：当前最高价={}, {}日最高价={}",
                    currentHigh, period, highestHigh);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .reason(String.format("向上突破%d日最高价 %.2f", period, highestHigh.doubleValue()))
                    .build();
        }

        // 向下突破
        if (currentLow.compareTo(lowestLow) < 0) {
            log.info("检测到向下突破信号：当前最低价={}, {}日最低价={}",
                    currentLow, period, lowestLow);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .reason(String.format("向下跌破%d日最低价 %.2f", period, lowestLow.doubleValue()))
                    .build();
        }

        return null;
    }

    @Override
    public String getStrategyType() {
        return "BREAKOUT";
    }

    @Override
    public String getStrategyName() {
        return "突破策略";
    }

    @Override
    public String getStrategyDescription() {
        return "基于价格突破的趋势跟随策略，价格突破近期最高价时买入，跌破最低价时卖出。参数格式：回看周期（如：20）";
    }

    @Override
    public boolean validateParams(String params) {
        if (params == null || params.isEmpty()) {
            return false;
        }

        try {
            int period = Integer.parseInt(params.trim());
            return period > 0 && period <= 200;
        } catch (Exception e) {
            return false;
        }
    }
}