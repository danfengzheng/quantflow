package com.quantflow.trading.dashboard.service;

import com.quantflow.common.utils.SecurityUtils;
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.domain.Position;
import com.quantflow.trading.account.mapper.AccountMapper;
import com.quantflow.trading.account.mapper.PositionMapper;
import com.quantflow.trading.dashboard.vo.DashboardVO;
import com.quantflow.trading.order.domain.Order;
import com.quantflow.trading.order.mapper.OrderMapper;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.mapper.StrategyMapper;
import com.quantflow.trading.strategy.mapper.StrategySignalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 仪表盘服务
 */
@Slf4j
@Service
public class DashboardService {

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private StrategyMapper strategyMapper;

    @Autowired
    private StrategySignalMapper signalMapper;

    /**
     * 获取仪表盘数据
     */
    public DashboardVO getDashboardData() {
        Long userId = SecurityUtils.getUserId();

        return DashboardVO.builder()
                .accountOverview(getAccountOverview(userId))
                .todayStats(getTodayStats(userId))
                .strategyStats(getStrategyStats(userId))
                .positions(getPositionSummaries(userId))
                .recentOrders(getRecentOrders(userId))
                .recentSignals(getRecentSignals(userId))
                .build();
    }

    /**
     * 获取账户总览
     */
    private DashboardVO.AccountOverview getAccountOverview(Long userId) {
        // 查询用户所有账户
        Account accountQuery = new Account();
        accountQuery.setUserId(userId);
        List<Account> accounts = accountMapper.selectAccountList(accountQuery);

        if (accounts.isEmpty()) {
            return DashboardVO.AccountOverview.builder()
                    .totalAsset(BigDecimal.ZERO)
                    .availableBalance(BigDecimal.ZERO)
                    .positionValue(BigDecimal.ZERO)
                    .totalPnl(BigDecimal.ZERO)
                    .totalPnlRatio(BigDecimal.ZERO)
                    .build();
        }

        // 统计所有账户的持仓
        BigDecimal totalPositionValue = BigDecimal.ZERO;
        BigDecimal totalPnl = BigDecimal.ZERO;

        for (Account account : accounts) {
            Position positionQuery = new Position();
            positionQuery.setAccountId(account.getId());
            List<Position> positions = positionMapper.selectPositionList(positionQuery);

            for (Position position : positions) {
                if (position.getMarketValue() != null) {
                    totalPositionValue = totalPositionValue.add(position.getMarketValue());
                }
                if (position.getUnrealizedPnl() != null) {
                    totalPnl = totalPnl.add(position.getUnrealizedPnl());
                }
            }
        }

        // 简化处理，实际应该从交易所获取真实余额
        BigDecimal availableBalance = new BigDecimal("10000");
        BigDecimal totalAsset = availableBalance.add(totalPositionValue);

        // 计算总盈亏率
        BigDecimal totalPnlRatio = BigDecimal.ZERO;
        if (totalAsset.compareTo(BigDecimal.ZERO) > 0) {
            totalPnlRatio = totalPnl.divide(totalAsset, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        return DashboardVO.AccountOverview.builder()
                .totalAsset(totalAsset)
                .availableBalance(availableBalance)
                .positionValue(totalPositionValue)
                .totalPnl(totalPnl)
                .totalPnlRatio(totalPnlRatio)
                .build();
    }

    /**
     * 获取今日统计
     */
    private DashboardVO.TodayStats getTodayStats(Long userId) {
        // 查询用户所有账户ID
        Account accountQuery = new Account();
        accountQuery.setUserId(userId);
        List<Account> accounts = accountMapper.selectAccountList(accountQuery);
        List<Long> accountIds = accounts.stream()
                .map(Account::getId)
                .collect(Collectors.toList());

        if (accountIds.isEmpty()) {
            return DashboardVO.TodayStats.builder()
                    .tradeCount(0)
                    .dailyPnl(BigDecimal.ZERO)
                    .dailyPnlRatio(BigDecimal.ZERO)
                    .signalCount(0)
                    .runningStrategyCount(0)
                    .build();
        }

        // 查询今日订单数
        // TODO: 添加日期筛选到mapper
        Order orderQuery = new Order();
        List<Order> todayOrders = orderMapper.selectOrderList(orderQuery);
        int tradeCount = (int) todayOrders.stream()
                .filter(o -> isToday(o.getCreateTime()))
                .count();

        // 查询今日信号数
        StrategySignal signalQuery = new StrategySignal();
        List<StrategySignal> signals = signalMapper.selectStrategySignalList(signalQuery);
        int signalCount = (int) signals.stream()
                .filter(s -> isToday(s.getCreateTime()))
                .count();

        // 查询运行中的策略数
        Strategy strategyQuery = new Strategy();
        strategyQuery.setUserId(userId);
        strategyQuery.setStatus(2);
        List<Strategy> runningStrategies = strategyMapper.selectStrategyList(strategyQuery);

        // 简化处理，实际应该计算真实盈亏
        BigDecimal dailyPnl = BigDecimal.ZERO;
        BigDecimal dailyPnlRatio = BigDecimal.ZERO;

        return DashboardVO.TodayStats.builder()
                .tradeCount(tradeCount)
                .dailyPnl(dailyPnl)
                .dailyPnlRatio(dailyPnlRatio)
                .signalCount(signalCount)
                .runningStrategyCount(runningStrategies.size())
                .build();
    }

    /**
     * 获取策略统计
     */
    private DashboardVO.StrategyStats getStrategyStats(Long userId) {
        Strategy query = new Strategy();
        query.setUserId(userId);
        List<Strategy> strategies = strategyMapper.selectStrategyList(query);

        int totalCount = strategies.size();
        long runningCount = strategies.stream().filter(s -> s.getStatus() == 2).count();
        long stoppedCount = strategies.stream().filter(s -> s.getStatus() == 0).count();

        // 策略胜率（简化处理）
        Map<Long, BigDecimal> winRates = new HashMap<>();
        // TODO: 实现真实的胜率计算

        return DashboardVO.StrategyStats.builder()
                .totalCount(totalCount)
                .runningCount((int) runningCount)
                .stoppedCount((int) stoppedCount)
                .strategyWinRates(winRates)
                .build();
    }

    /**
     * 获取持仓摘要
     */
    private List<DashboardVO.PositionSummary> getPositionSummaries(Long userId) {
        Account accountQuery = new Account();
        accountQuery.setUserId(userId);
        List<Account> accounts = accountMapper.selectAccountList(accountQuery);

        List<DashboardVO.PositionSummary> summaries = new ArrayList<>();

        for (Account account : accounts) {
            Position positionQuery = new Position();
            positionQuery.setAccountId(account.getId());
            List<Position> positions = positionMapper.selectPositionList(positionQuery);

            for (Position position : positions) {
                summaries.add(DashboardVO.PositionSummary.builder()
                        .symbol(position.getSymbol())
                        .quantity(position.getQuantity())
                        .avgPrice(position.getAvgPrice())
                        .currentPrice(position.getCurrentPrice())
                        .unrealizedPnl(position.getUnrealizedPnl())
                        .unrealizedPnlRatio(position.getUnrealizedPnlRatio())
                        .build());
            }
        }

        return summaries;
    }

    /**
     * 获取最近订单
     */
    private List<DashboardVO.RecentOrder> getRecentOrders(Long userId) {
        Account accountQuery = new Account();
        accountQuery.setUserId(userId);
        List<Account> accounts = accountMapper.selectAccountList(accountQuery);

        if (accounts.isEmpty()) {
            return new ArrayList<>();
        }

        Order orderQuery = new Order();
        List<Order> orders = orderMapper.selectOrderList(orderQuery);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return orders.stream()
                .limit(10)
                .map(order -> DashboardVO.RecentOrder.builder()
                        .orderNo(order.getOrderNo())
                        .symbol(order.getSymbol())
                        .side(order.getSide())
                        .quantity(order.getQuantity())
                        .price(order.getPrice())
                        .status(order.getStatus())
                        .createTime(order.getCreateTime() != null ?
                                order.getCreateTime().toString() : "")
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 获取最近信号
     */
    private List<DashboardVO.RecentSignal> getRecentSignals(Long userId) {
        Strategy strategyQuery = new Strategy();
        strategyQuery.setUserId(userId);
        List<Strategy> strategies = strategyMapper.selectStrategyList(strategyQuery);

        if (strategies.isEmpty()) {
            return new ArrayList<>();
        }

        StrategySignal signalQuery = new StrategySignal();
        List<StrategySignal> signals = signalMapper.selectStrategySignalList(signalQuery);

        return signals.stream()
                .limit(10)
                .map(signal -> DashboardVO.RecentSignal.builder()
                        .symbol(signal.getSymbol())
                        .signalType(signal.getSignalType())
                        .price(signal.getPrice())
                        .reason(signal.getReason())
                        .createTime(signal.getCreateTime() != null ?
                                signal.getCreateTime().toString() : "")
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 判断是否是今天
     */
    private boolean isToday(Object date) {
        if (date == null) return false;
        LocalDate today = LocalDate.now();
        // 简化处理
        return true;
    }
}