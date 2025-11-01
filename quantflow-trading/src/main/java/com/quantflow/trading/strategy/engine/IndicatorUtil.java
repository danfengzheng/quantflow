package com.quantflow.trading.strategy.engine;

import com.quantflow.trading.market.domain.KLine;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

/**
 * 技术指标计算工具类
 */
public class IndicatorUtil {

    /**
     * 计算简单移动平均线 (SMA)
     * @param klines K线数据
     * @param period 周期
     * @return MA值列表
     */
    public static List<BigDecimal> calculateSMA(List<KLine> klines, int period) {
        List<BigDecimal> result = new ArrayList<>();

        if (klines == null || klines.size() < period) {
            return result;
        }

        for (int i = 0; i <= klines.size() - period; i++) {
            BigDecimal sum = BigDecimal.ZERO;
            for (int j = i; j < i + period; j++) {
                sum = sum.add(klines.get(j).getClose());
            }
            BigDecimal ma = sum.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
            result.add(ma);
        }

        return result;
    }

    /**
     * 计算指数移动平均线 (EMA)
     * @param klines K线数据
     * @param period 周期
     * @return EMA值列表
     */
    public static List<BigDecimal> calculateEMA(List<KLine> klines, int period) {
        List<BigDecimal> result = new ArrayList<>();

        if (klines == null || klines.size() < period) {
            return result;
        }

        // 计算第一个EMA（使用SMA）
        BigDecimal firstSum = BigDecimal.ZERO;
        for (int i = 0; i < period; i++) {
            firstSum = firstSum.add(klines.get(i).getClose());
        }
        BigDecimal ema = firstSum.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
        result.add(ema);

        // 计算后续EMA
        BigDecimal multiplier = BigDecimal.valueOf(2.0 / (period + 1));
        for (int i = period; i < klines.size(); i++) {
            BigDecimal close = klines.get(i).getClose();
            ema = close.subtract(ema).multiply(multiplier).add(ema);
            result.add(ema);
        }

        return result;
    }

    /**
     * 计算RSI (相对强弱指标)
     * @param klines K线数据
     * @param period 周期（通常为14）
     * @return RSI值列表
     */
    public static List<BigDecimal> calculateRSI(List<KLine> klines, int period) {
        List<BigDecimal> result = new ArrayList<>();

        if (klines == null || klines.size() < period + 1) {
            return result;
        }

        // 计算价格变化
        List<BigDecimal> gains = new ArrayList<>();
        List<BigDecimal> losses = new ArrayList<>();

        for (int i = 1; i < klines.size(); i++) {
            BigDecimal change = klines.get(i).getClose().subtract(klines.get(i - 1).getClose());
            if (change.compareTo(BigDecimal.ZERO) > 0) {
                gains.add(change);
                losses.add(BigDecimal.ZERO);
            } else {
                gains.add(BigDecimal.ZERO);
                losses.add(change.abs());
            }
        }

        // 计算平均涨幅和平均跌幅
        for (int i = period - 1; i < gains.size(); i++) {
            BigDecimal avgGain = BigDecimal.ZERO;
            BigDecimal avgLoss = BigDecimal.ZERO;

            for (int j = i - period + 1; j <= i; j++) {
                avgGain = avgGain.add(gains.get(j));
                avgLoss = avgLoss.add(losses.get(j));
            }

            avgGain = avgGain.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);
            avgLoss = avgLoss.divide(BigDecimal.valueOf(period), 8, RoundingMode.HALF_UP);

            // 计算RSI
            if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
                result.add(BigDecimal.valueOf(100));
            } else {
                BigDecimal rs = avgGain.divide(avgLoss, 8, RoundingMode.HALF_UP);
                BigDecimal rsi = BigDecimal.valueOf(100)
                        .subtract(BigDecimal.valueOf(100)
                                .divide(BigDecimal.ONE.add(rs), 8, RoundingMode.HALF_UP));
                result.add(rsi);
            }
        }

        return result;
    }

    /**
     * 获取最新的指标值
     */
    public static BigDecimal getLatest(List<BigDecimal> indicators) {
        if (indicators == null || indicators.isEmpty()) {
            return null;
        }
        return indicators.get(indicators.size() - 1);
    }

    /**
     * 获取倒数第N个指标值
     */
    public static BigDecimal get(List<BigDecimal> indicators, int index) {
        if (indicators == null || indicators.isEmpty() || index >= indicators.size()) {
            return null;
        }
        return indicators.get(indicators.size() - 1 - index);
    }
}