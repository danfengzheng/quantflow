package com.quantflow.trading.common.exchange;

import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.market.domain.MarketTicker;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 交易所适配器接口
 */
public interface IExchangeAdapter {

    /**
     * 测试连接
     */
    boolean testConnection() throws IOException;

    /**
     * 获取账户余额
     */
    Map<String, BigDecimal> getBalance() throws IOException;

    /**
     * 获取最新行情
     */
    MarketTicker getTicker(String symbol) throws IOException;

    /**
     * 获取K线数据
     */
    List<KLine> getKlines(String symbol, String interval, Integer limit) throws IOException;

    /**
     * 市价买入
     */
    String marketBuy(String symbol, BigDecimal quantity) throws IOException;

    /**
     * 市价卖出
     */
    String marketSell(String symbol, BigDecimal quantity) throws IOException;

    /**
     * 限价买入
     */
    String limitBuy(String symbol, BigDecimal price, BigDecimal quantity) throws IOException;

    /**
     * 限价卖出
     */
    String limitSell(String symbol, BigDecimal price, BigDecimal quantity) throws IOException;

    /**
     * 取消订单
     */
    boolean cancelOrder(String symbol, String orderId) throws IOException;

    /**
     * 查询订单
     */
    Map<String, Object> getOrder(String symbol, String orderId) throws IOException;

    /**
     * 获取交易所名称
     */
    String getExchangeName();
}