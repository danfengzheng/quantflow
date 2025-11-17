package com.quantflow.trading.account.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.constant.TradingMessageKeys;
import com.quantflow.common.core.page.TableDataInfo;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.common.utils.poi.ExcelUtil;
import com.quantflow.trading.account.service.AccountTestService;
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
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.IAccountService;

/**
 * 账户Controller
 * 
 * @author quantflow
 * @date 2025-10-31
 */
@Slf4j
@RestController
@RequestMapping("/trading/account")
public class AccountController extends BaseController
{
    @Autowired
    private IAccountService accountService;
    @Autowired
    private AccountTestService accountTestService;

    /**
     * 查询账户列表
     */
    @PreAuthorize("@ss.hasPermi('trading:account:list')")
    @GetMapping("/list")
    public TableDataInfo list(Account account)
    {
        startPage();
        List<Account> list = accountService.selectAccountList(account);
        return getDataTable(list);
    }

    /**
     * 导出账户列表
     */
    @PreAuthorize("@ss.hasPermi('trading:account:export')")
    @Log(title = "账户", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Account account)
    {
        List<Account> list = accountService.selectAccountList(account);
        ExcelUtil<Account> util = new ExcelUtil<Account>(Account.class);
        util.exportExcel(response, list, "账户数据");
    }

    /**
     * 获取账户详细信息
     */
    @PreAuthorize("@ss.hasPermi('trading:account:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(accountService.selectAccountById(id));
    }

    /**
     * 新增账户
     */
    @PreAuthorize("@ss.hasPermi('trading:account:add')")
    @Log(title = "账户", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Account account)
    {
        return toAjax(accountService.insertAccount(account));
    }

    /**
     * 修改账户
     */
    @PreAuthorize("@ss.hasPermi('trading:account:edit')")
    @Log(title = "账户", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Account account)
    {
        return toAjax(accountService.updateAccount(account));
    }

    /**
     * 删除账户
     */
    @PreAuthorize("@ss.hasPermi('trading:account:remove')")
    @Log(title = "账户", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(accountService.deleteAccountByIds(ids));
    }

    /**
     * 测试交易所连接
     */
    @PreAuthorize("@ss.hasPermi('trading:account:test')")
    @PostMapping("/testConnection/{id}")
    public AjaxResult testConnection(@PathVariable Long id) {
        try {
            Map<String, Object> result = accountTestService.testConnection(id);
            if ((Boolean) result.get("success")) {
                return AjaxResult.success(TradingMessageKeys.ACCOUNT_TEST_CONNECTION_SUCCESS, result);
            } else {
                return AjaxResult.error((String) result.get("message"));
            }
        } catch (Exception e) {
            log.error("测试连接失败", e);
            return AjaxResult.error(TradingMessageKeys.ACCOUNT_TEST_CONNECTION_FAILED, e.getMessage());
        }
    }

    /**
     * 获取账户余额
     */
    @PreAuthorize("@ss.hasPermi('trading:account:query')")
    @GetMapping("/balance/{id}")
    public AjaxResult getBalance(@PathVariable Long id) {
        try {
            Map<String, Object> result = accountTestService.getBalance(id);
            if ((Boolean) result.get("success")) {
                return AjaxResult.success(result);
            } else {
                return AjaxResult.error((String) result.get("message"));
            }
        } catch (Exception e) {
            log.error("获取余额失败", e);
            return AjaxResult.error(TradingMessageKeys.ACCOUNT_GET_BALANCE_FAILED, e.getMessage());
        }
    }
}
