package com.quantflow.trading.common.exchange;

import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.common.exchange.binance.BinanceApiClient;
import com.quantflow.trading.common.exchange.impl.BinanceAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 交易所工厂类
 */
@Slf4j
@Component
public class ExchangeFactory {

    @Autowired
    private BinanceApiClient binanceApiClient;

    /**
     * 创建交易所适配器
     */
    public IExchangeAdapter createAdapter(Account account) {
        String exchange = account.getExchange().toLowerCase();
        boolean isTestnet = account.getIsTestnet() == 1;

        switch (exchange) {
            case "binance":
                log.info("创建币安交易所适配器：{}", isTestnet ? "测试网" : "主网");
                return new BinanceAdapter(
                        binanceApiClient,
                        account.getApiKey(),
                        account.getApiSecret(),
                        isTestnet
                );

            case "okx":
                // TODO: 实现OKX适配器
                throw new UnsupportedOperationException("OKX 适配器暂未实现");

            default:
                throw new IllegalArgumentException("不支持的交易所：" + exchange);
        }
    }
}