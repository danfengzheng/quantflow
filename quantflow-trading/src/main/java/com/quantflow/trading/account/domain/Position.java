package com.quantflow.trading.account.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 持仓对象 qf_position
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Position extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 持仓ID */
    private Long id;

    /** 账户ID */
    @Excel(name = "账户ID")
    private Long accountId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 持仓数量 */
    @Excel(name = "持仓数量")
    private BigDecimal quantity;

    /** 可用数量 */
    @Excel(name = "可用数量")
    private BigDecimal availableQty;

    /** 冻结数量 */
    @Excel(name = "冻结数量")
    private BigDecimal frozenQty;

    /** 平均成本价 */
    @Excel(name = "平均成本价")
    private BigDecimal avgPrice;

    /** 当前价格 */
    @Excel(name = "当前价格")
    private BigDecimal currentPrice;

    /** 市值 */
    @Excel(name = "市值")
    private BigDecimal marketValue;

    /** 浮动盈亏 */
    @Excel(name = "浮动盈亏")
    private BigDecimal unrealizedPnl;

    /** 浮动盈亏率 */
    @Excel(name = "浮动盈亏率")
    private BigDecimal unrealizedPnlRatio;

}