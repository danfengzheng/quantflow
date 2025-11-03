package com.quantflow.trading.backtest.mapper;

import java.util.List;
import com.quantflow.trading.backtest.domain.Backtest;

/**
 * 回测Mapper接口
 * 
 * @author quantflow
 * @date 2025-11-03
 */
public interface BacktestMapper 
{
    /**
     * 查询回测
     * 
     * @param id 回测主键
     * @return 回测
     */
    public Backtest selectBacktestById(Long id);

    /**
     * 查询回测列表
     * 
     * @param backtest 回测
     * @return 回测集合
     */
    public List<Backtest> selectBacktestList(Backtest backtest);

    /**
     * 新增回测
     * 
     * @param backtest 回测
     * @return 结果
     */
    public int insertBacktest(Backtest backtest);

    /**
     * 修改回测
     * 
     * @param backtest 回测
     * @return 结果
     */
    public int updateBacktest(Backtest backtest);

    /**
     * 删除回测
     * 
     * @param id 回测主键
     * @return 结果
     */
    public int deleteBacktestById(Long id);

    /**
     * 批量删除回测
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBacktestByIds(Long[] ids);
}
