package com.quantflow.trading.backtest.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;

/**
 * 回测对象 qf_backtest
 * 
 * @author quantflow
 * @date 2025-11-03
 */
@Data
public class Backtest extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 任务ID */
    private Long id;

    /** 任务名称 */
    @Excel(name = "任务名称")
    private String name;

    /** 策略ID */
    @Excel(name = "策略ID")
    private Long strategyId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** K线周期 */
    @Excel(name = "K线周期")
    private String interval;

    /** 回测开始日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回测开始日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;

    /** 回测结束日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "回测结束日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;

    /** 初始资金 */
    @Excel(name = "初始资金")
    private BigDecimal initialCapital;

    /** 手续费率 */
    @Excel(name = "手续费率")
    private BigDecimal commissionRate;

    /** 滑点率 */
    @Excel(name = "滑点率")
    private BigDecimal slippageRate;

    /** 状态：PENDING/RUNNING/COMPLETED/FAILED */
    @Excel(name = "状态：PENDING/RUNNING/COMPLETED/FAILED")
    private String status;

    /** 回测结果（JSON） */
    @Excel(name = "回测结果", readConverterExp = "J=SON")
    private String result;

    /** 总收益率 */
    @Excel(name = "总收益率")
    private BigDecimal totalReturn;

    /** 年化收益率 */
    @Excel(name = "年化收益率")
    private BigDecimal annualReturn;

    /** 最大回撤 */
    @Excel(name = "最大回撤")
    private BigDecimal maxDrawdown;

    /** 夏普比率 */
    @Excel(name = "夏普比率")
    private BigDecimal sharpeRatio;

    /** 胜率 */
    @Excel(name = "胜率")
    private BigDecimal winRate;

    /** 交易次数 */
    @Excel(name = "交易次数")
    private Integer tradeCount;

    /** 错误信息 */
    @Excel(name = "错误信息")
    private String errorMsg;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completeTime;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("strategyId", getStrategyId())
            .append("symbol", getSymbol())
            .append("interval", getInterval())
            .append("startDate", getStartDate())
            .append("endDate", getEndDate())
            .append("initialCapital", getInitialCapital())
            .append("commissionRate", getCommissionRate())
            .append("slippageRate", getSlippageRate())
            .append("status", getStatus())
            .append("result", getResult())
            .append("totalReturn", getTotalReturn())
            .append("annualReturn", getAnnualReturn())
            .append("maxDrawdown", getMaxDrawdown())
            .append("sharpeRatio", getSharpeRatio())
            .append("winRate", getWinRate())
            .append("tradeCount", getTradeCount())
            .append("errorMsg", getErrorMsg())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("completeTime", getCompleteTime())
            .toString();
    }
}
