package com.quantflow.trading.account.service.impl;

import java.util.List;

import com.quantflow.common.utils.DateUtils;
import com.quantflow.common.utils.SecurityUtils;
import com.quantflow.trading.common.utils.ApiKeyEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quantflow.trading.account.mapper.QfAccountMapper;
import com.quantflow.trading.account.domain.QfAccount;
import com.quantflow.trading.account.service.IQfAccountService;

/**
 * 账户Service业务层处理
 * 
 * @author quantflow
 * @date 2025-10-31
 */
@Service
public class QfAccountServiceImpl implements IQfAccountService 
{
    @Autowired
    private QfAccountMapper qfAccountMapper;

    /**
     * 查询账户
     * 
     * @param id 账户主键
     * @return 账户
     */
    @Override
    public QfAccount selectQfAccountById(Long id)
    {
        QfAccount account = qfAccountMapper.selectQfAccountById(id);
        if (account != null) {
            // 解密（如果需要在前端显示部分信息）
            // 这里我们不解密，使用脱敏方法
        }
        return account;
    }

    /**
     * 查询账户列表
     * 
     * @param qfAccount 账户
     * @return 账户
     */
    @Override
    public List<QfAccount> selectQfAccountList(QfAccount qfAccount)
    {
        return qfAccountMapper.selectQfAccountList(qfAccount);
    }

    /**
     * 新增账户
     * 
     * @param account 账户
     * @return 结果
     */
    @Override
    public int insertQfAccount(QfAccount account)
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

        return qfAccountMapper.insertQfAccount(account);
    }

    /**
     * 修改账户
     * 
     * @param account 账户
     * @return 结果
     */
    @Override
    public int updateQfAccount(QfAccount account)
    {
        // 只能修改自己的账户
        QfAccount existAccount = qfAccountMapper.selectQfAccountById(account.getId());
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

        return qfAccountMapper.updateQfAccount(account);
    }

    /**
     * 批量删除账户
     * 
     * @param ids 需要删除的账户主键
     * @return 结果
     */
    @Override
    public int deleteQfAccountByIds(Long[] ids)
    {
        for (Long id : ids) {
            deleteQfAccountById(id);
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
    public int deleteQfAccountById(Long id)
    {
        // 只能删除自己的账户
        QfAccount account = qfAccountMapper.selectQfAccountById(id);
        if (account == null || !account.getUserId().equals(SecurityUtils.getUserId())) {
            throw new RuntimeException("无权限删除此账户");
        }
        return qfAccountMapper.deleteQfAccountById(id);
    }

    /**
     * 获取解密后的账户信息（供内部使用）
     */
    public QfAccount getDecryptedAccount(Long id) {
        QfAccount account = qfAccountMapper.selectQfAccountById(id);
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
