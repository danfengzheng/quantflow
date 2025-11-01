package com.quantflow.trading.market.domain;

import com.quantflow.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 实时行情
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MarketTicker extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 交易对 */
    private String symbol;

    /** 最新价 */
    private BigDecimal last;

    /** 买一价 */
    private BigDecimal bid;

    /** 卖一价 */
    private BigDecimal ask;

    /** 24h最高价 */
    private BigDecimal high;

    /** 24h最低价 */
    private BigDecimal low;

    /** 24h成交量 */
    private BigDecimal volume;

    /** 24h成交额 */
    private BigDecimal quoteVolume;

    /** 24h涨跌幅 */
    private BigDecimal priceChangePercent;

    /** 时间戳 */
    private Long timestamp;
}