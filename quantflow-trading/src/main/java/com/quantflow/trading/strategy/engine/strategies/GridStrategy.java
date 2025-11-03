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
 * 网格交易策略
 */
@Slf4j
@Component
public class GridStrategy implements IStrategy {

    private Strategy strategy;
    private BigDecimal lowerPrice;
    private BigDecimal upperPrice;
    private int gridCount;
    private BigDecimal gridSize;
    private BigDecimal lastActionPrice;

    @Override
    public void init(Strategy strategy) {
        this.strategy = strategy;

        String params = strategy.getParamsConfig();
        if (params != null && !params.isEmpty()) {
            String[] paramArray = params.split(",");
            if (paramArray.length >= 3) {
                this.lowerPrice = new BigDecimal(paramArray[0].trim());
                this.upperPrice = new BigDecimal(paramArray[1].trim());
                this.gridCount = Integer.parseInt(paramArray[2].trim());
            }
        }

        if (this.lowerPrice == null || this.upperPrice == null || this.gridCount == 0) {
            this.lowerPrice = new BigDecimal("3000");
            this.upperPrice = new BigDecimal("5000");
            this.gridCount = 10;
        }

        this.gridSize = this.upperPrice.subtract(this.lowerPrice)
                .divide(BigDecimal.valueOf(this.gridCount), 2, RoundingMode.HALF_UP);

        this.lastActionPrice = null;

        log.info("网格交易策略初始化：下界={}, 上界={}, 网格数={}, 网格大小={}",
                lowerPrice, upperPrice, gridCount, gridSize);
    }

    @Override
    public StrategySignal calculate(List<KLine> klines) {
        if (klines.isEmpty()) {
            return null;
        }

        KLine currentKline = klines.get(klines.size() - 1);
        BigDecimal currentPrice = currentKline.getClose();

        if (currentPrice.compareTo(lowerPrice) < 0 || currentPrice.compareTo(upperPrice) > 0) {
            log.debug("价格超出网格范围：price={}", currentPrice);
            return null;
        }

        BigDecimal priceFromLower = currentPrice.subtract(lowerPrice);
        int currentGrid = priceFromLower.divide(gridSize, 0, RoundingMode.DOWN).intValue();

        if (lastActionPrice == null) {
            lastActionPrice = currentPrice;
            return null;
        }

        BigDecimal lastPriceFromLower = lastActionPrice.subtract(lowerPrice);
        int lastGrid = lastPriceFromLower.divide(gridSize, 0, RoundingMode.DOWN).intValue();

        // 价格下跌穿过网格线
        if (currentGrid < lastGrid) {
            BigDecimal gridLine = lowerPrice.add(gridSize.multiply(BigDecimal.valueOf(currentGrid + 1)));

            log.info("检测到网格买入信号：价格={}, 网格线={}", currentPrice, gridLine);

            lastActionPrice = currentPrice;

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("BUY")
                    .price(currentPrice)
                    .quantity(new BigDecimal("0.01"))
                    .reason(String.format("网格买入，网格线=%.2f", gridLine.doubleValue()))
                    .build();
        }

        // 价格上涨穿过网格线
        if (currentGrid > lastGrid) {
            BigDecimal gridLine = lowerPrice.add(gridSize.multiply(BigDecimal.valueOf(currentGrid)));

            log.info("检测到网格卖出信号：价格={}, 网格线={}", currentPrice, gridLine);

            lastActionPrice = currentPrice;

            return StrategySignal.builder()
                    .strategyId(strategy.getId())
                    .symbol(strategy.getSymbol())
                    .signalType("SELL")
                    .price(currentPrice)
                    .quantity(new BigDecimal("0.01"))
                    .reason(String.format("网格卖出，网格线=%.2f", gridLine.doubleValue()))
                    .build();
        }

        return null;
    }

    @Override
    public String getStrategyType() {
        return "GRID";
    }

    @Override
    public String getStrategyName() {
        return "网格交易策略";
    }

    @Override
    public String getStrategyDescription() {
        return "在价格区间内设置网格进行高抛低吸，适合震荡行情。参数格式：下界,上界,网格数（如：3000,5000,10）";
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

            BigDecimal lower = new BigDecimal(paramArray[0].trim());
            BigDecimal upper = new BigDecimal(paramArray[1].trim());
            int count = Integer.parseInt(paramArray[2].trim());

            return lower.compareTo(BigDecimal.ZERO) > 0 &&
                    upper.compareTo(lower) > 0 &&
                    count > 0 && count <= 100;
        } catch (Exception e) {
            return false;
        }
    }
}