package com.quantflow.trading.account.service.impl;

import java.util.List;

import com.quantflow.common.utils.SecurityUtils;
import com.quantflow.trading.common.utils.ApiKeyEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quantflow.trading.account.mapper.AccountMapper;
import com.quantflow.trading.account.domain.Account;
import com.quantflow.trading.account.service.IAccountService;

/**
 * 账户Service业务层处理
 * 
 * @author quantflow
 * @date 2025-10-31
 */
@Service
public class AccountServiceImpl implements IAccountService 
{
    @Autowired
    private AccountMapper AccountMapper;

    /**
     * 查询账户
     * 
     * @param id 账户主键
     * @return 账户
     */
    @Override
    public Account selectAccountById(Long id)
    {
        Account account = AccountMapper.selectAccountById(id);
        if (account != null) {
            // 解密（如果需要在前端显示部分信息）
            // 这里我们不解密，使用脱敏方法
        }
        return account;
    }

    /**
     * 查询账户列表
     * 
     * @param account 账户
     * @return 账户
     */
    @Override
    public List<Account> selectAccountList(Account account)
    {
        return AccountMapper.selectAccountList(account);
    }

    /**
     * 新增账户
     * 
     * @param account 账户
     * @return 结果
     */
    @Override
    public int insertAccount(Account account)
    {
        // 设置当前用户ID
        account.setUserId(SecurityUtils.getUserId());

        // 加密 API Key 和 Secret
        if (account.getApiKey() != null && !account.getApiKey().isEmpty()) {
            account.setApiKey(ApiKeyEncryptUtil.encrypt(account.getApiKey()));
        }
        if (account.getApiSecret() != null && !account.getApiSecret().isEmpty()) {
            account.setApiSecret(ApiKeyEncryptUtil.encrypt(account.getApiSecret()));
        }
        if (account.getPassphrase() != null && !account.getPassphrase().isEmpty()) {
            account.setPassphrase(ApiKeyEncryptUtil.encrypt(account.getPassphrase()));
        }

        return AccountMapper.insertAccount(account);
    }

    /**
     * 修改账户
     * 
     * @param account 账户
     * @return 结果
     */
    @Override
    public int updateAccount(Account account)
    {
        // 只能修改自己的账户
        Account existAccount = AccountMapper.selectAccountById(account.getId());
        if (existAccount == null || !existAccount.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限修改此账户");
        }

        // 如果传入了新的 API Key，则加密
        if (account.getApiKey() != null && !account.getApiKey().isEmpty()) {
            account.setApiKey(ApiKeyEncryptUtil.encrypt(account.getApiKey()));
        }
        if (account.getApiSecret() != null && !account.getApiSecret().isEmpty()) {
            account.setApiSecret(ApiKeyEncryptUtil.encrypt(account.getApiSecret()));
        }
        if (account.getPassphrase() != null && !account.getPassphrase().isEmpty()) {
            account.setPassphrase(ApiKeyEncryptUtil.encrypt(account.getPassphrase()));
        }

        return AccountMapper.updateAccount(account);
    }

    /**
     * 批量删除账户
     * 
     * @param ids 需要删除的账户主键
     * @return 结果
     */
    @Override
    public int deleteAccountByIds(Long[] ids)
    {
        for (Long id : ids) {
            deleteAccountById(id);
        }
        return ids.length;
    }

    /**
     * 删除账户信息
     * 
     * @param id 账户主键
     * @return 结果
     */
    @Override
    public int deleteAccountById(Long id)
    {
        // 只能删除自己的账户
        Account account = AccountMapper.selectAccountById(id);
        if (account == null || !account.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限删除此账户");
        }
        return AccountMapper.deleteAccountById(id);
    }

    /**
     * 获取解密后的账户信息（供内部使用）
     */
    public Account getDecryptedAccount(Long id) {
        Account account = AccountMapper.selectAccountById(id);
        if (account != null) {
            account.setApiKey(ApiKeyEncryptUtil.decrypt(account.getApiKey()));
            account.setApiSecret(ApiKeyEncryptUtil.decrypt(account.getApiSecret()));
            if (account.getPassphrase() != null) {
                account.setPassphrase(ApiKeyEncryptUtil.decrypt(account.getPassphrase()));
            }
        }
        return account;
    }
}
