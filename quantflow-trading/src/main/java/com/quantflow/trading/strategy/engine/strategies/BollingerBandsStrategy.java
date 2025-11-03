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
 * 布林带策略
 */
@Slf4j
@Component
public class BollingerBandsStrategy implements IStrategy {

    private Strategy strategy;
    private int period;
    private double stdMultiple;

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
                this.stdMultiple = Double.parseDouble(paramArray[1].trim());
            }
        }

        if (this.period == 0) this.period = 20;
        if (this.stdMultiple == 0) this.stdMultiple = 2.0;

        log.info("布林带策略初始化：周期={}, 标准差倍数={}", period, stdMultiple);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        if (klines.size() < period) {
            log.debug("K线数据不足，需要至少 {} 根K线", period);
            return null;
        }

        List<KLine> recentKlines = klines.subList(klines.size() - period, klines.size());

        // 计算中轨
        BigDecimal sum = BigDecimal.ZERO;
        for (KLine kline : recentKlines) {
            sum = sum.add(kline.getClose());
        }
        BigDecimal ma = sum.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);

        // 计算标准差
        BigDecimal variance = BigDecimal.ZERO;
        for (KLine kline : recentKlines) {
            BigDecimal diff = kline.getClose().subtract(ma);
            variance = variance.add(diff.multiply(diff));
        }
        variance = variance.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
        double std = Math.sqrt(variance.doubleValue());
        BigDecimal stdDev = BigDecimal.valueOf(std);

        // 计算上下轨
        BigDecimal upperBand = ma.add(stdDev.multiply(BigDecimal.valueOf(stdMultiple)));
        BigDecimal lowerBand = ma.subtract(stdDev.multiply(BigDecimal.valueOf(stdMultiple)));

        KLine currentKline = klines.get(klines.size() - 1);
        BigDecimal currentPrice = currentKline.getClose();

        KLine prevKline = klines.get(klines.size() - 2);
        BigDecimal prevPrice = prevKline.getClose();

        // 买入信号
        if (prevPrice.compareTo(lowerBand) < 0 && currentPrice.compareTo(lowerBand) >= 0) {
            log.info("检测到布林带买入信号：价格={}, 下轨={}", currentPrice, lowerBand);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .reason(String.format("价格触及下轨反弹，下轨=%.2f", lowerBand.doubleValue()))
                    .build();
        }

        // 卖出信号
        if (currentPrice.compareTo(upperBand) >= 0) {
            log.info("检测到布林带卖出信号：价格={}, 上轨={}", currentPrice, upperBand);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .reason(String.format("价格触及上轨，上轨=%.2f", upperBand.doubleValue()))
                    .build();
        }

        return null;
    }

    @Override
    public String getStrategyType() {
        return "BOLLINGER_BANDS";
    }

    @Override
    public String getStrategyName() {
        return "布林带策略";
    }

    @Override
    public String getStrategyDescription() {
        return "基于布林带的均值回归策略，价格触及下轨时买入，触及上轨时卖出。参数格式：周期,标准差倍数（如：20,2）";
    }

    @Override
    public boolean validateParams(String params) {
        if (params == null || params.isEmpty()) {
            return false;
        }

        try {
            String[] paramArray = params.split(",");
            if (paramArray.length < 1 || paramArray.length > 2) {
                return false;
            }

            int period = Integer.parseInt(paramArray[0].trim());
            if (period <= 0) {
                return false;
            }

            if (paramArray.length == 2) {
                double stdMultiple = Double.parseDouble(paramArray[1].trim());
                if (stdMultiple <= 0) {
                    return false;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}