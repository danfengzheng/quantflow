package com.quantflow.trading.strategy.engine.strategies;

import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.engine.IStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * MACD策略
 */
@Slf4j
@Component
public class MACDStrategy implements IStrategy {

    private Strategy strategy;
    private int fastPeriod;
    private int slowPeriod;
    private int signalPeriod;

    @Override
    public void init(Strategy strategy) {
        this.strategy = strategy;

        String params = strategy.getParamsConfig();
        if (params != null && !params.isEmpty()) {
            String[] paramArray = params.split(",");
            if (paramArray.length >= 1) {
                this.fastPeriod = Integer.parseInt(paramArray[0].trim());
            }
            if (paramArray.length >= 2) {
                this.slowPeriod = Integer.parseInt(paramArray[1].trim());
            }
            if (paramArray.length >= 3) {
                this.signalPeriod = Integer.parseInt(paramArray[2].trim());
            }
        }

        if (this.fastPeriod == 0) this.fastPeriod = 12;
        if (this.slowPeriod == 0) this.slowPeriod = 26;
        if (this.signalPeriod == 0) this.signalPeriod = 9;

        log.info("MACD策略初始化：快线={}, 慢线={}, 信号线={}",
                fastPeriod, slowPeriod, signalPeriod);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        int minRequired = slowPeriod + signalPeriod;
        if (klines.size() < minRequired) {
            log.debug("K线数据不足，需要至少 {} 根K线", minRequired);
            return null;
        }

        List<BigDecimal> prices = new ArrayList<>();
        for (KLine kline : klines) {
            prices.add(kline.getClose());
        }

        List<BigDecimal> emaFast = calculateEMA(prices, fastPeriod);
        List<BigDecimal> emaSlow = calculateEMA(prices, slowPeriod);

        List<BigDecimal> dif = new ArrayList<>();
        for (int i = 0; i < emaSlow.size(); i++) {
            dif.add(emaFast.get(i + (emaFast.size() - emaSlow.size())).subtract(emaSlow.get(i)));
        }

        List<BigDecimal> dea = calculateEMA(dif, signalPeriod);

        if (dea.size() < 2) {
            return null;
        }

        BigDecimal currentDIF = dif.get(dif.size() - 1);
        BigDecimal currentDEA = dea.get(dea.size() - 1);
        BigDecimal prevDIF = dif.get(dif.size() - 2);
        BigDecimal prevDEA = dea.get(dea.size() - 2);

        KLine currentKline = klines.get(klines.size() - 1);
        BigDecimal currentPrice = currentKline.getClose();

        // 金叉
        if (prevDIF.compareTo(prevDEA) < 0 && currentDIF.compareTo(currentDEA) >= 0) {
            log.info("检测到MACD金叉信号：DIF={}, DEA={}", currentDIF, currentDEA);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .reason(String.format("MACD金叉，DIF=%.4f, DEA=%.4f",
                            currentDIF.doubleValue(), currentDEA.doubleValue()))
                    .build();
        }

        // 死叉
        if (prevDIF.compareTo(prevDEA) > 0 && currentDIF.compareTo(currentDEA) <= 0) {
            log.info("检测到MACD死叉信号：DIF={}, DEA={}", currentDIF, currentDEA);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .reason(String.format("MACD死叉，DIF=%.4f, DEA=%.4f",
                            currentDIF.doubleValue(), currentDEA.doubleValue()))
                    .build();
        }

        return null;
    }

    private List<BigDecimal> calculateEMA(List<BigDecimal> prices, int period) {
        List<BigDecimal> ema = new ArrayList<>();

        if (prices.size() < period) {
            return ema;
        }

        BigDecimal multiplier = BigDecimal.valueOf(2.0 / (period + 1));

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = 0; i < period; i++) {
            sum = sum.add(prices.get(i));
        }
        BigDecimal sma = sum.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
        ema.add(sma);

        for (int i = period; i < prices.size(); i++) {
            BigDecimal currentEma = prices.get(i).subtract(ema.get(ema.size() - 1))
                    .multiply(multiplier)
                    .add(ema.get(ema.size() - 1));
            ema.add(currentEma);
        }

        return ema;
    }

    @Override
    public String getStrategyType() {
        return "MACD";
    }

    @Override
    public String getStrategyName() {
        return "MACD策略";
    }

    @Override
    public String getStrategyDescription() {
        return "基于MACD指标的趋势跟踪策略，DIF上穿DEA时买入（金叉），下穿时卖出（死叉）。参数格式：快线,慢线,信号线（如：12,26,9）";
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

            int fast = Integer.parseInt(paramArray[0].trim());
            int slow = Integer.parseInt(paramArray[1].trim());
            int signal = Integer.parseInt(paramArray[2].trim());

            return fast > 0 && slow > fast && signal > 0;
        } catch (Exception e) {
            return false;
        }
    }
}