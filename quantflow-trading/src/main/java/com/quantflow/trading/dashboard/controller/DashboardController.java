package com.quantflow.trading.dashboard.controller;

import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.constant.TradingMessageKeys;
import com.quantflow.trading.dashboard.service.DashboardService;
import com.quantflow.trading.dashboard.vo.DashboardVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 仪表盘Controller
 */
@Slf4j
@RestController
@RequestMapping("/trading/dashboard")
public class DashboardController extends BaseController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * 获取仪表盘数据
     */
    @PreAuthorize("@ss.hasPermi('trading:dashboard:query')")
    @GetMapping("/data")
    public AjaxResult getDashboardData() {
        try {
            DashboardVO data = dashboardService.getDashboardData();
            return AjaxResult.success(data);
        } catch (Exception e) {
            log.error("获取仪表盘数据失败", e);
            return AjaxResult.error(TradingMessageKeys.DASHBOARD_GET_DATA_FAILED, e.getMessage());
        }
    }
}