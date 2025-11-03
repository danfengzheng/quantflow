package com.quantflow.trading.risk.service;

import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.domain.Position;
import com.quantflow.trading.account.mapper.AccountMapper;
import com.quantflow.trading.account.mapper.PositionMapper;
import com.quantflow.trading.order.domain.Order;
import com.quantflow.trading.order.mapper.OrderMapper;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.mapper.StrategyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 风控服务
 */
@Slf4j
@Service
public class RiskControlService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PositionMapper positionMapper;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private StrategyMapper strategyMapper;

    /**
     * 下单前风控检查
     */
    public RiskCheckResult checkBeforeOrder(Order order) {
        log.info("执行下单前风控检查：symbol={}, side={}, quantity={}",
                order.getSymbol(), order.getSide(), order.getQuantity());

        // 1. 检查单笔订单金额
        RiskCheckResult amountCheck = checkOrderAmount(order);
        if (!amountCheck.isPassed()) {
            return amountCheck;
        }

        // 2. 检查当日交易次数
        RiskCheckResult dailyTradesCheck = checkDailyTrades(order);
        if (!dailyTradesCheck.isPassed()) {
            return dailyTradesCheck;
        }

        // 3. 检查持仓比例
        RiskCheckResult positionRatioCheck = checkPositionRatio(order);
        if (!positionRatioCheck.isPassed()) {
            return positionRatioCheck;
        }

        // 4. 检查账户余额（简化）
        // TODO: 实现真实的余额检查

        log.info("风控检查通过");
        return RiskCheckResult.passed();
    }

    /**
     * 检查单笔订单金额
     */
    private RiskCheckResult checkOrderAmount(Order order) {
        // 默认单笔最大金额 10000 USDT
        BigDecimal maxAmount = new BigDecimal("10000");

        BigDecimal orderAmount = order.getQuantity();
        if (order.getPrice() != null) {
            orderAmount = order.getQuantity().multiply(order.getPrice());
        }

        if (orderAmount.compareTo(maxAmount) > 0) {
            return RiskCheckResult.rejected("单笔订单金额超过限制：" + maxAmount);
        }

        return RiskCheckResult.passed();
    }

    /**
     * 检查当日交易次数
     */
    private RiskCheckResult checkDailyTrades(Order order) {
        // 默认单日最大交易次数 100
        int maxDailyTrades = 100;

        // 查询今日订单数
        Order query = new Order();
        query.setAccountId(order.getAccountId());
        List<Order> orders = orderMapper.selectOrderList(query);

        long todayCount = orders.stream()
                .filter(o -> isToday(o.getCreateTime()))
                .count();

        if (todayCount >= maxDailyTrades) {
            return RiskCheckResult.rejected("当日交易次数超过限制：" + maxDailyTrades);
        }

        return RiskCheckResult.passed();
    }

    /**
     * 检查持仓比例
     */
    private RiskCheckResult checkPositionRatio(Order order) {
        // 默认最大持仓比例 80%
        BigDecimal maxPositionRatio = new BigDecimal("80");

        // 简化处理，实际应该计算真实的持仓比例
        // TODO: 实现真实的持仓比例计算

        return RiskCheckResult.passed();
    }

    /**
     * 检查止损止盈
     */
    public void checkStopLossAndTakeProfit() {
        log.debug("检查止损止盈");

        // 查询所有持仓
        Position query = new Position();
        List<Position> positions = positionMapper.selectPositionList(query);

        for (Position position : positions) {
            // 获取策略配置
            // TODO: 根据策略配置检查止损止盈

            // 检查止损
            if (shouldStopLoss(position)) {
                log.warn("触发止损：symbol={}, pnlRatio={}",
                        position.getSymbol(), position.getUnrealizedPnlRatio());
                // TODO: 自动平仓
            }

            // 检查止盈
            if (shouldTakeProfit(position)) {
                log.info("触发止盈：symbol={}, pnlRatio={}",
                        position.getSymbol(), position.getUnrealizedPnlRatio());
                // TODO: 自动平仓
            }
        }
    }

    /**
     * 是否应该止损
     */
    private boolean shouldStopLoss(Position position) {
        if (position.getUnrealizedPnlRatio() == null) {
            return false;
        }
        // 默认止损 -5%
        return position.getUnrealizedPnlRatio().compareTo(new BigDecimal("-5")) < 0;
    }

    /**
     * 是否应该止盈
     */
    private boolean shouldTakeProfit(Position position) {
        if (position.getUnrealizedPnlRatio() == null) {
            return false;
        }
        // 默认止盈 +10%
        return position.getUnrealizedPnlRatio().compareTo(new BigDecimal("10")) > 0;
    }

    /**
     * 判断是否是今天
     */
    private boolean isToday(Object date) {
        // 简化处理
        return true;
    }

    /**
     * 风控检查结果
     */
    public static class RiskCheckResult {
        private boolean passed;
        private String message;

        public static RiskCheckResult passed() {
            RiskCheckResult result = new RiskCheckResult();
            result.passed = true;
            result.message = "风控检查通过";
            return result;
        }

        public static RiskCheckResult rejected(String message) {
            RiskCheckResult result = new RiskCheckResult();
            result.passed = false;
            result.message = message;
            return result;
        }

        public boolean isPassed() {
            return passed;
        }

        public String getMessage() {
            return message;
        }
    }
}