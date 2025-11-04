package com.quantflow.trading.signal.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.quantflow.common.utils.DateUtils;
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.common.exchange.ExchangeFactory;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import com.quantflow.trading.common.exchange.binance.BinanceApiClient;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.signal.domain.MarketSignal;
import com.quantflow.trading.signal.mapper.MarketSignalMapper;
import com.quantflow.trading.signal.service.IMarketSignalService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 市场信号分析Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
@Slf4j
@Service
public class MarketSignalServiceImpl implements IMarketSignalService {
    
    @Autowired
    private MarketSignalMapper marketSignalMapper;

    /**
     * 查询市场信号分析
     * 
     * @param signalId 市场信号分析主键
     * @return 市场信号分析
     */
    @Override
    public MarketSignal selectMarketSignalBySignalId(Long signalId) {
        return marketSignalMapper.selectMarketSignalBySignalId(signalId);
    }

    /**
     * 查询市场信号分析列表
     * 
     * @param marketSignal 市场信号分析
     * @return 市场信号分析
     */
    @Override
    public List<MarketSignal> selectMarketSignalList(MarketSignal marketSignal) {
        return marketSignalMapper.selectMarketSignalList(marketSignal);
    }

    /**
     * 实时分析市场信号
     * 
     * @param symbol 交易对
     * @param interval K线周期
     * @return 市场信号分析结果
     */
    @Override
    public MarketSignal analyzeMarketSignal(String symbol, String interval) {
        MarketSignal signal = new MarketSignal();
        signal.setSymbol(symbol);
        signal.setInterval(interval);
        signal.setAnalysisTime(new Date());

        // 1. 获取市场数据 (这里需要对接您的市场数据服务)
        MarketData marketData = fetchMarketData(symbol, interval);
        
        if (marketData == null) {
            return null;
        }

        signal.setCurrentPrice(marketData.getCurrentPrice());

        // 2. 计算支撑位和压力位
        calculateSupportResistance(signal, marketData);

        // 3. 判断突破信号
        analyzeBreakout(signal, marketData);

        // 4. 分析超买超卖
        analyzeOverboughtOversold(signal, marketData);

        // 5. 分析MACD信号
        analyzeMacd(signal, marketData);

        // 6. 分析布林带位置
        analyzeBollinger(signal, marketData);

        // 7. 分析成交量
        analyzeVolume(signal, marketData);

        // 8. 判断趋势方向和强度
        analyzeTrend(signal, marketData);

        // 9. 综合评分和推荐
        generateRecommendation(signal, marketData);

        // 10. 计算止损止盈
        calculateStopLossTakeProfit(signal);

        // 11. 评估信号可信度和假信号风险
        evaluateSignalReliability(signal, marketData);

        return signal;
    }

    /**
     * 批量分析多个交易对
     */
    @Override
    public List<MarketSignal> batchAnalyzeMarketSignals(List<String> symbols, String interval) {
        List<MarketSignal> signals = new ArrayList<>();
        for (String symbol : symbols) {
            MarketSignal signal = analyzeMarketSignal(symbol, interval);
            if (signal != null) {
                signals.add(signal);
            }
        }
        return signals;
    }

    /**
     * 计算支撑位和压力位
     */
    private void calculateSupportResistance(MarketSignal signal, MarketData data) {
        BigDecimal currentPrice = data.getCurrentPrice();
        List<BigDecimal> recentHighs = data.getRecentHighs();
        List<BigDecimal> recentLows = data.getRecentLows();

        // 找出最近的3个支撑位（从当前价格往下）
        List<BigDecimal> supports = new ArrayList<>();
        for (BigDecimal low : recentLows) {
            if (low.compareTo(currentPrice) < 0) {
                supports.add(low);
            }
        }
        supports.sort((a, b) -> b.compareTo(a)); // 降序排列

        if (supports.size() >= 1) signal.setSupport1(supports.get(0));
        if (supports.size() >= 2) signal.setSupport2(supports.get(1));
        if (supports.size() >= 3) signal.setSupport3(supports.get(2));

        // 找出最近的3个压力位（从当前价格往上）
        List<BigDecimal> resistances = new ArrayList<>();
        for (BigDecimal high : recentHighs) {
            if (high.compareTo(currentPrice) > 0) {
                resistances.add(high);
            }
        }
        resistances.sort(BigDecimal::compareTo); // 升序排列

        if (resistances.size() >= 1) signal.setResistance1(resistances.get(0));
        if (resistances.size() >= 2) signal.setResistance2(resistances.get(1));
        if (resistances.size() >= 3) signal.setResistance3(resistances.get(2));
    }

