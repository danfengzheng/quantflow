package com.quantflow.trading.account.controller;

import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.core.page.TableDataInfo;
import com.quantflow.trading.account.domain.Position;
import com.quantflow.trading.account.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 持仓Controller
 */
@RestController
@RequestMapping("/trading/position")
public class PositionController extends BaseController {

    @Autowired
    private PositionService positionService;

    /**
     * 查询持仓列表
     */
    @PreAuthorize("@ss.hasPermi('trading:position:list')")
    @GetMapping("/list")
    public TableDataInfo list(Position position) {
        startPage();
        List<Position> list = positionService.selectPositionList(position);
        return getDataTable(list);
    }
}