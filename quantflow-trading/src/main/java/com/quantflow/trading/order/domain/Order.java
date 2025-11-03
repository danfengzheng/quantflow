package com.quantflow.trading.order.domain;

import java.math.BigDecimal;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;

/**
 * 订单对象 qf_order
 * 
 * @author quantflow
 * @date 2025-11-03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Order extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 订单ID */
    @Excel(name = "订单ID")
    private Long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderNo;

    /** 策略ID */
    @Excel(name = "策略ID")
    private Long strategyId;

    /** 账户ID */
    @Excel(name = "账户ID")
    private Long accountId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** 方向 */
    @Excel(name = "方向")
    private String side;

    /** 类型 */
    @Excel(name = "类型")
    private String type;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 数量 */
    @Excel(name = "数量")
    private BigDecimal quantity;

    /** 成交数量 */
    @Excel(name = "成交数量")
    private BigDecimal filledQty;

    /** 成交均价 */
    @Excel(name = "成交均价")
    private BigDecimal avgPrice;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal amount;

    /** 手续费 */
    @Excel(name = "手续费")
    private BigDecimal commission;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 交易所订单ID */
    @Excel(name = "交易所订单ID")
    private String exchangeOrderId;

    /** 错误信息 */
    private String errorMsg;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderNo", getOrderNo())
            .append("strategyId", getStrategyId())
            .append("accountId", getAccountId())
            .append("symbol", getSymbol())
            .append("side", getSide())
            .append("type", getType())
            .append("price", getPrice())
            .append("quantity", getQuantity())
            .append("filledQty", getFilledQty())
            .append("avgPrice", getAvgPrice())
            .append("amount", getAmount())
            .append("commission", getCommission())
            .append("status", getStatus())
            .append("exchangeOrderId", getExchangeOrderId())
            .append("errorMsg", getErrorMsg())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
