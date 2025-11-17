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
    /** 策略已在运行中 */
    STRATEGY_ALREADY_RUNNING("22004", "策略已在运行中"),
    /** 策略未在运行 */
    STRATEGY_NOT_RUNNING("22005", "策略未在运行"),
    /** 策略已启动 */
    STRATEGY_STARTED("22006", "策略已启动"),
    /** 策略已停止 */
    STRATEGY_STOPPED("22007", "策略已停止"),
    /** 策略执行完成 */
    STRATEGY_EXECUTE_COMPLETED("22008", "策略执行完成"),
    /** 执行策略失败：{0} */
    STRATEGY_EXECUTE_FAILED_WITH_MSG("22009", "执行策略失败：{0}"),
    
    // ========== 账户相关 (23000-23999) ==========
    /** 账户不存在 */
    ACCOUNT_NOT_FOUND("23001", "账户不存在"),
    /** 账户已禁用 */
    ACCOUNT_DISABLED("23002", "账户已禁用"),
    /** API密钥无效 */
    API_KEY_INVALID("23003", "API密钥无效"),
    /** 测试连接成功 */
    ACCOUNT_TEST_CONNECTION_SUCCESS("23004", "测试连接成功"),
    /** 测试连接失败：{0} */
    ACCOUNT_TEST_CONNECTION_FAILED("23005", "测试连接失败：{0}"),
    /** 获取余额失败：{0} */
    ACCOUNT_GET_BALANCE_FAILED("23006", "获取余额失败：{0}"),
    
    // ========== 回测相关 (24000-24999) ==========
    /** 回测任务不存在 */
    BACKTEST_NOT_FOUND("24001", "回测任务不存在"),
    /** 回测任务执行失败 */
    BACKTEST_EXECUTE_FAILED("24002", "回测任务执行失败"),
    /** 回测数据不足 */
    BACKTEST_DATA_INSUFFICIENT("24003", "回测数据不足"),
    /** 回测任务已启动 */
    BACKTEST_STARTED("24004", "回测任务已启动"),
    /** 启动回测失败：{0} */
    BACKTEST_START_FAILED("24005", "启动回测失败：{0}"),
    /** 回测结果不存在 */
    BACKTEST_RESULT_NOT_FOUND("24006", "回测结果不存在"),
    /** 获取回测结果失败：{0} */
    BACKTEST_GET_RESULT_FAILED("24007", "获取回测结果失败：{0}"),
    
    // ========== 行情相关 (25000-25999) ==========
    /** 获取行情失败：{0} */
    MARKET_GET_TICKER_FAILED("25001", "获取行情失败：{0}"),
    /** 批量获取行情失败：{0} */
    MARKET_GET_TICKERS_FAILED("25002", "批量获取行情失败：{0}"),
    /** 获取K线数据失败：{0} */
    MARKET_GET_KLINES_FAILED("25003", "获取K线数据失败：{0}"),
    
    // ========== 统计相关 (26000-26999) ==========
    /** 获取交易统计失败：{0} */
    STATISTICS_GET_TRADING_STATISTICS_FAILED("26001", "获取交易统计失败：{0}"),
    
    // ========== 仪表盘相关 (27000-27999) ==========
    /** 获取仪表盘数据失败：{0} */
    DASHBOARD_GET_DATA_FAILED("27001", "获取仪表盘数据失败：{0}");
    
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

