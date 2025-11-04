package com.quantflow.trading.signal.domain;

import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 市场信号分析对象 market_signal
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
public class MarketSignal extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /** 信号ID */
    private Long signalId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** K线周期 */
    @Excel(name = "K线周期")
    private String interval;

    /** 当前价格 */
    @Excel(name = "当前价格")
    private BigDecimal currentPrice;

    /** 支撑位1 */
    @Excel(name = "支撑位1")
    private BigDecimal support1;

    /** 支撑位2 */
    @Excel(name = "支撑位2")
    private BigDecimal support2;

    /** 支撑位3 */
    @Excel(name = "支撑位3")
    private BigDecimal support3;

    /** 压力位1 */
    @Excel(name = "压力位1")
    private BigDecimal resistance1;

    /** 压力位2 */
    @Excel(name = "压力位2")
    private BigDecimal resistance2;

    /** 压力位3 */
    @Excel(name = "压力位3")
    private BigDecimal resistance3;

    /** 是否接近突破 */
    @Excel(name = "是否接近突破")
    private String nearBreakout;

    /** 突破方向 (UP/DOWN) */
    @Excel(name = "突破方向")
    private String breakoutDirection;

    /** 是否超买 */
    @Excel(name = "是否超买")
    private String isOverbought;

    /** 是否超卖 */
    @Excel(name = "是否超卖")
    private String isOversold;

    /** RSI值 */
    @Excel(name = "RSI值")
    private BigDecimal rsiValue;

    /** MACD信号 */
    @Excel(name = "MACD信号")
    private String macdSignal;

    /** 布林带位置 */
    @Excel(name = "布林带位置")
    private String bollingerPosition;

    /** 成交量状态 */
    @Excel(name = "成交量状态")
    private String volumeStatus;

    /** 趋势方向 (UP/DOWN/SIDEWAYS) */
    @Excel(name = "趋势方向")
    private String trendDirection;

    /** 趋势强度 (0-100) */
    @Excel(name = "趋势强度")
    private Integer trendStrength;

    /** 综合信号评分 (0-100) */
    @Excel(name = "综合评分")
    private Integer signalScore;

    /** 推荐操作 (BUY/SELL/HOLD) */
    @Excel(name = "推荐操作")
    private String recommendAction;

    /** 推荐原因 */
    @Excel(name = "推荐原因")
    private String recommendReason;

    /** 风险等级 (LOW/MEDIUM/HIGH) */
    @Excel(name = "风险等级")
    private String riskLevel;

    /** 止损建议价格 */
    @Excel(name = "止损价格")
    private BigDecimal stopLossPrice;

    /** 止盈建议价格 */
    @Excel(name = "止盈价格")
    private BigDecimal takeProfitPrice;

    /** 信号可信度 (0-100) */
    @Excel(name = "信号可信度")
    private Integer signalReliability;

    /** 是否假信号风险 */
    @Excel(name = "假信号风险")
    private String falseSignalRisk;

    /** 分析时间 */
    @Excel(name = "分析时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date analysisTime;

    // Getter and Setter methods

    public Long getSignalId() {
        return signalId;
    }

    public void setSignalId(Long signalId) {
        this.signalId = signalId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getSupport1() {
        return support1;
    }

    public void setSupport1(BigDecimal support1) {
        this.support1 = support1;
    }

    public BigDecimal getSupport2() {
        return support2;
    }

    public void setSupport2(BigDecimal support2) {
        this.support2 = support2;
    }

    public BigDecimal getSupport3() {
        return support3;
    }

    public void setSupport3(BigDecimal support3) {
        this.support3 = support3;
    }

    public BigDecimal getResistance1() {
        return resistance1;
    }

    public void setResistance1(BigDecimal resistance1) {
        this.resistance1 = resistance1;
    }

    public BigDecimal getResistance2() {
        return resistance2;
    }

    public void setResistance2(BigDecimal resistance2) {
        this.resistance2 = resistance2;
    }

    public BigDecimal getResistance3() {
        return resistance3;
    }

    public void setResistance3(BigDecimal resistance3) {
        this.resistance3 = resistance3;
    }

    public String getNearBreakout() {
        return nearBreakout;
    }

    public void setNearBreakout(String nearBreakout) {
        this.nearBreakout = nearBreakout;
    }

    public String getBreakoutDirection() {
        return breakoutDirection;
    }

    public void setBreakoutDirection(String breakoutDirection) {
        this.breakoutDirection = breakoutDirection;
    }

    public String getIsOverbought() {
        return isOverbought;
    }

    public void setIsOverbought(String isOverbought) {
        this.isOverbought = isOverbought;
    }

    public String getIsOversold() {
        return isOversold;
    }

    public void setIsOversold(String isOversold) {
        this.isOversold = isOversold;
    }

    public BigDecimal getRsiValue() {
        return rsiValue;
    }

    public void setRsiValue(BigDecimal rsiValue) {
        this.rsiValue = rsiValue;
    }

    public String getMacdSignal() {
        return macdSignal;
    }

    public void setMacdSignal(String macdSignal) {
        this.macdSignal = macdSignal;
    }

    public String getBollingerPosition() {
        return bollingerPosition;
    }

    public void setBollingerPosition(String bollingerPosition) {
        this.bollingerPosition = bollingerPosition;
    }

    public String getVolumeStatus() {
        return volumeStatus;
    }

    public void setVolumeStatus(String volumeStatus) {
        this.volumeStatus = volumeStatus;
    }

    public String getTrendDirection() {
        return trendDirection;
    }

    public void setTrendDirection(String trendDirection) {
        this.trendDirection = trendDirection;
    }

    public Integer getTrendStrength() {
        return trendStrength;
    }

    public void setTrendStrength(Integer trendStrength) {
        this.trendStrength = trendStrength;
    }

    public Integer getSignalScore() {
        return signalScore;
    }

    public void setSignalScore(Integer signalScore) {
        this.signalScore = signalScore;
    }

    public String getRecommendAction() {
        return recommendAction;
    }

    public void setRecommendAction(String recommendAction) {
        this.recommendAction = recommendAction;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public BigDecimal getStopLossPrice() {
        return stopLossPrice;
    }

    public void setStopLossPrice(BigDecimal stopLossPrice) {
        this.stopLossPrice = stopLossPrice;
    }

    public BigDecimal getTakeProfitPrice() {
        return takeProfitPrice;
    }

    public void setTakeProfitPrice(BigDecimal takeProfitPrice) {
        this.takeProfitPrice = takeProfitPrice;
    }

    public Integer getSignalReliability() {
        return signalReliability;
    }

    public void setSignalReliability(Integer signalReliability) {
        this.signalReliability = signalReliability;
    }

    public String getFalseSignalRisk() {
        return falseSignalRisk;
    }

    public void setFalseSignalRisk(String falseSignalRisk) {
        this.falseSignalRisk = falseSignalRisk;
    }

    public Date getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(Date analysisTime) {
        this.analysisTime = analysisTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("signalId", getSignalId())
                .append("symbol", getSymbol())
                .append("interval", getInterval())
                .append("currentPrice", getCurrentPrice())
                .append("support1", getSupport1())
                .append("resistance1", getResistance1())
                .append("recommendAction", getRecommendAction())
                .append("recommendReason", getRecommendReason())
                .append("signalScore", getSignalScore())
                .append("analysisTime", getAnalysisTime())
                .toString();
    }
}
