package com.quantflow.trading.account.mapper;

import java.util.List;
import com.quantflow.trading.account.domain.Account;

/**
 * 账户Mapper接口
 * 
 * @author quantflow
 * @date 2025-10-31
 */
public interface AccountMapper 
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
     * 删除账户
     * 
     * @param id 账户主键
     * @return 结果
     */
    public int deleteAccountById(Long id);

    /**
     * 批量删除账户
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteAccountByIds(Long[] ids);
}
