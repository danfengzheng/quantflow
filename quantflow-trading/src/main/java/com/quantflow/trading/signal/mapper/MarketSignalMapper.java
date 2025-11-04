package com.quantflow.trading.signal.mapper;


import com.quantflow.trading.signal.domain.MarketSignal;

import java.util.List;

/**
 * 市场信号分析Mapper接口
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
public interface MarketSignalMapper {
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
     * 删除市场信号分析
     * 
     * @param signalId 市场信号分析主键
     * @return 结果
     */
    public int deleteMarketSignalBySignalId(Long signalId);

    /**
     * 批量删除市场信号分析
     * 
     * @param signalIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteMarketSignalBySignalIds(Long[] signalIds);
}
