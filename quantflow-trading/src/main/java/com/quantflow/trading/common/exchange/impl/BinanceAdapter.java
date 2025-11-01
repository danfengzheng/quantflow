package com.quantflow.trading.common.exchange.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import com.quantflow.trading.common.exchange.binance.BinanceApiClient;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.market.domain.MarketTicker;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 币安交易所适配器
 */
@Slf4j
public class BinanceAdapter implements IExchangeAdapter {

    private final BinanceApiClient apiClient;
    private final String apiKey;
    private final String apiSecret;
    private final boolean isTestnet;

    public BinanceAdapter(BinanceApiClient apiClient, String apiKey, String apiSecret, boolean isTestnet) {
        this.apiClient = apiClient;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.isTestnet = isTestnet;

        log.info("币安交易所适配器初始化完成，测试网模式：{}", isTestnet);
    }

    @Override
    public boolean testConnection() throws IOException {
        try {
            // 测试 ping
            boolean pingSuccess = apiClient.ping(isTestnet);
            if (!pingSuccess) {
                return false;
            }

            // 测试账户信息
            JSONObject accountInfo = apiClient.getAccountInfo(apiKey, apiSecret, isTestnet);
            log.info("币安连接测试成功，账户类型：{}", accountInfo.getString("accountType"));
            return true;
        } catch (Exception e) {
            log.error("币安连接测试失败", e);
            throw e;
        }
    }

    @Override
    public Map<String, BigDecimal> getBalance() throws IOException {
        JSONObject accountInfo = apiClient.getAccountInfo(apiKey, apiSecret, isTestnet);
        JSONArray balances = accountInfo.getJSONArray("balances");

        Map<String, BigDecimal> result = new HashMap<>();
        for (int i = 0; i < balances.size(); i++) {
            JSONObject balance = balances.getJSONObject(i);
            String asset = balance.getString("asset");
            BigDecimal free = balance.getBigDecimal("free");
            BigDecimal locked = balance.getBigDecimal("locked");
            BigDecimal total = free.add(locked);

            if (total.compareTo(BigDecimal.ZERO) > 0) {
                result.put(asset, total);
            }
        }

        return result;
    }

    @Override
    public MarketTicker getTicker(String symbol) throws IOException {
        JSONObject ticker24h = apiClient.getTicker24h(symbol, isTestnet);

        MarketTicker marketTicker = new MarketTicker();
        marketTicker.setSymbol(symbol);
        marketTicker.setLast(ticker24h.getBigDecimal("lastPrice"));
        marketTicker.setBid(ticker24h.getBigDecimal("bidPrice"));
        marketTicker.setAsk(ticker24h.getBigDecimal("askPrice"));
        marketTicker.setHigh(ticker24h.getBigDecimal("highPrice"));
        marketTicker.setLow(ticker24h.getBigDecimal("lowPrice"));
        marketTicker.setVolume(ticker24h.getBigDecimal("volume"));
        marketTicker.setQuoteVolume(ticker24h.getBigDecimal("quoteVolume"));
        marketTicker.setPriceChangePercent(ticker24h.getBigDecimal("priceChangePercent"));
        marketTicker.setTimestamp(ticker24h.getLong("closeTime"));

        return marketTicker;
    }

    @Override
    public List<KLine> getKlines(String symbol, String interval, Integer limit) throws IOException {
        JSONArray klines = apiClient.getKlines(symbol, interval, limit, isTestnet);

        List<KLine> result = new ArrayList<>();
        for (int i = 0; i < klines.size(); i++) {
            JSONArray kline = klines.getJSONArray(i);

            // 币安K线数据格式：
            // [0]开盘时间, [1]开盘价, [2]最高价, [3]最低价, [4]收盘价, [5]成交量,
            // [6]收盘时间, [7]成交额, [8]成交笔数, ...

            KLine k = KLine.builder()
                    .symbol(symbol)
                    .interval(interval)
                    .openTime(kline.getLong(0))
                    .open(new BigDecimal(kline.getString(1)))
                    .high(new BigDecimal(kline.getString(2)))
                    .low(new BigDecimal(kline.getString(3)))
                    .close(new BigDecimal(kline.getString(4)))
                    .volume(new BigDecimal(kline.getString(5)))
                    .closeTime(kline.getLong(6))
                    .quoteVolume(new BigDecimal(kline.getString(7)))
                    .trades(kline.getInteger(8))
                    .build();

            result.add(k);
        }

        return result;
    }

    @Override
    public String marketBuy(String symbol, BigDecimal quantity) throws IOException {
        JSONObject order = apiClient.createMarketOrder(
                apiKey, apiSecret, symbol, "BUY", quantity.toPlainString(), isTestnet);
        return order.getString("orderId");
    }

    @Override
    public String marketSell(String symbol, BigDecimal quantity) throws IOException {
        JSONObject order = apiClient.createMarketOrder(
                apiKey, apiSecret, symbol, "SELL", quantity.toPlainString(), isTestnet);
        return order.getString("orderId");
    }

    @Override
    public String limitBuy(String symbol, BigDecimal price, BigDecimal quantity) throws IOException {
        JSONObject order = apiClient.createLimitOrder(
                apiKey, apiSecret, symbol, "BUY",
                price.toPlainString(), quantity.toPlainString(), isTestnet);
        return order.getString("orderId");
    }

    @Override
    public String limitSell(String symbol, BigDecimal price, BigDecimal quantity) throws IOException {
        JSONObject order = apiClient.createLimitOrder(
                apiKey, apiSecret, symbol, "SELL",
                price.toPlainString(), quantity.toPlainString(), isTestnet);
        return order.getString("orderId");
    }

    @Override
    public boolean cancelOrder(String symbol, String orderId) throws IOException {
        JSONObject result = apiClient.cancelOrder(
                apiKey, apiSecret, symbol, Long.parseLong(orderId), isTestnet);
        return "CANCELED".equals(result.getString("status"));
    }

    @Override
    public Map<String, Object> getOrder(String symbol, String orderId) throws IOException {
        JSONObject order = apiClient.getOrder(
                apiKey, apiSecret, symbol, Long.parseLong(orderId), isTestnet);

        Map<String, Object> result = new HashMap<>();
        result.put("orderId", order.getString("orderId"));
        result.put("symbol", order.getString("symbol"));
        result.put("status", order.getString("status"));
        result.put("price", order.getString("price"));
        result.put("executedQty", order.getString("executedQty"));
        result.put("side", order.getString("side"));
        result.put("type", order.getString("type"));

        return result;
    }

    @Override
    public String getExchangeName() {
        return isTestnet ? "binance-testnet" : "binance";
    }
}