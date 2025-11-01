package com.quantflow.trading.account.service;

import java.util.List;
import com.quantflow.trading.account.domain.Account;

/**
 * 账户Service接口
 * 
 * @author quantflow
 * @date 2025-10-31
 */
public interface IAccountService 
{
    /**
     * 查询账户
     * 
     * @param id 账户主键
     * @return 账户
     */
    public Account selectAccountById(Long id);

    /**
     * 查询账户列表
     * 
     * @param account 账户
     * @return 账户集合
     */
    public List<Account> selectAccountList(Account account);

    /**
     * 新增账户
     * 
     * @param account 账户
     * @return 结果
     */
    public int insertAccount(Account account);

    /**
     * 修改账户
     * 
     * @param account 账户
     * @return 结果
     */
    public int updateAccount(Account account);

    /**
     * 批量删除账户
     * 
     * @param ids 需要删除的账户主键集合
     * @return 结果
     */
    public int deleteAccountByIds(Long[] ids);

    /**
     * 删除账户信息
     * 
     * @param id 账户主键
     * @return 结果
     */
    public int deleteAccountById(Long id);


    /**
     * 获取解密后的账户信息（供内部使用）
     */
    public Account getDecryptedAccount(Long id);
}
