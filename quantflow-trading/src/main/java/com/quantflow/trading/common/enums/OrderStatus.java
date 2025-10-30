package com.quantflow.trading.common.enums;

/**
 * 订单状态枚举
 */
public enum OrderStatus {

    PENDING("PENDING", "待提交"),
    SUBMITTED("SUBMITTED", "已提交"),
    PARTIAL_FILLED("PARTIAL_FILLED", "部分成交"),
    FILLED("FILLED", "完全成交"),
    CANCELING("CANCELING", "取消中"),
    CANCELLED("CANCELLED", "已取消"),
    REJECTED("REJECTED", "已拒绝"),
    EXPIRED("EXPIRED", "已过期"),
    FAILED("FAILED", "失败");

    private final String code;
    private final String desc;

    OrderStatus(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}