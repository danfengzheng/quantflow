package com.quantflow.trading.account.service;

import com.quantflow.trading.account.domain.Position;
import com.quantflow.trading.account.mapper.PositionMapper;
import com.quantflow.trading.order.domain.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * 持仓服务
 */
@Slf4j
@Service
public class PositionService {

    @Autowired
    private PositionMapper positionMapper;

    /**
     * 查询持仓列表
     */
    public List<Position> selectPositionList(Position position) {
        return positionMapper.selectPositionList(position);
    }

    /**
     * 根据订单更新持仓
     */
    @Transactional
    public void updatePositionByOrder(Order order) {
        if (!"FILLED".equals(order.getStatus())) {
            return; // 只有完全成交的订单才更新持仓
        }

        log.info("根据订单更新持仓：orderNo={}, side={}, quantity={}",
                order.getOrderNo(), order.getSide(), order.getFilledQty());

        // 查询现有持仓
        Position position = positionMapper.selectPositionByAccountAndSymbol(
                order.getAccountId(), order.getSymbol());

        if ("BUY".equals(order.getSide())) {
            // 买入
            if (position == null) {
                // 新建持仓
                position = Position.builder()
                        .accountId(order.getAccountId())
                        .symbol(order.getSymbol())
                        .quantity(order.getFilledQty())
                        .availableQty(order.getFilledQty())
                        .frozenQty(BigDecimal.ZERO)
                        .avgPrice(order.getAvgPrice() != null ? order.getAvgPrice() : order.getPrice())
                        .build();
                positionMapper.insertPosition(position);
            } else {
                // 更新持仓（加仓，重新计算平均价格）
                BigDecimal newQuantity = position.getQuantity().add(order.getFilledQty());
                BigDecimal newAvgPrice = position.getAvgPrice()
                        .multiply(position.getQuantity())
                        .add(order.getAvgPrice().multiply(order.getFilledQty()))
                        .divide(newQuantity, 8, RoundingMode.HALF_UP);

                position.setQuantity(newQuantity);
                position.setAvailableQty(position.getAvailableQty().add(order.getFilledQty()));
                position.setAvgPrice(newAvgPrice);
                positionMapper.updatePosition(position);
            }
        } else {
            // 卖出
            if (position != null) {
                BigDecimal newQuantity = position.getQuantity().subtract(order.getFilledQty());
                if (newQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                    // 全部卖出，删除持仓
                    positionMapper.deletePositionById(position.getId());
                } else {
                    // 部分卖出，更新持仓
                    position.setQuantity(newQuantity);
                    position.setAvailableQty(position.getAvailableQty().subtract(order.getFilledQty()));
                    positionMapper.updatePosition(position);
                }
            }
        }

        log.info("持仓更新完成");
    }

    /**
     * 更新持仓的当前价格和盈亏
     */
    public void updatePositionPrice(Long accountId, String symbol, BigDecimal currentPrice) {
        Position position = positionMapper.selectPositionByAccountAndSymbol(accountId, symbol);
        if (position == null) {
            return;
        }

        // 更新当前价格
        position.setCurrentPrice(currentPrice);

        // 计算市值
        BigDecimal marketValue = currentPrice.multiply(position.getQuantity());
        position.setMarketValue(marketValue);

        // 计算浮动盈亏
        BigDecimal costValue = position.getAvgPrice().multiply(position.getQuantity());
        BigDecimal unrealizedPnl = marketValue.subtract(costValue);
        position.setUnrealizedPnl(unrealizedPnl);

        // 计算浮动盈亏率
        if (costValue.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal pnlRatio = unrealizedPnl.divide(costValue, 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            position.setUnrealizedPnlRatio(pnlRatio);
        }

        positionMapper.updatePosition(position);
    }
}