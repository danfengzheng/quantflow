package com.quantflow.trading.account.service;

import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.impl.AccountServiceImpl;
import com.quantflow.trading.common.dto.Balance;
import com.quantflow.trading.common.exchange.ExchangeFactory;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 账户测试服务
 */
@Slf4j
@Service
public class AccountTestService {

    @Autowired
    private AccountServiceImpl accountService;
    @Autowired
    private ExchangeFactory exchangeFactory;

    /**
     * 测试交易所连接
     */
    public Map<String, Object> testConnection(Long accountId) {
        Map<String, Object> result = new HashMap<>();
        IExchangeAdapter adapter = null;

        try {
            // 获取解密后的账户信息
            Account account = accountService.getDecryptedAccount(accountId);
            if (account == null) {
                result.put("success", false);
                result.put("message", "账户不存在");
                return result;
            }

            // 创建交易所适配器
            adapter = exchangeFactory.createAdapter(account);

            // 测试连接
            boolean connected = adapter.testConnection();

            if (connected) {
                // 获取账户余额
                Map<String, BigDecimal> balances = adapter.getBalance();

                result.put("success", true);
                result.put("message", "连接成功");
                result.put("exchange", account.getExchange());
                result.put("testnet", account.getIsTestnet() == 1);
                result.put("balanceCount", balances);

                // 返回主要币种余额
                Map<String, String> mainBalances = new HashMap<>();
//                balances.forEach((currency, balance) -> {
//                    if (balance.getTotal().doubleValue() > 0) {
//                        mainBalances.put(currency, balance.getTotal().toPlainString());
//                    }
//                });
                result.put("balances", mainBalances);

                log.info("账户连接测试成功：accountId={}, exchange={}", accountId, account.getExchange());
            }

        } catch (Exception e) {
            log.error("账户连接测试失败：accountId={}", accountId, e);
            result.put("success", false);
            result.put("message", "连接失败：" + e.getMessage());
        }

        return result;
    }

    /**
     * 获取账户余额
     */
    public Map<String, Object> getBalance(Long accountId) {
        Map<String, Object> result = new HashMap<>();
        IExchangeAdapter adapter = null;

        try {
            Account account = accountService.getDecryptedAccount(accountId);
            if (account == null) {
                result.put("success", false);
                result.put("message", "账户不存在");
                return result;
            }

            adapter = exchangeFactory.createAdapter(account);
            Map<String, BigDecimal> balances = adapter.getBalance();

            result.put("success", true);
            result.put("balances", balances);

        } catch (Exception e) {
            log.error("获取账户余额失败：accountId={}", accountId, e);
            result.put("success", false);
            result.put("message", e.getMessage());
        }

        return result;
    }
}