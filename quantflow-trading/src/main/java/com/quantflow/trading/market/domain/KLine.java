package com.quantflow.trading.market.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * K线数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KLine {

    /** 交易对 */
    private String symbol;

    /** 周期 */
    private String interval;

    /** 开盘时间 */
    private Long openTime;

    /** 开盘价 */
    private BigDecimal open;

    /** 最高价 */
    private BigDecimal high;

    /** 最低价 */
    private BigDecimal low;

    /** 收盘价 */
    private BigDecimal close;

    /** 成交量 */
    private BigDecimal volume;

    /** 收盘时间 */
    private Long closeTime;

    /** 成交额 */
    private BigDecimal quoteVolume;

    /** 成交笔数 */
    private Integer trades;
}