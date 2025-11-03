package com.quantflow.trading.strategy.service;

import com.quantflow.trading.order.domain.Order;
import com.quantflow.trading.order.mapper.OrderMapper;
import com.quantflow.trading.order.service.OrderExecuteService;
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.domain.StrategySignal;
import com.quantflow.trading.strategy.mapper.StrategyMapper;
import com.quantflow.trading.strategy.mapper.StrategySignalMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 信号处理服务
 * 将策略信号转换为订单并执行
 */
@Slf4j
@Service
public class SignalProcessService {

    @Autowired
    private StrategySignalMapper signalMapper;

    @Autowired
    private StrategyMapper strategyMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderExecuteService orderExecuteService;

    /**
     * 处理待处理的信号
     */
    @Transactional
    public void processSignals() {
        // 查询所有待处理的信号
        StrategySignal query = new StrategySignal();
        query.setStatus(0); // 待处理
        List<StrategySignal> signals = signalMapper.selectStrategySignalList(query);

        if (signals.isEmpty()) {
            return;
        }

        log.info("找到 {} 个待处理信号", signals.size());

        for (StrategySignal signal : signals) {
            try {
                processSignal(signal);
            } catch (Exception e) {
                log.error("处理信号失败：signalId={}", signal.getId(), e);
                // 标记信号为已忽略
                signal.setStatus(2);
                signalMapper.updateStrategySignal(signal);
            }
        }
    }

    /**
     * 处理单个信号
     */
    private void processSignal(StrategySignal signal) {
        log.info("开始处理信号：signalId={}, type={}, symbol={}",
                signal.getId(), signal.getSignalType(), signal.getSymbol());

        // 1. 获取策略信息
        Strategy strategy = strategyMapper.selectStrategyById(signal.getStrategyId());
        if (strategy == null) {
            throw new RuntimeException("策略不存在");
        }

        // 2. 计算下单数量
        BigDecimal quantity = calculateQuantity(signal, strategy);
        if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
            log.warn("计算得到的数量为0，跳过此信号");
            signal.setStatus(2); // 已忽略
            signalMapper.updateStrategySignal(signal);
            return;
        }

        // 3. 创建订单
        Order order = Order.builder()
                .strategyId(strategy.getId())
                .accountId(strategy.getAccountId())
                .symbol(signal.getSymbol())
                .side(signal.getSignalType())
                .type("MARKET") // 默认使用市价单
                .quantity(quantity)
                .status("PENDING")
                .build();

        orderMapper.insertOrder(order);

        log.info("创建订单：orderNo={}, side={}, quantity={}",
                order.getOrderNo(), order.getSide(), order.getQuantity());

        // 4. 执行订单
        orderExecuteService.executeOrder(order);

        // 5. 更新信号状态
        signal.setStatus(1); // 已处理
        signalMapper.updateStrategySignal(signal);

        log.info("信号处理完成：signalId={}, orderNo={}", signal.getId(), order.getOrderNo());
    }

    /**
     * 计算下单数量
     * 这里简化处理，实际应该根据资金管理策略计算
     */
    private BigDecimal calculateQuantity(StrategySignal signal, Strategy strategy) {
        // 如果信号中有建议数量，使用信号数量
        if (signal.getQuantity() != null && signal.getQuantity().compareTo(BigDecimal.ZERO) > 0) {
            return signal.getQuantity();
        }

        // 否则使用固定数量（实际应该根据账户余额和风控规则计算）
        // 这里简化为固定值
        if (signal.getSymbol().startsWith("BTC")) {
            return new BigDecimal("0.001"); // BTC
        } else if (signal.getSymbol().startsWith("ETH")) {
            return new BigDecimal("0.01"); // ETH
        } else {
            return new BigDecimal("10"); // 其他币种
        }
    }
}