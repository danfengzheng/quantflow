package com.quantflow.trading.backtest.service;

import com.alibaba.fastjson2.JSON;
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.impl.AccountServiceImpl;
import com.quantflow.trading.backtest.domain.Backtest;
import com.quantflow.trading.backtest.engine.BacktestEngine;
import com.quantflow.trading.backtest.mapper.BacktestMapper;
import com.quantflow.trading.backtest.vo.BacktestResultVO;
import com.quantflow.trading.common.exchange.ExchangeFactory;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import com.quantflow.trading.market.domain.KLine;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.mapper.StrategyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 回测执行服务
 */
@Slf4j
@Service
public class BacktestExecuteService {

    @Autowired
    private BacktestMapper backtestMapper;

    @Autowired
    private StrategyMapper strategyMapper;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private ExchangeFactory exchangeFactory;

    @Autowired
    private BacktestEngine backtestEngine;

    /**
     * 异步执行回测
     */
    @Async("strategyExecutor")
    public void executeBacktest(Long backtestId) {
        log.info("开始执行回测：backtestId={}", backtestId);

        Backtest backtest = backtestMapper.selectBacktestById(backtestId);
        if (backtest == null) {
            log.error("回测任务不存在：backtestId={}", backtestId);
            return;
        }

        try {
            // 更新状态为运行中
            backtest.setStatus("RUNNING");
            backtestMapper.updateBacktest(backtest);

            // 获取策略
            Strategy strategy = strategyMapper.selectStrategyById(backtest.getStrategyId());
            if (strategy == null) {
                throw new RuntimeException("策略不存在");
            }

            // 验证回测参数
            validateBacktestParams(backtest, strategy);

            // 获取历史K线数据
            List<KLine> klines = getHistoricalKlines(strategy, backtest);

            if (klines.isEmpty()) {
                throw new RuntimeException("未获取到历史K线数据，请检查日期范围或交易对");
            }

            log.info("获取到 {} 根K线数据，开始执行回测", klines.size());

            // 执行回测
            BigDecimal commissionRate = backtest.getCommissionRate() != null ?
                    backtest.getCommissionRate() :
                    new BigDecimal("0.001");

            BigDecimal slippageRate = backtest.getSlippageRate() != null ?
                    backtest.getSlippageRate() :
                    BigDecimal.ZERO;

            BacktestResultVO result = backtestEngine.runBacktest(
                    strategy,
                    klines,
                    backtest.getInitialCapital(),
                    commissionRate
            );

            // 保存回测结果
            backtest.setResult(JSON.toJSONString(result));
            backtest.setTotalReturn(result.getReturnMetrics().getTotalReturn());
            backtest.setAnnualReturn(result.getReturnMetrics().getAnnualReturn());
            backtest.setMaxDrawdown(result.getRiskMetrics().getMaxDrawdown());
            backtest.setSharpeRatio(result.getRiskMetrics().getSharpeRatio());
            backtest.setWinRate(result.getTradeStats().getWinRate());
            backtest.setTradeCount(result.getTradeStats().getTotalTrades());
            backtest.setStatus("COMPLETED");
            backtest.setCompleteTime(new Date());

            backtestMapper.updateBacktest(backtest);

            log.info("回测完成：backtestId={}, totalReturn={}%, winRate={}%, trades={}",
                    backtestId,
                    result.getReturnMetrics().getTotalReturn(),
                    result.getTradeStats().getWinRate(),
                    result.getTradeStats().getTotalTrades());

        } catch (Exception e) {
            log.error("回测执行失败：backtestId={}", backtestId, e);
            backtest.setStatus("FAILED");
            backtest.setErrorMsg(e.getMessage());
            backtest.setCompleteTime(new Date());
            backtestMapper.updateBacktest(backtest);
        }
    }

    /**
     * 验证回测参数
     */
    private void validateBacktestParams(Backtest backtest, Strategy strategy) {
        if (backtest.getSymbol() == null || backtest.getSymbol().isEmpty()) {
            backtest.setSymbol(strategy.getSymbol());
        }

        if (backtest.getInterval() == null || backtest.getInterval().isEmpty()) {
            backtest.setInterval(strategy.getInterval());
        }

        if (backtest.getStartDate() == null) {
            throw new RuntimeException("回测开始日期不能为空");
        }

        if (backtest.getEndDate() == null) {
            throw new RuntimeException("回测结束日期不能为空");
        }

        if (backtest.getStartDate().after(backtest.getEndDate())) {
            throw new RuntimeException("开始日期不能晚于结束日期");
        }

        if (backtest.getInitialCapital() == null ||
                backtest.getInitialCapital().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("初始资金必须大于0");
        }
    }

    /**
     * 获取历史K线数据
     */
    private List<KLine> getHistoricalKlines(Strategy strategy, Backtest backtest) throws Exception {
        // 获取账户
        Account account = accountService.getDecryptedAccount(strategy.getAccountId());
        if (account == null) {
            throw new RuntimeException("账户不存在");
        }

        // 创建交易所适配器
        IExchangeAdapter adapter = exchangeFactory.createAdapter(account);

        // 计算需要获取的K线数量
        // 根据周期和日期范围计算
        long startTime = backtest.getStartDate().getTime();
        long endTime = backtest.getEndDate().getTime();
        long diffDays = (endTime - startTime) / (24 * 60 * 60 * 1000);

        // 根据K线周期估算需要的K线数量
        int limit = calculateKlineLimit(backtest.getInterval(), diffDays);

        log.info("获取历史K线：symbol={}, interval={}, limit={}, days={}",
                backtest.getSymbol(), backtest.getInterval(), limit, diffDays);

        // 获取K线数据（这里简化处理，实际应该支持分页获取大量历史数据）
        List<KLine> klines = adapter.getKlines(backtest.getSymbol(), backtest.getInterval(), limit);

        // 过滤日期范围内的K线
        return klines.stream()
                .filter(k -> {
                    Date klineDate = new Date(k.getOpenTime());
                    return !klineDate.before(backtest.getStartDate()) &&
                            !klineDate.after(backtest.getEndDate());
                })
                .collect(java.util.stream.Collectors.toList());
    }

    /**
     * 计算需要获取的K线数量
     */
    private int calculateKlineLimit(String interval, long days) {
        // 根据不同的K线周期计算
        switch (interval) {
            case "1m":
                return (int) Math.min(days * 1440, 1000); // 每天1440根1分钟K线
            case "5m":
                return (int) Math.min(days * 288, 1000);  // 每天288根5分钟K线
            case "15m":
                return (int) Math.min(days * 96, 1000);   // 每天96根15分钟K线
            case "30m":
                return (int) Math.min(days * 48, 1000);   // 每天48根30分钟K线
            case "1h":
                return (int) Math.min(days * 24, 1000);   // 每天24根1小时K线
            case "4h":
                return (int) Math.min(days * 6, 1000);    // 每天6根4小时K线
            case "1d":
                return (int) Math.min(days, 1000);        // 每天1根日K线
            default:
                return 1000; // 默认1000根
        }
    }
}