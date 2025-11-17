package com.quantflow.common.constant;

/**
 * 交易模块国际化消息键枚举（示例）
 * 
 * 编码规则：
 * - 20000-20999: 交易相关错误
 * - 21000-21999: 订单相关
 * - 22000-22999: 策略相关
 * - 23000-23999: 账户相关
 * - 24000-24999: 回测相关
 * 
 * 使用示例：
 * throw new ServiceException(TradingMessageKeys.ORDER_FAILED, orderId);
 * 
 * @author quantflow
 */
public enum TradingMessageKeys implements I18nMessageKey {
    
    // ========== 交易相关错误 (20000-20999) ==========
    /** 交易所连接失败 */
    EXCHANGE_CONNECTION_FAILED("20001", "交易所连接失败"),
    /** 交易对不存在 */
    SYMBOL_NOT_FOUND("20002", "交易对不存在"),
    /** 账户余额不足 */
    INSUFFICIENT_BALANCE("20003", "账户余额不足"),
    
    // ========== 订单相关 (21000-21999) ==========
    /** 订单创建失败 */
    ORDER_CREATE_FAILED("21001", "订单创建失败"),
    /** 订单不存在 */
    ORDER_NOT_FOUND("21002", "订单不存在"),
    /** 订单已取消 */
    ORDER_ALREADY_CANCELLED("21003", "订单已取消"),
    /** 订单执行失败：{0} */
    ORDER_EXECUTE_FAILED("21004", "订单执行失败：{0}"),
    
    // ========== 策略相关 (22000-22999) ==========
    /** 策略不存在 */
    STRATEGY_NOT_FOUND("22001", "策略不存在"),
    /** 策略已停止 */
    STRATEGY_ALREADY_STOPPED("22002", "策略已停止"),
    /** 策略执行失败 */
    STRATEGY_EXECUTE_FAILED("22003", "策略执行失败"),
    
    // ========== 账户相关 (23000-23999) ==========
    /** 账户不存在 */
    ACCOUNT_NOT_FOUND("23001", "账户不存在"),
    /** 账户已禁用 */
    ACCOUNT_DISABLED("23002", "账户已禁用"),
    /** API密钥无效 */
    API_KEY_INVALID("23003", "API密钥无效"),
    
    // ========== 回测相关 (24000-24999) ==========
    /** 回测任务不存在 */
    BACKTEST_NOT_FOUND("24001", "回测任务不存在"),
    /** 回测任务执行失败 */
    BACKTEST_EXECUTE_FAILED("24002", "回测任务执行失败"),
    /** 回测数据不足 */
    BACKTEST_DATA_INSUFFICIENT("24003", "回测数据不足");
    
    /**
     * 消息键（数字编码），用于国际化资源文件查找
     */
    private final String key;
    
    /**
     * 中文说明（仅用于后端调试和代码可读性，不参与实际消息返回）
     */
    private final String msg;
    
    TradingMessageKeys(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
    
    @Override
    public String getKey() {
        return key;
    }
    
    @Override
    public String getMsg() {
        return msg;
    }
}

