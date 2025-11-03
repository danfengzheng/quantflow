package com.quantflow.trading.order.service;

import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.PositionService;
import com.quantflow.trading.account.service.impl.AccountServiceImpl;
import com.quantflow.trading.common.exchange.ExchangeFactory;
import com.quantflow.trading.common.exchange.IExchangeAdapter;
import com.quantflow.trading.order.domain.Order;
import com.quantflow.trading.order.mapper.OrderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * 订单执行服务
 */
@Slf4j
@Service
public class OrderExecuteService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private AccountServiceImpl accountService;

    @Autowired
    private ExchangeFactory exchangeFactory;
    @Autowired
    private PositionService positionService;
    /**
     * 执行订单
     */
    public void executeOrder(Order order) {
        log.info("开始执行订单：orderNo={}, symbol={}, side={}, type={}",
                order.getOrderNo(), order.getSymbol(), order.getSide(), order.getType());

        IExchangeAdapter adapter = null;
        try {
            // 1. 获取账户信息
            Account account = accountService.getDecryptedAccount(order.getAccountId());
            if (account == null) {
                throw new RuntimeException("账户不存在");
            }

            // 2. 创建交易所适配器
            adapter = exchangeFactory.createAdapter(account);

            // 3. 更新订单状态为已提交
            order.setStatus("SUBMITTED");
            orderMapper.updateOrder(order);

            // 4. 根据订单类型执行
            String exchangeOrderId = null;

            if ("MARKET".equals(order.getType())) {
                // 市价单
                if ("BUY".equals(order.getSide())) {
                    exchangeOrderId = adapter.marketBuy(order.getSymbol(), order.getQuantity());
                } else {
                    exchangeOrderId = adapter.marketSell(order.getSymbol(), order.getQuantity());
                }
            } else if ("LIMIT".equals(order.getType())) {
                // 限价单
                if ("BUY".equals(order.getSide())) {
                    exchangeOrderId = adapter.limitBuy(order.getSymbol(), order.getPrice(), order.getQuantity());
                } else {
                    exchangeOrderId = adapter.limitSell(order.getSymbol(), order.getPrice(), order.getQuantity());
                }
            }

            // 5. 更新订单信息
            order.setExchangeOrderId(exchangeOrderId);
            order.setStatus("FILLED"); // 市价单通常立即成交

            log.info("订单执行成功：orderNo={}, exchangeOrderId={}",
                    order.getOrderNo(), exchangeOrderId);
            // 6. 更新持仓
            positionService.updatePositionByOrder(order);

            log.info("订单执行成功并更新持仓：orderNo={}, exchangeOrderId={}",
                    order.getOrderNo(), exchangeOrderId);
        } catch (IOException e) {
            log.error("订单执行失败：orderNo={}", order.getOrderNo(), e);
            order.setStatus("FAILED");
            order.setErrorMsg(e.getMessage());
        } finally {
            // 更新订单状态
            orderMapper.updateOrder(order);
        }
    }

    /**
     * 取消订单
     */
    public void cancelOrder(Order order) {
        log.info("开始取消订单：orderNo={}, exchangeOrderId={}",
                order.getOrderNo(), order.getExchangeOrderId());

        IExchangeAdapter adapter = null;
        try {
            // 获取账户信息
            Account account = accountService.getDecryptedAccount(order.getAccountId());
            if (account == null) {
                throw new RuntimeException("账户不存在");
            }

            // 创建交易所适配器
            adapter = exchangeFactory.createAdapter(account);

            // 取消订单
            boolean success = adapter.cancelOrder(order.getSymbol(), order.getExchangeOrderId());

            if (success) {
                order.setStatus("CANCELLED");
                log.info("订单取消成功：orderNo={}", order.getOrderNo());
            } else {
                throw new RuntimeException("取消订单失败");
            }

        } catch (Exception e) {
            log.error("取消订单失败：orderNo={}", order.getOrderNo(), e);
            order.setErrorMsg(e.getMessage());
        } finally {
            orderMapper.updateOrder(order);
        }
    }
}