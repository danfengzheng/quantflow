package com.quantflow.trading.strategy.service;

import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.impl.AccountServiceImpl;
import com.quantflow.trading.common.exchange.ExchangeFactory;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.engine.IStrategy;
import com.quantflow.trading.strategy.engine.StrategyFactory;
import com.quantflow.trading.strategy.mapper.StrategySignalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 策略执行服务
 */
@Slf4j
@Service
public class StrategyExecuteService {

    @Autowired
    private StrategyFactory strategyFactory;

    @Autowired
    private ExchangeFactory exchangeFactory;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private StrategySignalMapper strategySignalMapper;

    /**
     * 执行单个策略
     */
    public void executeStrategy(Strategy strategy) {
        log.info("开始执行策略：id={}, name={}", strategy.getId(), strategy.getName());

        IExchangeAdapter adapter = null;
        try {
            // 1. 获取账户信息
            Account account = accountService.getDecryptedAccount(strategy.getAccountId());
            if (account == null) {
                log.error("账户不存在：accountId={}", strategy.getAccountId());
                return;
            }

            // 2. 创建交易所适配器
            adapter = exchangeFactory.createAdapter(account);

            // 3. 获取K线数据
            List<KLine> klines = adapter.getKlines(
                    strategy.getSymbol(),
                    strategy.getInterval(),
                    100  // 获取100根K线
            );

            if (klines == null || klines.isEmpty()) {
                log.warn("未获取到K线数据：symbol={}", strategy.getSymbol());
                return;
            }

            log.debug("获取到{}根K线数据", klines.size());

            // 4. 创建策略实例
            IStrategy strategyInstance = strategyFactory.createStrategy(strategy);

            // 5. 计算策略信号
            StrategySignal signal = strategyInstance.calculate(klines);

            // 6. 保存信号
            if (signal != null) {
                strategySignalMapper.insertStrategySignal(signal);
                log.info("生成交易信号：type={}, price={}, reason={}",
                        signal.getSignalType(), signal.getPrice(), signal.getReason());
            } else {
                log.debug("未生成交易信号");
            }

        } catch (Exception e) {
            log.error("执行策略失败：strategyId={}", strategy.getId(), e);
        } finally {
            // 不需要关闭adapter，因为每次都是新创建的
        }
    }
}