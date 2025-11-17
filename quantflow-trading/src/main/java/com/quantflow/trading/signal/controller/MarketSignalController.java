package com.quantflow.trading.signal.controller;

import com.alibaba.fastjson2.JSONObject;
import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.core.page.TableDataInfo;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.common.utils.poi.ExcelUtil;
import com.quantflow.trading.signal.domain.MarketSignal;
import com.quantflow.trading.signal.service.IMarketSignalService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 市场信号分析Controller
 * 
 * @author ruoyi
 * @date 2025-11-04
 */
@RestController
@RequestMapping("/trading/signal")
public class MarketSignalController extends BaseController {
    
    @Autowired
    private IMarketSignalService marketSignalService;

    /**
     * 查询市场信号分析列表
     */
    @PreAuthorize("@ss.hasPermi('system:signal:list')")
    @GetMapping("/list")
    public TableDataInfo list(MarketSignal marketSignal) {
        startPage();
        List<MarketSignal> list = marketSignalService.selectMarketSignalList(marketSignal);
        return getDataTable(list);
    }

    /**
     * 导出市场信号分析列表
     */
    @PreAuthorize("@ss.hasPermi('system:signal:export')")
    @Log(title = "市场信号分析", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MarketSignal marketSignal) {
        List<MarketSignal> list = marketSignalService.selectMarketSignalList(marketSignal);
        ExcelUtil<MarketSignal> util = new ExcelUtil<MarketSignal>(MarketSignal.class);
        util.exportExcel(response, list, "市场信号分析数据");
    }

    /**
     * 获取市场信号分析详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:signal:query')")
    @GetMapping(value = "/{signalId}")
    public AjaxResult getInfo(@PathVariable("signalId") Long signalId) {
        return success(marketSignalService.selectMarketSignalBySignalId(signalId));
    }

    /**
     * 实时分析市场信号
     */
    @PreAuthorize("@ss.hasPermi('system:signal:analyze')")
    @GetMapping("/analyze/{symbol}/{interval}")
    public AjaxResult analyze(@PathVariable("symbol") String symbol, 
                            @PathVariable("interval") String interval) {
        MarketSignal signal = marketSignalService.analyzeMarketSignal(symbol, interval);
        if (signal != null) {
//             可选：保存分析结果到数据库
             marketSignalService.insertMarketSignal(signal);
            return success(signal);
        }
        return error("分析失败，请检查交易对和K线周期");
    }

    /**
     * 批量分析多个交易对
     */
    @PreAuthorize("@ss.hasPermi('system:signal:analyze')")
    @PostMapping("/batchAnalyze")
    public AjaxResult batchAnalyze(@RequestBody BatchAnalyzeRequest request) {
        List<MarketSignal> signals = marketSignalService.batchAnalyzeMarketSignals(
                request.getSymbols(), 
                request.getInterval()
        );
        for (MarketSignal signal :signals)
            marketSignalService.insertMarketSignal(signal);
        return success(signals);
    }

    /**
     * 获取推荐交易列表
     * 返回评分>70的买入信号或<30的卖出信号
     */
    @PreAuthorize("@ss.hasPermi('system:signal:recommend')")
    @GetMapping("/recommend")
    public AjaxResult recommend() {
        // 分析主流币种
        List<String> symbols = Arrays.asList(
                "BTCUSDT", "ETHUSDT", "BNBUSDT", "SOLUSDT", 
                "XRPUSDT", "ADAUSDT", "DOGEUSDT", "MATICUSDT"
        );
        
        List<MarketSignal> allSignals = marketSignalService.batchAnalyzeMarketSignals(symbols, "1h");
        
        // 筛选高分信号
        List<MarketSignal> recommendations = allSignals.stream()
                .filter(signal -> signal.getSignalScore() >= 70 || signal.getSignalScore() <= 30)
                .sorted((a, b) -> b.getSignalScore().compareTo(a.getSignalScore()))
                .toList();
        
        return success(recommendations);
    }

    /**
     * 新增市场信号分析
     */
    @PreAuthorize("@ss.hasPermi('system:signal:add')")
    @Log(title = "市场信号分析", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MarketSignal marketSignal) {
        return toAjax(marketSignalService.insertMarketSignal(marketSignal));
    }

    /**
     * 修改市场信号分析
     */
    @PreAuthorize("@ss.hasPermi('system:signal:edit')")
    @Log(title = "市场信号分析", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MarketSignal marketSignal) {
        return toAjax(marketSignalService.updateMarketSignal(marketSignal));
    }

    /**
     * 删除市场信号分析
     */
    @PreAuthorize("@ss.hasPermi('system:signal:remove')")
    @Log(title = "市场信号分析", businessType = BusinessType.DELETE)
    @DeleteMapping("/{signalIds}")
    public AjaxResult remove(@PathVariable Long[] signalIds) {
        return toAjax(marketSignalService.deleteMarketSignalBySignalIds(signalIds));
    }

    /**
     * 批量分析请求对象
     */
    public static class BatchAnalyzeRequest {
        private List<String> symbols;
        private String interval;

        public List<String> getSymbols() {
            return symbols;
        }

        public void setSymbols(List<String> symbols) {
            this.symbols = symbols;
        }

        public String getInterval() {
            return interval;
        }

        public void setInterval(String interval) {
            this.interval = interval;
        }
    }
}
