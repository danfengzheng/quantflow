package com.quantflow.trading.account.domain;

import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 账户对象 qf_account
 * 
 * @author quantflow
 * @date 2025-10-31
 */
@Data
public class QfAccount extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 账户ID */
    private Long id;

    /** 用户ID */
    private Long userId;

    /** 账户名称 */
    @Excel(name = "账户名称")
    private String accountName;

    /** 交易所 */
    @Excel(name = "交易所")
    private String exchange;

    /** API Key */
    private String apiKey;

    /** API Secret */
    private String apiSecret;

    /** Passphrase */
    private String passphrase;

    /** 是否测试网：0否 1是 */
    @Excel(name = "是否测试网：0否 1是")
    private Integer isTestnet;

    /** 状态：0禁用 1启用 */
    @Excel(name = "状态：0禁用 1启用")
    private Integer status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("accountName", getAccountName())
            .append("exchange", getExchange())
            .append("apiKey", getApiKey())
            .append("apiSecret", getApiSecret())
            .append("passphrase", getPassphrase())
            .append("isTestnet", getIsTestnet())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
