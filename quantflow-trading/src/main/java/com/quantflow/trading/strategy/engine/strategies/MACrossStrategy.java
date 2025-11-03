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
 * 均线交叉策略
 */
@Slf4j
@Component
public class MACrossStrategy implements IStrategy {

    private Strategy strategy;
    private int shortPeriod;  // 短期均线
    private int longPeriod;   // 长期均线

    @Override
    public void init(Strategy strategy) {
        this.strategy = strategy;

        // 解析参数
        String params = strategy.getParamsConfig();
        if (params != null && !params.isEmpty()) {
            String[] paramArray = params.split(",");
            if (paramArray.length >= 2) {
                this.shortPeriod = Integer.parseInt(paramArray[0].trim());
                this.longPeriod = Integer.parseInt(paramArray[1].trim());
            }
        }

        // 默认值
        if (this.shortPeriod == 0) this.shortPeriod = 5;
        if (this.longPeriod == 0) this.longPeriod = 20;

        log.info("均线交叉策略初始化：短期={}, 长期={}", shortPeriod, longPeriod);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        if (klines.size() < longPeriod + 1) {
            log.debug("K线数据不足，需要至少 {} 根K线", longPeriod + 1);
            return null;
        }

        // 计算当前短期均线和长期均线
        BigDecimal currentShortMA = calculateMA(klines, shortPeriod, 0);
        BigDecimal currentLongMA = calculateMA(klines, longPeriod, 0);

        // 计算前一根K线的短期均线和长期均线
        BigDecimal prevShortMA = calculateMA(klines, shortPeriod, 1);
        BigDecimal prevLongMA = calculateMA(klines, longPeriod, 1);

        KLine currentKline = klines.get(klines.size() - 1);
        BigDecimal currentPrice = currentKline.getClose();

        // 金叉：短期均线上穿长期均线
        if (prevShortMA.compareTo(prevLongMA) < 0 && currentShortMA.compareTo(currentLongMA) >= 0) {
            log.info("检测到金叉信号：短期MA={}, 长期MA={}, 价格={}",
                    currentShortMA, currentLongMA, currentPrice);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .reason(String.format("金叉，短期MA=%.2f，长期MA=%.2f",
                            currentShortMA.doubleValue(), currentLongMA.doubleValue()))
                    .build();
        }

        // 死叉：短期均线下穿长期均线
        if (prevShortMA.compareTo(prevLongMA) > 0 && currentShortMA.compareTo(currentLongMA) <= 0) {
            log.info("检测到死叉信号：短期MA={}, 长期MA={}, 价格={}",
                    currentShortMA, currentLongMA, currentPrice);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .reason(String.format("死叉，短期MA=%.2f，长期MA=%.2f",
                            currentShortMA.doubleValue(), currentLongMA.doubleValue()))
                    .build();
        }

        return null;
    }

    /**
     * 计算移动平均线
     * @param klines K线数据
     * @param period 周期
     * @param offset 偏移量（0表示最新，1表示前一根）
     */
    private BigDecimal calculateMA(List<KLine> klines, int period, int offset) {
        int endIndex = klines.size() - 1 - offset;
        int startIndex = endIndex - period + 1;

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = startIndex; i <= endIndex; i++) {
            sum = sum.add(klines.get(i).getClose());
        }

        return sum.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
    }

    @Override
    public String getStrategyType() {
        return "MA_CROSS";
    }

    @Override
    public String getStrategyName() {
        return "均线交叉策略";
    }

    @Override
    public String getStrategyDescription() {
        return "基于移动平均线交叉的经典策略，当短期均线上穿长期均线时买入（金叉），下穿时卖出（死叉）。参数格式：短期,长期（如：5,20）";
    }

    @Override
    public boolean validateParams(String params) {
        if (params == null || params.isEmpty()) {
            return false;
        }

        try {
            String[] paramArray = params.split(",");
            if (paramArray.length != 2) {
                return false;
            }

            int shortPeriod = Integer.parseInt(paramArray[0].trim());
            int longPeriod = Integer.parseInt(paramArray[1].trim());

            return shortPeriod > 0 && longPeriod > shortPeriod;
        } catch (Exception e) {
            return false;
        }
    }
}