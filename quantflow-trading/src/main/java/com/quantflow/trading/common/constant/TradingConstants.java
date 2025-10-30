package com.quantflow.trading.common.constant;

/**
 * 交易系统常量
 */
public class TradingConstants {

    /**
     * 交易所类型
     */
    public static final String EXCHANGE_BINANCE = "binance";
    public static final String EXCHANGE_OKX = "okx";
    public static final String EXCHANGE_HUOBI = "huobi";

    /**
     * 订单方向
     */
    public static final String ORDER_SIDE_BUY = "BUY";
    public static final String ORDER_SIDE_SELL = "SELL";

    /**
     * 订单类型
     */
    public static final String ORDER_TYPE_MARKET = "MARKET";
    public static final String ORDER_TYPE_LIMIT = "LIMIT";

    /**
     * K线周期
     */
    public static final String INTERVAL_1M = "1m";
    public static final String INTERVAL_5M = "5m";
    public static final String INTERVAL_15M = "15m";
    public static final String INTERVAL_1H = "1h";
    public static final String INTERVAL_4H = "4h";
    public static final String INTERVAL_1D = "1d";

    /**
     * Redis Key 前缀
     */
    public static final String REDIS_KEY_MARKET_DATA = "qf:market:";
    public static final String REDIS_KEY_STRATEGY = "qf:strategy:";
    public static final String REDIS_KEY_ORDER = "qf:order:";
    public static final String REDIS_KEY_POSITION = "qf:position:";
}