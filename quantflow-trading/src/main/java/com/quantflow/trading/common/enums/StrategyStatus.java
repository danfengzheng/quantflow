package com.quantflow.trading.common.enums;

/**
 * 策略状态枚举
 */
public enum StrategyStatus {

    STOPPED(0, "已停止"),
    RUNNING(1, "运行中"),
    PAUSED(2, "已暂停"),
    ERROR(3, "异常");

    private final Integer code;
    private final String desc;

    StrategyStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}