package com.quantflow.trading.market.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.impl.AccountServiceImpl;
import com.quantflow.trading.common.exchange.ExchangeFactory;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.market.domain.MarketTicker;
import com.quantflow.trading.market.domain.Ticker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 行情数据服务
 */
@Slf4j
@Service
public class MarketDataService {

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    @Qualifier("marketDataCache")
    private Cache<String, Object> marketDataCache;

    @Autowired
    private ExchangeFactory exchangeFactory;

    /**
     * 获取实时行情
     */
    public MarketTicker getTicker(Long accountId, String symbol) {
        // 先从缓存获取
        String cacheKey = "ticker:" + accountId + ":" + symbol;
        MarketTicker cached = (MarketTicker) marketDataCache.getIfPresent(cacheKey);
        if (cached != null) {
            return cached;
        }

        // 缓存未命中，从交易所获取
        IExchangeAdapter adapter = null;
        try {
            Account account = accountService.getDecryptedAccount(accountId);
            adapter = exchangeFactory.createAdapter(account);

            MarketTicker marketTicker = adapter.getTicker(symbol);

//            MarketTicker marketTicker = convertTicker(ticker, symbol);

            // 放入缓存
            marketDataCache.put(cacheKey, marketTicker);

            return marketTicker;

        } catch (Exception e) {
            log.error("获取行情失败：symbol={}", symbol, e);
            throw new RuntimeException("获取行情失败：" + e.getMessage());
        }
    }

    /**
     * 获取K线数据
     */
    public List<KLine> getKlines(Long accountId, String symbol, String interval, Integer limit) {
        IExchangeAdapter adapter = null;
        try {
            Account account = accountService.getDecryptedAccount(accountId);
            adapter = exchangeFactory.createAdapter(account);

            return adapter.getKlines(symbol, interval, limit);

        } catch (Exception e) {
            log.error("获取K线数据失败：symbol={}, interval={}", symbol, interval, e);
            throw new RuntimeException("获取K线数据失败：" + e.getMessage());
        }
    }

    /**
     * 批量获取行情
     */
    public Map<String, MarketTicker> getMultipleTickers(Long accountId, List<String> symbols) {
        Map<String, MarketTicker> result = new HashMap<>();
        for (String symbol : symbols) {
            try {
                MarketTicker ticker = getTicker(accountId, symbol);
                result.put(symbol, ticker);
            } catch (Exception e) {
                log.warn("获取行情失败，跳过：symbol={}", symbol);
            }
        }
        return result;
    }

    /**
     * 转换Ticker对象
     */
    private MarketTicker convertTicker(Ticker ticker, String symbol) {
        MarketTicker marketTicker = new MarketTicker();
        marketTicker.setSymbol(symbol);
        marketTicker.setLast(ticker.getLast());
        marketTicker.setBid(ticker.getBid());
        marketTicker.setAsk(ticker.getAsk());
        marketTicker.setHigh(ticker.getHigh());
        marketTicker.setLow(ticker.getLow());
        marketTicker.setVolume(ticker.getVolume());
        marketTicker.setQuoteVolume(ticker.getQuoteVolume());

        // 计算涨跌幅
        if (ticker.getOpen() != null && ticker.getLast() != null) {
            BigDecimal change = ticker.getLast().subtract(ticker.getOpen());
            BigDecimal percent = change.divide(ticker.getOpen(), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            marketTicker.setPriceChangePercent(percent);
        }

        marketTicker.setTimestamp(ticker.getTimestamp().getTime());

        return marketTicker;
    }

    /**
     * 转换K线数据
     */
    private List<KLine> convertKlines(List<Object[]> rawKlines, String symbol, String interval) {
        List<KLine> klines = new ArrayList<>();

        for (Object[] raw : rawKlines) {
            // 币安K线数据格式：
            // [0]openTime, [1]open, [2]high, [3]low, [4]close, [5]volume,
            // [6]closeTime, [7]quoteVolume, [8]trades, ...

            KLine kline = KLine.builder()
                    .symbol(symbol)
                    .interval(interval)
                    .openTime((Long) raw[0])
                    .open(new BigDecimal(raw[1].toString()))
                    .high(new BigDecimal(raw[2].toString()))
                    .low(new BigDecimal(raw[3].toString()))
                    .close(new BigDecimal(raw[4].toString()))
                    .volume(new BigDecimal(raw[5].toString()))
                    .closeTime((Long) raw[6])
                    .quoteVolume(new BigDecimal(raw[7].toString()))
                    .trades(((Number) raw[8]).intValue())
                    .build();

            klines.add(kline);
        }

        return klines;
    }
}