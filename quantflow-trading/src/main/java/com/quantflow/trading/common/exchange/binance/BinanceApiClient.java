package com.quantflow.trading.common.exchange.binance;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.quantflow.trading.common.http.HttpClient;
import com.quantflow.trading.common.utils.SignatureUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 币安 API 客户端
 */
@Slf4j
@Component
public class BinanceApiClient {

    private final HttpClient httpClient;

    // API 端点
    private static final String BASE_URL = "https://api.binance.com";
    private static final String TESTNET_URL = "https://testnet.binance.vision";

    public BinanceApiClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    /**
     * 获取基础URL
     */
    private String getBaseUrl(boolean isTestnet) {
        return isTestnet ? TESTNET_URL : BASE_URL;
    }

    /**
     * 测试连接
     */
    public boolean ping(boolean isTestnet) throws IOException {
        String url = getBaseUrl(isTestnet) + "/api/v3/ping";
        String response = httpClient.get(url, null);
        return "{}".equals(response);
    }

    /**
     * 获取服务器时间
     */
    public long getServerTime(boolean isTestnet) throws IOException {
        String url = getBaseUrl(isTestnet) + "/api/v3/time";
        String response = httpClient.get(url, null);
        JSONObject json = JSON.parseObject(response);
        return json.getLongValue("serverTime");
    }

    /**
     * 获取账户信息（需要签名）
     */
    public JSONObject getAccountInfo(String apiKey, String apiSecret, boolean isTestnet) throws IOException {
        String url = getBaseUrl(isTestnet) + "/api/v3/account";

        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", System.currentTimeMillis());

        String queryString = SignatureUtil.buildQueryString(params);
        String signature = SignatureUtil.hmacSha256(queryString, apiSecret);

        String finalUrl = url + "?" + queryString + "&signature=" + signature;

        Map<String, String> headers = new HashMap<>();
        headers.put("X-MBX-APIKEY", apiKey);

        String response = httpClient.get(finalUrl, headers);
        return JSON.parseObject(response);
    }

    /**
     * 获取最新价格
     */
    public JSONObject getTickerPrice(String symbol, boolean isTestnet) throws IOException {
        String url = getBaseUrl(isTestnet) + "/api/v3/ticker/price";
        if (symbol != null) {
            url += "?symbol=" + symbol;
        }
        String response = httpClient.get(url, null);
        return JSON.parseObject(response);
    }

    /**
     * 获取24小时价格变动
     */
    public JSONObject getTicker24h(String symbol, boolean isTestnet) throws IOException {
        String url = getBaseUrl(isTestnet) + "/api/v3/ticker/24hr?symbol=" + symbol;
        String response = httpClient.get(url, null);
        return JSON.parseObject(response);
    }

    /**
     * 获取K线数据
     */
    public JSONArray getKlines(String symbol, String interval, Integer limit, boolean isTestnet) throws IOException {
        StringBuilder url = new StringBuilder(getBaseUrl(isTestnet) + "/api/v3/klines");
        url.append("?symbol=").append(symbol);
        url.append("&interval=").append(interval);
        if (limit != null) {
            url.append("&limit=").append(limit);
        }

        String response = httpClient.get(url.toString(), null);
        return JSON.parseArray(response);
    }

    /**
     * 获取订单簿
     */
    public JSONObject getOrderBook(String symbol, Integer limit, boolean isTestnet) throws IOException {
        StringBuilder url = new StringBuilder(getBaseUrl(isTestnet) + "/api/v3/depth");
        url.append("?symbol=").append(symbol);
        if (limit != null) {
            url.append("&limit=").append(limit);
        }

        String response = httpClient.get(url.toString(), null);
        return JSON.parseObject(response);
    }

    /**
     * 市价下单
     */
    public JSONObject createMarketOrder(
            String apiKey, String apiSecret,
            String symbol, String side, String quantity,
            boolean isTestnet) throws IOException {

        String url = getBaseUrl(isTestnet) + "/api/v3/order";

        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("side", side); // BUY or SELL
        params.put("type", "MARKET");
        params.put("quantity", quantity);
        params.put("timestamp", System.currentTimeMillis());

        String queryString = SignatureUtil.buildQueryString(params);
        String signature = SignatureUtil.hmacSha256(queryString, apiSecret);

        String finalUrl = url + "?" + queryString + "&signature=" + signature;

        Map<String, String> headers = new HashMap<>();
        headers.put("X-MBX-APIKEY", apiKey);

        String response = httpClient.post(finalUrl, headers, "");
        return JSON.parseObject(response);
    }

    /**
     * 限价下单
     */
    public JSONObject createLimitOrder(
            String apiKey, String apiSecret,
            String symbol, String side, String price, String quantity,
            boolean isTestnet) throws IOException {

        String url = getBaseUrl(isTestnet) + "/api/v3/order";

        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("side", side);
        params.put("type", "LIMIT");
        params.put("timeInForce", "GTC");
        params.put("price", price);
        params.put("quantity", quantity);
        params.put("timestamp", System.currentTimeMillis());

        String queryString = SignatureUtil.buildQueryString(params);
        String signature = SignatureUtil.hmacSha256(queryString, apiSecret);

        String finalUrl = url + "?" + queryString + "&signature=" + signature;

        Map<String, String> headers = new HashMap<>();
        headers.put("X-MBX-APIKEY", apiKey);

        String response = httpClient.post(finalUrl, headers, "");
        return JSON.parseObject(response);
    }

    /**
     * 取消订单
     */
    public JSONObject cancelOrder(
            String apiKey, String apiSecret,
            String symbol, Long orderId,
            boolean isTestnet) throws IOException {

        String url = getBaseUrl(isTestnet) + "/api/v3/order";

        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("orderId", orderId);
        params.put("timestamp", System.currentTimeMillis());

        String queryString = SignatureUtil.buildQueryString(params);
        String signature = SignatureUtil.hmacSha256(queryString, apiSecret);

        String finalUrl = url + "?" + queryString + "&signature=" + signature;

        Map<String, String> headers = new HashMap<>();
        headers.put("X-MBX-APIKEY", apiKey);

        String response = httpClient.delete(finalUrl, headers);
        return JSON.parseObject(response);
    }

    /**
     * 查询订单
     */
    public JSONObject getOrder(
            String apiKey, String apiSecret,
            String symbol, Long orderId,
            boolean isTestnet) throws IOException {

        String url = getBaseUrl(isTestnet) + "/api/v3/order";

        Map<String, Object> params = new HashMap<>();
        params.put("symbol", symbol);
        params.put("orderId", orderId);
        params.put("timestamp", System.currentTimeMillis());

        String queryString = SignatureUtil.buildQueryString(params);
        String signature = SignatureUtil.hmacSha256(queryString, apiSecret);

        String finalUrl = url + "?" + queryString + "&signature=" + signature;

        Map<String, String> headers = new HashMap<>();
        headers.put("X-MBX-APIKEY", apiKey);

        String response = httpClient.get(finalUrl, headers);
        return JSON.parseObject(response);
    }
}