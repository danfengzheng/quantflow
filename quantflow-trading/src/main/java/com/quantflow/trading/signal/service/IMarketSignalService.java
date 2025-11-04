package com.quantflow.trading.signal.service;

import com.quantflow.trading.signal.domain.MarketSignal;

import java.io.IOException;
import java.util.List;

/**
 * 市场信号分析Service接口
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
public interface IMarketSignalService {
    /**
     * 查询市场信号分析
     * 
     * @param signalId 市场信号分析主键
     * @return 市场信号分析
     */
    public MarketSignal selectMarketSignalBySignalId(Long signalId);

    /**
     * 查询市场信号分析列表
     * 
     * @param marketSignal 市场信号分析
     * @return 市场信号分析集合
     */
    public List<MarketSignal> selectMarketSignalList(MarketSignal marketSignal);

    /**
     * 实时分析市场信号
     * 
     * @param symbol 交易对
     * @param interval K线周期
     * @return 市场信号分析结果
     */
    public MarketSignal analyzeMarketSignal(String symbol, String interval);

    /**
     * 批量分析多个交易对
     * 
     * @param symbols 交易对列表
     * @param interval K线周期
     * @return 市场信号分析结果列表
     */
    public List<MarketSignal> batchAnalyzeMarketSignals(List<String> symbols, String interval);

    /**
     * 新增市场信号分析
     * 
     * @param marketSignal 市场信号分析
     * @return 结果
     */
    public int insertMarketSignal(MarketSignal marketSignal);

    /**
     * 修改市场信号分析
     * 
     * @param marketSignal 市场信号分析
     * @return 结果
     */
    public int updateMarketSignal(MarketSignal marketSignal);

    /**
     * 批量删除市场信号分析
     * 
     * @param signalIds 需要删除的市场信号分析主键集合
     * @return 结果
     */
    public int deleteMarketSignalBySignalIds(Long[] signalIds);

    /**
     * 删除市场信号分析信息
     * 
     * @param signalId 市场信号分析主键
     * @return 结果
     */
    public int deleteMarketSignalBySignalId(Long signalId);
}
