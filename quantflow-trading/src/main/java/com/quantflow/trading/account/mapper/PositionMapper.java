package com.quantflow.trading.account.mapper;

import com.quantflow.trading.account.domain.Position;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 持仓Mapper接口
 */
@Mapper
public interface PositionMapper {

    /**
     * 查询持仓列表
     */
    List<Position> selectPositionList(Position position);

    /**
     * 查询持仓详情
     */
    Position selectPositionById(Long id);

    /**
     * 根据账户和交易对查询持仓
     */
    Position selectPositionByAccountAndSymbol(Long accountId, String symbol);

    /**
     * 新增持仓
     */
    int insertPosition(Position position);

    /**
     * 修改持仓
     */
    int updatePosition(Position position);

    /**
     * 删除持仓
     */
    int deletePositionById(Long id);
}