package com.quantflow.trading.strategy.domain;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.quantflow.common.annotation.Excel;
import com.quantflow.common.core.domain.BaseEntity;

/**
 * 策略对象 qf_strategy
 * 
 * @author quantflow
 * @date 2025-11-01
 */
@Data
public class Strategy extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 策略ID */
    @Excel(name = "策略ID")
    private Long id;

    /** 策略名称 */
    @Excel(name = "策略名称")
    private String name;

    /** 策略代码 */
    @Excel(name = "策略代码")
    private String code;

    /** 策略类型 */
    @Excel(name = "策略类型")
    private String type;

    /** 策略描述 */
    private String description;

    /** 交易账户ID */
    @Excel(name = "交易账户ID")
    private Long accountId;

    /** 交易对 */
    @Excel(name = "交易对")
    private String symbol;

    /** K线周期 */
    @Excel(name = "K线周期")
    private String interval;

    /** 策略参数 */
    private String paramsConfig;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    /** 创建用户ID */
    @Excel(name = "创建用户ID")
    private Long userId;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("code", getCode())
            .append("type", getType())
            .append("description", getDescription())
            .append("accountId", getAccountId())
            .append("symbol", getSymbol())
            .append("interval", getInterval())
            .append("params", getParams())
            .append("status", getStatus())
            .append("userId", getUserId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
