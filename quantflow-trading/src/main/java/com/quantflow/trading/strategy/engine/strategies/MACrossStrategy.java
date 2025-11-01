package com.quantflow.trading.strategy.engine.strategies;

import com.alibaba.fastjson2.JSONObject;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.engine.IStrategy;
import com.quantflow.trading.strategy.engine.IndicatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * 均线交叉策略
 * 当短期均线上穿长期均线时买入（金叉）
 * 当短期均线下穿长期均线时卖出（死叉）
 */
@Slf4j
@Component
public class MACrossStrategy implements IStrategy {

    private Strategy strategy;
    private int shortPeriod = 5;   // 短期均线周期
    private int longPeriod = 20;   // 长期均线周期

    @Override
    public void init(Strategy strategy) {
        this.strategy = strategy;

        // 解析策略参数
        JSONObject params = strategy.getParamsObject();
        if (params.containsKey("shortPeriod")) {
            this.shortPeriod = params.getIntValue("shortPeriod");
        }
        if (params.containsKey("longPeriod")) {
            this.longPeriod = params.getIntValue("longPeriod");
        }

        log.info("均线交叉策略初始化：短期={}, 长期={}", shortPeriod, longPeriod);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        if (klines == null || klines.size() < longPeriod + 1) {
            log.debug("K线数据不足，需要至少 {} 根K线", longPeriod + 1);
            return null;
        }

        // 计算短期和长期均线
        List<BigDecimal> shortMA = IndicatorUtil.calculateSMA(klines, shortPeriod);
        List<BigDecimal> longMA = IndicatorUtil.calculateSMA(klines, longPeriod);

        if (shortMA.isEmpty() || longMA.isEmpty()) {
            return null;
        }

        // 获取最新的两个均线值（当前和前一个）
        BigDecimal currentShortMA = IndicatorUtil.getLatest(shortMA);
        BigDecimal previousShortMA = IndicatorUtil.get(shortMA, 1);
        BigDecimal currentLongMA = IndicatorUtil.getLatest(longMA);
        BigDecimal previousLongMA = IndicatorUtil.get(longMA, 1);

        if (currentShortMA == null || previousShortMA == null ||
                currentLongMA == null || previousLongMA == null) {
            return null;
        }

        // 获取当前价格
        BigDecimal currentPrice = klines.get(klines.size() - 1).getClose();

        // 判断金叉（买入信号）
        // 前一根K线：短期MA < 长期MA
        // 当前K线：短期MA > 长期MA
        if (previousShortMA.compareTo(previousLongMA) < 0 &&
                currentShortMA.compareTo(currentLongMA) > 0) {

            log.info("检测到金叉信号：短期MA={}, 长期MA={}, 价格={}",
                    currentShortMA, currentLongMA, currentPrice);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .reason(String.format("金叉：短期MA(%d)上穿长期MA(%d)", shortPeriod, longPeriod))
                    .confidence(BigDecimal.valueOf(80))
                    .status(0)
                    .build();
        }

        // 判断死叉（卖出信号）
        // 前一根K线：短期MA > 长期MA
        // 当前K线：短期MA < 长期MA
        if (previousShortMA.compareTo(previousLongMA) > 0 &&
                currentShortMA.compareTo(currentLongMA) < 0) {

            log.info("检测到死叉信号：短期MA={}, 长期MA={}, 价格={}",
                    currentShortMA, currentLongMA, currentPrice);

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .reason(String.format("死叉：短期MA(%d)下穿长期MA(%d)", shortPeriod, longPeriod))
                    .confidence(BigDecimal.valueOf(80))
                    .status(0)
                    .build();
        }

        // 没有信号
        return null;
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
        return "基于短期均线和长期均线交叉产生买卖信号。金叉买入，死叉卖出。";
    }

    @Override
    public boolean validateParams(String params) {
        try {
            JSONObject json = JSONObject.parseObject(params);
            int shortPeriod = json.getIntValue("shortPeriod", 5);
            int longPeriod = json.getIntValue("longPeriod", 20);

            // 验证参数合理性
            if (shortPeriod <= 0 || longPeriod <= 0) {
                return false;
            }
            if (shortPeriod >= longPeriod) {
                return false;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}