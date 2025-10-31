package com.quantflow.trading.account.controller;

import java.util.List;

import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.core.page.TableDataInfo;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.common.utils.poi.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
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
import com.quantflow.trading.account.domain.QfAccount;
import com.quantflow.trading.account.service.IQfAccountService;

/**
 * 账户Controller
 * 
 * @author quantflow
 * @date 2025-10-31
 */
@RestController
@RequestMapping("/trading/account")
public class QfAccountController extends BaseController
{
    @Autowired
    private IQfAccountService qfAccountService;

    /**
     * 查询账户列表
     */
    @PreAuthorize("@ss.hasPermi('trading:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(QfAccount qfAccount)
    {
        startPage();
        List<QfAccount> list = qfAccountService.selectQfAccountList(qfAccount);
        return getDataTable(list);
    }

    /**
     * 导出账户列表
     */
    @PreAuthorize("@ss.hasPermi('trading:account:export')")
    @Log(title = "账户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, QfAccount qfAccount)
    {
        List<QfAccount> list = qfAccountService.selectQfAccountList(qfAccount);
        ExcelUtil<QfAccount> util = new ExcelUtil<QfAccount>(QfAccount.class);
        util.exportExcel(response, list, "账户数据");
    }

    /**
     * 获取账户详细信息
     */
    @PreAuthorize("@ss.hasPermi('trading:account:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(qfAccountService.selectQfAccountById(id));
    }

    /**
     * 新增账户
     */
    @PreAuthorize("@ss.hasPermi('trading:account:add')")
    @Log(title = "账户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody QfAccount qfAccount)
    {
        return toAjax(qfAccountService.insertQfAccount(qfAccount));
    }

    /**
     * 修改账户
     */
    @PreAuthorize("@ss.hasPermi('trading:account:edit')")
    @Log(title = "账户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody QfAccount qfAccount)
    {
        return toAjax(qfAccountService.updateQfAccount(qfAccount));
    }

    /**
     * 删除账户
     */
    @PreAuthorize("@ss.hasPermi('trading:account:remove')")
    @Log(title = "账户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(qfAccountService.deleteQfAccountByIds(ids));
    }
}
