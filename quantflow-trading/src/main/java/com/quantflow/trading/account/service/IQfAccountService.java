package com.quantflow.trading.account.service;

import java.util.List;
import com.quantflow.trading.account.domain.QfAccount;

/**
 * 账户Service接口
 * 
 * @author quantflow
 * @date 2025-10-31
 */
public interface IQfAccountService 
{
    /**
     * 查询账户
     * 
     * @param id 账户主键
     * @return 账户
     */
    public QfAccount selectQfAccountById(Long id);

    /**
     * 查询账户列表
     * 
     * @param qfAccount 账户
     * @return 账户集合
     */
    public List<QfAccount> selectQfAccountList(QfAccount qfAccount);

    /**
     * 新增账户
     * 
     * @param qfAccount 账户
     * @return 结果
     */
    public int insertQfAccount(QfAccount qfAccount);

    /**
     * 修改账户
     * 
     * @param qfAccount 账户
     * @return 结果
     */
    public int updateQfAccount(QfAccount qfAccount);

    /**
     * 批量删除账户
     * 
     * @param ids 需要删除的账户主键集合
     * @return 结果
     */
    public int deleteQfAccountByIds(Long[] ids);

    /**
     * 删除账户信息
     * 
     * @param id 账户主键
     * @return 结果
     */
    public int deleteQfAccountById(Long id);
}
