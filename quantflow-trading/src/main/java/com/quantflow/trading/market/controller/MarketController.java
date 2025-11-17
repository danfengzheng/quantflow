package com.quantflow.trading.market.controller;

import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.constant.TradingMessageKeys;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.market.domain.MarketTicker;
import com.quantflow.trading.market.service.MarketDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 行情数据Controller
 */
@Slf4j
@RestController
@RequestMapping("/trading/market")
public class MarketController extends BaseController {

    @Autowired
    private MarketDataService marketDataService;

    /**
     * 获取实时行情
     */
    @PreAuthorize("@ss.hasPermi('trading:market:query')")
    @GetMapping("/ticker")
    public AjaxResult getTicker(@RequestParam Long accountId, @RequestParam String symbol) {
        try {
            MarketTicker ticker = marketDataService.getTicker(accountId, symbol);
            return AjaxResult.success(ticker);
        } catch (Exception e) {
            log.error("获取行情失败", e);
            return AjaxResult.error(TradingMessageKeys.MARKET_GET_TICKER_FAILED, e.getMessage());
        }
    }

    /**
     * 批量获取行情
     */
    @PreAuthorize("@ss.hasPermi('trading:market:query')")
    @GetMapping("/tickers")
    public AjaxResult getMultipleTickers(@RequestParam Long accountId, @RequestParam String symbols) {
        try {
            List<String> symbolList = Arrays.asList(symbols.split(","));
            Map<String, MarketTicker> tickers = marketDataService.getMultipleTickers(accountId, symbolList);
            return AjaxResult.success(tickers);
        } catch (Exception e) {
            log.error("批量获取行情失败", e);
            return AjaxResult.error(TradingMessageKeys.MARKET_GET_TICKERS_FAILED, e.getMessage());
        }
    }

    /**
     * 获取K线数据
     */
    @PreAuthorize("@ss.hasPermi('trading:market:query')")
    @GetMapping("/klines")
    public AjaxResult getKlines(
            @RequestParam Long accountId,
            @RequestParam String symbol,
            @RequestParam(defaultValue = "1h") String interval,
            @RequestParam(defaultValue = "100") Integer limit) {
        try {
            List<KLine> klines = marketDataService.getKlines(accountId, symbol, interval, limit);
            return AjaxResult.success(klines);
        } catch (Exception e) {
            log.error("获取K线数据失败", e);
            return AjaxResult.error(TradingMessageKeys.MARKET_GET_KLINES_FAILED, e.getMessage());
        }
    }
}