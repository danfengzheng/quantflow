package com.quantflow.trading.statistics.service;

import com.quantflow.common.utils.SecurityUtils;
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.mapper.AccountMapper;
import com.quantflow.trading.order.domain.Order;
import com.quantflow.trading.order.mapper.OrderMapper;
import com.quantflow.trading.statistics.vo.TradingStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 统计服务
 */
@Slf4j
@Service
public class StatisticsService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 获取交易统计
     */
    public TradingStatisticsVO getTradingStatistics() {
        Long userId = SecurityUtils.getUserId();

        // 查询用户所有账户
        Account accountQuery = new Account();
        accountQuery.setUserId(userId);
        List<Account> accounts = accountMapper.selectAccountList(accountQuery);

        if (accounts.isEmpty()) {
            return createEmptyStatistics();
        }

        // 查询所有已成交订单
        Order orderQuery = new Order();
        List<Order> allOrders = orderMapper.selectOrderList(orderQuery);

        List<Order> filledOrders = allOrders.stream()
                .filter(o -> "FILLED".equals(o.getStatus()))
                .collect(Collectors.toList());

        if (filledOrders.isEmpty()) {
            return createEmptyStatistics();
        }

        // 计算统计数据
        return calculateStatistics(filledOrders);
    }

    /**
     * 计算统计数据
     */
    private TradingStatisticsVO calculateStatistics(List<Order> orders) {
        int totalTrades = orders.size();
        int profitTrades = 0;
        int lossTrades = 0;

        BigDecimal totalProfit = BigDecimal.ZERO;
        BigDecimal totalLoss = BigDecimal.ZERO;
        BigDecimal maxProfit = BigDecimal.ZERO;
        BigDecimal maxLoss = BigDecimal.ZERO;

        // 简化处理：这里应该配对买卖订单计算实际盈亏
        // 当前只是示例逻辑
        for (Order order : orders) {
            // TODO: 实现真实的盈亏计算逻辑
            // 需要配对买入和卖出订单
        }

        // 计算胜率
        BigDecimal winRate = BigDecimal.ZERO;
        if (totalTrades > 0) {
            winRate = BigDecimal.valueOf(profitTrades)
                    .divide(BigDecimal.valueOf(totalTrades), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
        }

        // 计算净盈利
        BigDecimal netProfit = totalProfit.add(totalLoss);

        // 计算盈亏比
        BigDecimal profitLossRatio = BigDecimal.ZERO;
        if (totalLoss.abs().compareTo(BigDecimal.ZERO) > 0) {
            profitLossRatio = totalProfit.divide(totalLoss.abs(), 2, RoundingMode.HALF_UP);
        }

        // 计算平均盈利和亏损
        BigDecimal avgProfit = profitTrades > 0 ?
                totalProfit.divide(BigDecimal.valueOf(profitTrades), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        BigDecimal avgLoss = lossTrades > 0 ?
                totalLoss.divide(BigDecimal.valueOf(lossTrades), 2, RoundingMode.HALF_UP) :
                BigDecimal.ZERO;

        return TradingStatisticsVO.builder()
                .totalTrades(totalTrades)
                .profitTrades(profitTrades)
                .lossTrades(lossTrades)
                .winRate(winRate)
                .totalProfit(totalProfit)
                .totalLoss(totalLoss)
                .netProfit(netProfit)
                .profitLossRatio(profitLossRatio)
                .avgProfit(avgProfit)
                .avgLoss(avgLoss)
                .maxProfit(maxProfit)
                .maxLoss(maxLoss)
                .consecutiveWins(0)
                .consecutiveLosses(0)
                .dailyPnLList(new ArrayList<>())
                .build();
    }

    /**
     * 创建空统计
     */
    private TradingStatisticsVO createEmptyStatistics() {
        return TradingStatisticsVO.builder()
                .totalTrades(0)
                .profitTrades(0)
                .lossTrades(0)
                .winRate(BigDecimal.ZERO)
                .totalProfit(BigDecimal.ZERO)
                .totalLoss(BigDecimal.ZERO)
                .netProfit(BigDecimal.ZERO)
                .profitLossRatio(BigDecimal.ZERO)
                .avgProfit(BigDecimal.ZERO)
                .avgLoss(BigDecimal.ZERO)
                .maxProfit(BigDecimal.ZERO)
                .maxLoss(BigDecimal.ZERO)
                .consecutiveWins(0)
                .consecutiveLosses(0)
                .dailyPnLList(new ArrayList<>())
                .build();
    }
}