package com.quantflow.trading.strategy.controller;

import java.util.List;

import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.constant.TradingMessageKeys;
import com.quantflow.common.core.page.TableDataInfo;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.common.utils.poi.ExcelUtil;
import com.quantflow.trading.strategy.service.StrategyExecuteService;
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
import com.quantflow.trading.strategy.domain.Strategy;
import com.quantflow.trading.strategy.service.IStrategyService;

/**
 * 策略Controller
 * 
 * @author quantflow
 * @date 2025-11-01
 */
@Slf4j
@RestController
@RequestMapping("/trading/strategy")
public class StrategyController extends BaseController
{
    @Autowired
    private IStrategyService strategyService;

    /**
     * 手动执行策略（用于测试）
     */
    @Autowired
    private StrategyExecuteService strategyExecuteService;

    /**
     * 查询策略列表
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:list')")
    @GetMapping("/list")
    public TableDataInfo list(Strategy strategy)
    {
        startPage();
        List<Strategy> list = strategyService.selectStrategyList(strategy);
        return getDataTable(list);
    }

    /**
     * 导出策略列表
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:export')")
    @Log(title = "策略", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Strategy strategy)
    {
        List<Strategy> list = strategyService.selectStrategyList(strategy);
        ExcelUtil<Strategy> util = new ExcelUtil<Strategy>(Strategy.class);
        util.exportExcel(response, list, "策略数据");
    }

    /**
     * 获取策略详细信息
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(strategyService.selectStrategyById(id));
    }

    /**
     * 新增策略
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:add')")
    @Log(title = "策略", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Strategy strategy)
    {
        return toAjax(strategyService.insertStrategy(strategy));
    }

    /**
     * 修改策略
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:edit')")
    @Log(title = "策略", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Strategy strategy)
    {
        return toAjax(strategyService.updateStrategy(strategy));
    }

    /**
     * 删除策略
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:remove')")
    @Log(title = "策略", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(strategyService.deleteStrategyByIds(ids));
    }

    /**
     * 启动策略
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:start')")
    @PostMapping("/start/{id}")
    public AjaxResult startStrategy(@PathVariable Long id) {
        Strategy strategy = strategyService.selectStrategyById(id);
        if (strategy == null) {
            return AjaxResult.error(TradingMessageKeys.STRATEGY_NOT_FOUND);
        }

        if (strategy.getStatus() == 2) {
            return AjaxResult.error(TradingMessageKeys.STRATEGY_ALREADY_RUNNING);
        }

        // 更新状态为运行中
        strategy.setStatus(2);
        strategyService.updateStrategy(strategy);

        log.info("启动策略：id={}, name={}", id, strategy.getName());
        return AjaxResult.success(TradingMessageKeys.STRATEGY_STARTED);
    }

    /**
     * 停止策略
     */
    @PreAuthorize("@ss.hasPermi('trading:strategy:stop')")
    @PostMapping("/stop/{id}")
    public AjaxResult stopStrategy(@PathVariable Long id) {
        Strategy strategy = strategyService.selectStrategyById(id);
        if (strategy == null) {
            return AjaxResult.error(TradingMessageKeys.STRATEGY_NOT_FOUND);
        }

        if (strategy.getStatus() != 2) {
            return AjaxResult.error(TradingMessageKeys.STRATEGY_NOT_RUNNING);
        }

        // 更新状态为停用
        strategy.setStatus(0);
        strategyService.stopStrategy(strategy);

        log.info("停止策略：id={}, name={}", id, strategy.getName());
        return AjaxResult.success(TradingMessageKeys.STRATEGY_STOPPED);
    }

    @PreAuthorize("@ss.hasPermi('trading:strategy:execute')")
    @PostMapping("/execute/{id}")
    public AjaxResult executeStrategy(@PathVariable Long id) {
        Strategy strategy = strategyService.selectStrategyById(id);
        if (strategy == null) {
            return AjaxResult.error(TradingMessageKeys.STRATEGY_NOT_FOUND);
        }

        try {
            strategyExecuteService.executeStrategy(strategy);
            return AjaxResult.success(TradingMessageKeys.STRATEGY_EXECUTE_COMPLETED);
        } catch (Exception e) {
            log.error("执行策略失败", e);
            return AjaxResult.error(TradingMessageKeys.STRATEGY_EXECUTE_FAILED_WITH_MSG, e.getMessage());
        }
    }
}
