package com.quantflow.trading.account.service.impl;

import java.util.List;

import com.quantflow.common.utils.DateUtils;
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
        return qfAccountMapper.selectQfAccountById(id);
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
     * @param qfAccount 账户
     * @return 结果
     */
    @Override
    public int insertQfAccount(QfAccount qfAccount)
    {
        qfAccount.setCreateTime(DateUtils.getNowDate());
        return qfAccountMapper.insertQfAccount(qfAccount);
    }

    /**
     * 修改账户
     * 
     * @param qfAccount 账户
     * @return 结果
     */
    @Override
    public int updateQfAccount(QfAccount qfAccount)
    {
        qfAccount.setUpdateTime(DateUtils.getNowDate());
        return qfAccountMapper.updateQfAccount(qfAccount);
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
        return qfAccountMapper.deleteQfAccountByIds(ids);
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
        return qfAccountMapper.deleteQfAccountById(id);
    }
}