    /**
     * 分析突破信号
     */
    private void analyzeBreakout(MarketSignal signal, MarketData data) {
        BigDecimal currentPrice = data.getCurrentPrice();
        BigDecimal resistance1 = signal.getResistance1();
        BigDecimal support1 = signal.getSupport1();

        // 判断是否接近突破（距离压力位或支撑位3%以内）
        if (resistance1 != null) {
            BigDecimal distance = resistance1.subtract(currentPrice)
                    .divide(currentPrice, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            
            if (distance.compareTo(new BigDecimal("3")) <= 0) {
                signal.setNearBreakout("Y");
                signal.setBreakoutDirection("UP");
            }
        }

        if (support1 != null) {
            BigDecimal distance = currentPrice.subtract(support1)
                    .divide(currentPrice, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            
            if (distance.compareTo(new BigDecimal("3")) <= 0) {
                signal.setNearBreakout("Y");
                signal.setBreakoutDirection("DOWN");
            }
        }

        if (!"Y".equals(signal.getNearBreakout())) {
            signal.setNearBreakout("N");
        }
    }

    /**
     * 分析超买超卖
     */
    private void analyzeOverboughtOversold(MarketSignal signal, MarketData data) {
        BigDecimal rsi = calculateRSI(data.getClosePrices(), 14);
        signal.setRsiValue(rsi);

        // RSI > 70 超买
        if (rsi.compareTo(new BigDecimal("70")) > 0) {
            signal.setIsOverbought("Y");
            signal.setIsOversold("N");
        }
        // RSI < 30 超卖
        else if (rsi.compareTo(new BigDecimal("30")) < 0) {
            signal.setIsOverbought("N");
            signal.setIsOversold("Y");
        }
        else {
            signal.setIsOverbought("N");
            signal.setIsOversold("N");
        }
    }

    /**
     * 计算RSI指标
     */
    private BigDecimal calculateRSI(List<BigDecimal> prices, int period) {
        if (prices.size() < period + 1) {
            return new BigDecimal("50"); // 默认值
        }

        BigDecimal avgGain = BigDecimal.ZERO;
        BigDecimal avgLoss = BigDecimal.ZERO;

        // 计算第一个周期的平均涨跌
        for (int i = 1; i <= period; i++) {
            BigDecimal change = prices.get(i).subtract(prices.get(i - 1));
            if (change.compareTo(BigDecimal.ZERO) > 0) {
                avgGain = avgGain.add(change);
            } else {
                avgLoss = avgLoss.add(change.abs());
            }
        }

        avgGain = avgGain.divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);
        avgLoss = avgLoss.divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);

        // 平滑后续周期
        for (int i = period + 1; i < prices.size(); i++) {
            BigDecimal change = prices.get(i).subtract(prices.get(i - 1));
            BigDecimal gain = change.compareTo(BigDecimal.ZERO) > 0 ? change : BigDecimal.ZERO;
            BigDecimal loss = change.compareTo(BigDecimal.ZERO) < 0 ? change.abs() : BigDecimal.ZERO;

            avgGain = avgGain.multiply(new BigDecimal(period - 1)).add(gain)
                    .divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);
            avgLoss = avgLoss.multiply(new BigDecimal(period - 1)).add(loss)
                    .divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);
        }

        if (avgLoss.compareTo(BigDecimal.ZERO) == 0) {
            return new BigDecimal("100");
        }

        BigDecimal rs = avgGain.divide(avgLoss, 4, RoundingMode.HALF_UP);
        BigDecimal rsi = new BigDecimal("100").subtract(
                new BigDecimal("100").divide(BigDecimal.ONE.add(rs), 2, RoundingMode.HALF_UP)
        );

        return rsi;
    }

    /**
     * 分析MACD信号
     */
    private void analyzeMacd(MarketSignal signal, MarketData data) {
        MacdResult macd = calculateMACD(data.getClosePrices());
        
        if (macd.getDif().compareTo(macd.getDea()) > 0 && 
            macd.getPrevDif().compareTo(macd.getPrevDea()) <= 0) {
            signal.setMacdSignal("金叉");
        }
        else if (macd.getDif().compareTo(macd.getDea()) < 0 && 
                 macd.getPrevDif().compareTo(macd.getPrevDea()) >= 0) {
            signal.setMacdSignal("死叉");
        }
        else if (macd.getDif().compareTo(macd.getDea()) > 0) {
            signal.setMacdSignal("多头");
        }
        else {
            signal.setMacdSignal("空头");
        }
    }

    /**
     * 计算MACD
     */
    private MacdResult calculateMACD(List<BigDecimal> prices) {
        // 简化实现，实际应该使用完整的EMA计算
        int size = prices.size();
        if (size < 26) {
            return new MacdResult(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        }

        BigDecimal ema12 = calculateEMA(prices, 12);
        BigDecimal ema26 = calculateEMA(prices, 26);
        BigDecimal dif = ema12.subtract(ema26);
        
        // 简化：用前一个周期的DIF作为DEA的近似
        BigDecimal dea = dif.multiply(new BigDecimal("0.9")); // 简化计算
        
        BigDecimal prevDif = dif.multiply(new BigDecimal("0.95")); // 模拟前一个DIF
        BigDecimal prevDea = dea.multiply(new BigDecimal("0.95")); // 模拟前一个DEA

        return new MacdResult(dif, dea, prevDif, prevDea);
    }

    /**
     * 计算EMA
     */
    private BigDecimal calculateEMA(List<BigDecimal> prices, int period) {
        if (prices.size() < period) {
            return prices.get(prices.size() - 1);
        }

        BigDecimal multiplier = new BigDecimal("2").divide(
                new BigDecimal(period + 1), 4, RoundingMode.HALF_UP
        );

        BigDecimal ema = prices.get(0);
        for (int i = 1; i < prices.size(); i++) {
            ema = prices.get(i).subtract(ema).multiply(multiplier).add(ema);
        }

        return ema;
    }

    /**
     * 分析布林带位置
     */
    private void analyzeBollinger(MarketSignal signal, MarketData data) {
        BollingerBands bb = calculateBollingerBands(data.getClosePrices(), 20, 2);
        BigDecimal currentPrice = data.getCurrentPrice();

        if (currentPrice.compareTo(bb.getUpperBand()) >= 0) {
            signal.setBollingerPosition("上轨");
        }
        else if (currentPrice.compareTo(bb.getLowerBand()) <= 0) {
            signal.setBollingerPosition("下轨");
        }
        else if (currentPrice.compareTo(bb.getMiddleBand()) > 0) {
            signal.setBollingerPosition("中上");
        }
        else {
            signal.setBollingerPosition("中下");
        }
    }

    /**
     * 计算布林带
     */
    private BollingerBands calculateBollingerBands(List<BigDecimal> prices, int period, int stdDev) {
        if (prices.size() < period) {
            BigDecimal price = prices.get(prices.size() - 1);
            return new BollingerBands(price, price, price);
        }

        // 计算简单移动平均
        BigDecimal sum = BigDecimal.ZERO;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum = sum.add(prices.get(i));
        }
        BigDecimal sma = sum.divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);

        // 计算标准差
        BigDecimal variance = BigDecimal.ZERO;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            BigDecimal diff = prices.get(i).subtract(sma);
            variance = variance.add(diff.multiply(diff));
        }
        variance = variance.divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);
        BigDecimal stdDeviation = new BigDecimal(Math.sqrt(variance.doubleValue()));

        BigDecimal upperBand = sma.add(stdDeviation.multiply(new BigDecimal(stdDev)));
        BigDecimal lowerBand = sma.subtract(stdDeviation.multiply(new BigDecimal(stdDev)));

        return new BollingerBands(upperBand, sma, lowerBand);
    }

    /**
     * 分析成交量
     */
    private void analyzeVolume(MarketSignal signal, MarketData data) {
        BigDecimal currentVolume = data.getCurrentVolume();
        BigDecimal avgVolume = data.getAverageVolume();

        BigDecimal ratio = currentVolume.divide(avgVolume, 2, RoundingMode.HALF_UP);

        if (ratio.compareTo(new BigDecimal("1.5")) >= 0) {
            signal.setVolumeStatus("放量");
        }
        else if (ratio.compareTo(new BigDecimal("0.7")) <= 0) {
            signal.setVolumeStatus("缩量");
        }
        else {
            signal.setVolumeStatus("正常");
        }
    }

    /**
     * 分析趋势
     */
    private void analyzeTrend(MarketSignal signal, MarketData data) {
        List<BigDecimal> prices = data.getClosePrices();
        if (prices.size() < 20) {
            signal.setTrendDirection("SIDEWAYS");
            signal.setTrendStrength(50);
            return;
        }

        BigDecimal ma20 = calculateSMA(prices, 20);
        BigDecimal ma50 = calculateSMA(prices, 50);
        BigDecimal currentPrice = data.getCurrentPrice();

        // 判断趋势方向
        if (currentPrice.compareTo(ma20) > 0 && ma20.compareTo(ma50) > 0) {
            signal.setTrendDirection("UP");
            
            // 计算趋势强度（价格距离均线的百分比）
            BigDecimal strength = currentPrice.subtract(ma50)
                    .divide(ma50, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .multiply(new BigDecimal("2")); // 放大系数
            
            signal.setTrendStrength(Math.min(100, strength.intValue()));
        }
        else if (currentPrice.compareTo(ma20) < 0 && ma20.compareTo(ma50) < 0) {
            signal.setTrendDirection("DOWN");
            
            BigDecimal strength = ma50.subtract(currentPrice)
                    .divide(ma50, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"))
                    .multiply(new BigDecimal("2"));
            
            signal.setTrendStrength(Math.min(100, strength.intValue()));
        }
        else {
            signal.setTrendDirection("SIDEWAYS");
            signal.setTrendStrength(30);
        }
    }

    /**
     * 计算简单移动平均
     */
    private BigDecimal calculateSMA(List<BigDecimal> prices, int period) {
        if (prices.size() < period) {
            return prices.get(prices.size() - 1);
        }

        BigDecimal sum = BigDecimal.ZERO;
        for (int i = prices.size() - period; i < prices.size(); i++) {
            sum = sum.add(prices.get(i));
        }

        return sum.divide(new BigDecimal(period), 4, RoundingMode.HALF_UP);
    }

    /**
     * 生成综合推荐
     */
    private void generateRecommendation(MarketSignal signal, MarketData data) {
        int score = 50; // 基础分50分
        StringBuilder reason = new StringBuilder();
        
        // 1. RSI超卖/超买评分
        if ("Y".equals(signal.getIsOversold())) {
            score += 15;
            reason.append("RSI超卖(").append(signal.getRsiValue()).append(")，反弹概率大；");
        }
        else if ("Y".equals(signal.getIsOverbought())) {
            score -= 15;
            reason.append("RSI超买(").append(signal.getRsiValue()).append(")，回调风险高；");
        }

        // 2. MACD信号评分
        if ("金叉".equals(signal.getMacdSignal())) {
            score += 12;
            reason.append("MACD金叉，买入信号；");
        }
        else if ("死叉".equals(signal.getMacdSignal())) {
            score -= 12;
            reason.append("MACD死叉，卖出信号；");
        }

        // 3. 趋势方向评分
        if ("UP".equals(signal.getTrendDirection())) {
            score += 10;
            reason.append("上升趋势，强度").append(signal.getTrendStrength()).append("；");
        }
        else if ("DOWN".equals(signal.getTrendDirection())) {
            score -= 10;
            reason.append("下降趋势，强度").append(signal.getTrendStrength()).append("；");
        }

        // 4. 布林带位置评分
        if ("下轨".equals(signal.getBollingerPosition())) {
            score += 8;
            reason.append("价格触及布林带下轨，支撑强；");
        }
        else if ("上轨".equals(signal.getBollingerPosition())) {
            score -= 8;
            reason.append("价格触及布林带上轨，压力大；");
        }

        // 5. 成交量评分
        if ("放量".equals(signal.getVolumeStatus())) {
            if (score > 50) {
                score += 5;
                reason.append("成交量放大，确认信号；");
            } else {
                score -= 5;
                reason.append("成交量放大，警惕下跌；");
            }
        }

        // 6. 突破评分
        if ("Y".equals(signal.getNearBreakout())) {
            if ("UP".equals(signal.getBreakoutDirection())) {
                score += 8;
                reason.append("接近向上突破压力位；");
            } else {
                score -= 8;
                reason.append("接近向下突破支撑位；");
            }
        }

        // 7. 支撑压力位评分
        if (signal.getSupport1() != null) {
            BigDecimal distance = signal.getCurrentPrice().subtract(signal.getSupport1())
                    .divide(signal.getCurrentPrice(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            
            if (distance.compareTo(new BigDecimal("2")) < 0) {
                score += 5;
                reason.append("接近强支撑位；");
            }
        }

        // 限制分数范围
        score = Math.max(0, Math.min(100, score));
        signal.setSignalScore(score);

        // 生成推荐操作
        if (score >= 70) {
            signal.setRecommendAction("BUY");
            signal.setRiskLevel("MEDIUM");
        }
        else if (score >= 55) {
            signal.setRecommendAction("HOLD");
            signal.setRiskLevel("LOW");
        }
        else if (score >= 45) {
            signal.setRecommendAction("HOLD");
            signal.setRiskLevel("MEDIUM");
        }
        else if (score >= 30) {
            signal.setRecommendAction("SELL");
            signal.setRiskLevel("MEDIUM");
        }
        else {
            signal.setRecommendAction("SELL");
            signal.setRiskLevel("HIGH");
        }

        signal.setRecommendReason(reason.toString());
    }

    /**
     * 计算止损止盈
     */
    private void calculateStopLossTakeProfit(MarketSignal signal) {
        BigDecimal currentPrice = signal.getCurrentPrice();
        
        if ("BUY".equals(signal.getRecommendAction())) {
            // 止损：最近的支撑位，或当前价格的3%
            if (signal.getSupport1() != null) {
                signal.setStopLossPrice(signal.getSupport1().multiply(new BigDecimal("0.995")));
            } else {
                signal.setStopLossPrice(currentPrice.multiply(new BigDecimal("0.97")));
            }

            // 止盈：最近的压力位，或当前价格的5-8%
            if (signal.getResistance1() != null) {
                signal.setTakeProfitPrice(signal.getResistance1().multiply(new BigDecimal("0.995")));
            } else {
                signal.setTakeProfitPrice(currentPrice.multiply(new BigDecimal("1.06")));
            }
        }
        else if ("SELL".equals(signal.getRecommendAction())) {
            // 卖出时的止损止盈（做空逻辑）
            if (signal.getResistance1() != null) {
                signal.setStopLossPrice(signal.getResistance1().multiply(new BigDecimal("1.005")));
            } else {
                signal.setStopLossPrice(currentPrice.multiply(new BigDecimal("1.03")));
            }

            if (signal.getSupport1() != null) {
                signal.setTakeProfitPrice(signal.getSupport1().multiply(new BigDecimal("1.005")));
            } else {
                signal.setTakeProfitPrice(currentPrice.multiply(new BigDecimal("0.94")));
            }
        }
    }

    /**
     * 评估信号可信度
     */
    private void evaluateSignalReliability(MarketSignal signal, MarketData data) {
        int reliability = 70; // 基础可信度70%
        
        // 1. 多个指标同向确认，增加可信度
        int bullishSignals = 0;
        int bearishSignals = 0;

        if ("Y".equals(signal.getIsOversold())) bullishSignals++;
        if ("Y".equals(signal.getIsOverbought())) bearishSignals++;
        if ("金叉".equals(signal.getMacdSignal())) bullishSignals++;
        if ("死叉".equals(signal.getMacdSignal())) bearishSignals++;
        if ("UP".equals(signal.getTrendDirection())) bullishSignals++;
        if ("DOWN".equals(signal.getTrendDirection())) bearishSignals++;

        // 信号一致性高，增加可信度
        if (bullishSignals >= 3 || bearishSignals >= 3) {
            reliability += 15;
        }

        // 2. 成交量确认
        if ("放量".equals(signal.getVolumeStatus())) {
            reliability += 10;
        }

        // 3. 趋势强度高，可信度高
        if (signal.getTrendStrength() != null && signal.getTrendStrength() > 70) {
            reliability += 5;
        }

        reliability = Math.min(100, reliability);
        signal.setSignalReliability(reliability);

        // 评估假信号风险
        if (reliability >= 85) {
            signal.setFalseSignalRisk("LOW");
        }
        else if (reliability >= 70) {
            signal.setFalseSignalRisk("MEDIUM");
        }
        else {
            signal.setFalseSignalRisk("HIGH");
        }
    }

    @Autowired
    private ExchangeFactory exchangeFactory;
    @Autowired
    private BinanceApiClient binanceApiClient;
    /**
     * 获取市场数据（需要对接实际的数据源）
     */
    private MarketData fetchMarketData(String symbol, String interval) {
        // TODO: 这里需要对接您的市场数据服务
        // 可以调用币安API、火币API等获取实时数据
        // 这里返回模拟数据作为示例
        Account account = new Account();
        account.setExchange("binance");
        account.setIsTestnet(0);
        IExchangeAdapter adapter = exchangeFactory.createAdapter(account);
        try {
            // 1. 调用您的K线API获取数据
            // limit=100: 获取最近100根K线，用于计算技术指标
            // isTestnet=false: 使用正式环境
            List<KLine> klines = adapter.getKlines(symbol, interval, 100);

            // 验证数据
            if (klines == null || klines.isEmpty()) {
                log.error("获取K线数据失败: symbol={}, interval={}", symbol, interval);
                return null;
            }

            // 确保数据按时间升序排列（从旧到新）
            klines.sort(Comparator.comparing(KLine::getOpenTime));

            // 2. 提取数据用于技术指标计算
            MarketData data = new MarketData();
            List<BigDecimal> closePrices = new ArrayList<>();
            List<BigDecimal> highs = new ArrayList<>();
            List<BigDecimal> lows = new ArrayList<>();
            List<BigDecimal> volumes = new ArrayList<>();

            // 遍历所有K线，提取OHLCV数据
            for (KLine kline : klines) {
                closePrices.add(kline.getClose());
                highs.add(kline.getHigh());
                lows.add(kline.getLow());
                volumes.add(kline.getVolume());
            }

            // 3. 设置基础数据
            data.setClosePrices(closePrices);

            // 当前价格 = 最后一根K线的收盘价
            KLine lastKline = klines.get(klines.size() - 1);
            data.setCurrentPrice(lastKline.getClose());

            // 4. 提取最近的高点和低点（用于支撑压力位计算）
            // 使用最近20根K线的高低点
            List<BigDecimal> recentHighs = new ArrayList<>();
            List<BigDecimal> recentLows = new ArrayList<>();

            int lookback = Math.min(20, klines.size());
            int startIndex = klines.size() - lookback;

            for (int i = startIndex; i < klines.size(); i++) {
                recentHighs.add(klines.get(i).getHigh());
                recentLows.add(klines.get(i).getLow());
            }

            data.setRecentHighs(recentHighs);
            data.setRecentLows(recentLows);

            // 5. 计算平均成交量
            BigDecimal totalVolume = BigDecimal.ZERO;
            for (BigDecimal volume : volumes) {
                totalVolume = totalVolume.add(volume);
            }

            BigDecimal avgVolume = totalVolume.divide(
                    new BigDecimal(volumes.size()),
                    4,
                    RoundingMode.HALF_UP
            );

            data.setAverageVolume(avgVolume);
            data.setCurrentVolume(lastKline.getVolume());

            // 6. 记录日志
            log.info("成功获取K线数据: symbol={}, interval={}, count={}, currentPrice={}, " +
                            "priceRange=[{} - {}], avgVolume={}",
                    symbol, interval, klines.size(), data.getCurrentPrice(),
                    Collections.min(lows), Collections.max(highs), avgVolume);

            return data;

        } catch (IOException e) {
            log.error("调用K线API失败: symbol={}, interval={}, error={}",
                    symbol, interval, e.getMessage(), e);
            return null;
        } catch (Exception e) {
            log.error("解析K线数据失败: symbol={}, interval={}, error={}",
                    symbol, interval, e.getMessage(), e);
            // 打印详细堆栈信息用于调试
            log.debug("详细错误信息:", e);
            return null;
        }
    }

    @Override
    public int insertMarketSignal(MarketSignal marketSignal) {
        marketSignal.setCreateTime(DateUtils.getNowDate());
        return marketSignalMapper.insertMarketSignal(marketSignal);
    }

    @Override
    public int updateMarketSignal(MarketSignal marketSignal) {
        marketSignal.setUpdateTime(DateUtils.getNowDate());
        return marketSignalMapper.updateMarketSignal(marketSignal);
    }

    @Override
    public int deleteMarketSignalBySignalIds(Long[] signalIds) {
        return marketSignalMapper.deleteMarketSignalBySignalIds(signalIds);
    }

    @Override
    public int deleteMarketSignalBySignalId(Long signalId) {
        return marketSignalMapper.deleteMarketSignalBySignalId(signalId);
    }

    // ========== 内部类 ==========

    /**
     * 市场数据
     */
    private static class MarketData {
        private BigDecimal currentPrice;
        private BigDecimal currentVolume;
        private BigDecimal averageVolume;
        private List<BigDecimal> closePrices;
        private List<BigDecimal> recentHighs;
        private List<BigDecimal> recentLows;

        // Getters and Setters
        public BigDecimal getCurrentPrice() { return currentPrice; }
        public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }
        public BigDecimal getCurrentVolume() { return currentVolume; }
        public void setCurrentVolume(BigDecimal currentVolume) { this.currentVolume = currentVolume; }
        public BigDecimal getAverageVolume() { return averageVolume; }
        public void setAverageVolume(BigDecimal averageVolume) { this.averageVolume = averageVolume; }
        public List<BigDecimal> getClosePrices() { return closePrices; }
        public void setClosePrices(List<BigDecimal> closePrices) { this.closePrices = closePrices; }
        public List<BigDecimal> getRecentHighs() { return recentHighs; }
        public void setRecentHighs(List<BigDecimal> recentHighs) { this.recentHighs = recentHighs; }
        public List<BigDecimal> getRecentLows() { return recentLows; }
        public void setRecentLows(List<BigDecimal> recentLows) { this.recentLows = recentLows; }
    }

    /**
     * MACD结果
     */
    private static class MacdResult {
        private BigDecimal dif;
        private BigDecimal dea;
        private BigDecimal prevDif;
        private BigDecimal prevDea;

        public MacdResult(BigDecimal dif, BigDecimal dea, BigDecimal prevDif, BigDecimal prevDea) {
            this.dif = dif;
            this.dea = dea;
            this.prevDif = prevDif;
            this.prevDea = prevDea;
        }

        public BigDecimal getDif() { return dif; }
        public BigDecimal getDea() { return dea; }
        public BigDecimal getPrevDif() { return prevDif; }
        public BigDecimal getPrevDea() { return prevDea; }
    }

    /**
     * 布林带结果
     */
    private static class BollingerBands {
        private BigDecimal upperBand;
        private BigDecimal middleBand;
        private BigDecimal lowerBand;

        public BollingerBands(BigDecimal upperBand, BigDecimal middleBand, BigDecimal lowerBand) {
            this.upperBand = upperBand;
            this.middleBand = middleBand;
            this.lowerBand = lowerBand;
        }

        public BigDecimal getUpperBand() { return upperBand; }
        public BigDecimal getMiddleBand() { return middleBand; }
        public BigDecimal getLowerBand() { return lowerBand; }
    }
}
