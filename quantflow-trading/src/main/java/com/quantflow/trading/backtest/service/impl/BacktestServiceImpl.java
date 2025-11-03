package com.quantflow.trading.backtest.service.impl;

import java.util.List;
import com.quantflow.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quantflow.trading.backtest.mapper.BacktestMapper;
import com.quantflow.trading.backtest.domain.Backtest;
import com.quantflow.trading.backtest.service.IBacktestService;

/**
 * 回测Service业务层处理
 * 
 * @author quantflow
 * @date 2025-11-03
 */
@Service
public class BacktestServiceImpl implements IBacktestService 
{
    @Autowired
    private BacktestMapper backtestMapper;

    /**
     * 查询回测
     * 
     * @param id 回测主键
     * @return 回测
     */
    @Override
    public Backtest selectBacktestById(Long id)
    {
        return backtestMapper.selectBacktestById(id);
    }

    /**
     * 查询回测列表
     * 
     * @param backtest 回测
     * @return 回测
     */
    @Override
    public List<Backtest> selectBacktestList(Backtest backtest)
    {
        return backtestMapper.selectBacktestList(backtest);
    }

    /**
     * 新增回测
     * 
     * @param backtest 回测
     * @return 结果
     */
    @Override
    public int insertBacktest(Backtest backtest)
    {
        backtest.setCreateTime(DateUtils.getNowDate());
        return backtestMapper.insertBacktest(backtest);
    }

    /**
     * 修改回测
     * 
     * @param backtest 回测
     * @return 结果
     */
    @Override
    public int updateBacktest(Backtest backtest)
    {
        return backtestMapper.updateBacktest(backtest);
    }

    /**
     * 批量删除回测
     * 
     * @param ids 需要删除的回测主键
     * @return 结果
     */
    @Override
    public int deleteBacktestByIds(Long[] ids)
    {
        return backtestMapper.deleteBacktestByIds(ids);
    }

    /**
     * 删除回测信息
     * 
     * @param id 回测主键
     * @return 结果
     */
    @Override
    public int deleteBacktestById(Long id)
    {
        return backtestMapper.deleteBacktestById(id);
    }
}
