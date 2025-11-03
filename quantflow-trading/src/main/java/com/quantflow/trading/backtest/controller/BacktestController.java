package com.quantflow.trading.backtest.controller;

import java.util.List;

import com.alibaba.fastjson2.JSON;
import com.quantflow.trading.backtest.service.BacktestExecuteService;
import com.quantflow.trading.backtest.vo.BacktestResultVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.trading.backtest.domain.Backtest;
import com.quantflow.trading.backtest.service.IBacktestService;
import com.quantflow.common.utils.poi.ExcelUtil;
import com.quantflow.common.core.page.TableDataInfo;

/**
 * 回测Controller
 * 
 * @author quantflow
 * @date 2025-11-03
 */
@Slf4j
@RestController
@RequestMapping("/trading/backtest")
public class BacktestController extends BaseController
{
    @Autowired
    private IBacktestService backtestService;

    @Autowired
    private BacktestExecuteService backtestExecuteService;

    /**
     * 查询回测列表
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:list')")
    @GetMapping("/list")
    public TableDataInfo list(Backtest backtest)
    {
        startPage();
        List<Backtest> list = backtestService.selectBacktestList(backtest);
        return getDataTable(list);
    }

    /**
     * 导出回测列表
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:export')")
    @Log(title = "回测", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Backtest backtest)
    {
        List<Backtest> list = backtestService.selectBacktestList(backtest);
        ExcelUtil<Backtest> util = new ExcelUtil<Backtest>(Backtest.class);
        util.exportExcel(response, list, "回测数据");
    }

    /**
     * 获取回测详细信息
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(backtestService.selectBacktestById(id));
    }

    /**
     * 新增回测
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:add')")
    @Log(title = "回测", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Backtest backtest)
    {
        return toAjax(backtestService.insertBacktest(backtest));
    }

    /**
     * 修改回测
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:edit')")
    @Log(title = "回测", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Backtest backtest)
    {
        return toAjax(backtestService.updateBacktest(backtest));
    }

    /**
     * 删除回测
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:remove')")
    @Log(title = "回测", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(backtestService.deleteBacktestByIds(ids));
    }
    /**
     * 执行回测
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:execute')")
    @PostMapping("/execute/{id}")
    public AjaxResult executeBacktest(@PathVariable Long id) {
        try {
            // 异步执行回测
            backtestExecuteService.executeBacktest(id);
            return AjaxResult.success("回测任务已启动");
        } catch (Exception e) {
            log.error("启动回测失败", e);
            return AjaxResult.error("启动回测失败：" + e.getMessage());
        }
    }

    /**
     * 获取回测结果
     */
    @PreAuthorize("@ss.hasPermi('trading:backtest:query')")
    @GetMapping("/result/{id}")
    public AjaxResult getBacktestResult(@PathVariable Long id) {
        try {
            Backtest backtest = backtestService.selectBacktestById(id);
            if (backtest == null) {
                return AjaxResult.error("回测任务不存在");
            }

            if (backtest.getResult() == null || backtest.getResult().isEmpty()) {
                return AjaxResult.error("回测结果不存在");
            }

            BacktestResultVO result = JSON.parseObject(backtest.getResult(), BacktestResultVO.class);
            return AjaxResult.success(result);
        } catch (Exception e) {
            log.error("获取回测结果失败", e);
            return AjaxResult.error("获取回测结果失败：" + e.getMessage());
        }
    }
}
