package com.quantflow.trading.strategy.domain;

import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 策略信号对象 qf_signal
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class StrategySignal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 信号ID */
    private Long id;

    /** 策略ID */
    @Excel(name = "策略ID")
    private Long strategyId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 信号类型：BUY/SELL */
    @Excel(name = "信号类型")
    private String signalType;

    /** 信号价格 */
    @Excel(name = "信号价格")
    private BigDecimal price;

    /** 建议数量 */
    @Excel(name = "建议数量")
    private BigDecimal quantity;

    /** 信号原因 */
    @Excel(name = "信号原因")
    private String reason;

    /** 置信度 */
    @Excel(name = "置信度")
    private BigDecimal confidence;

    /** 状态：0待处理 1已处理 2已忽略 */
    @Excel(name = "状态")
    private Integer status;
}