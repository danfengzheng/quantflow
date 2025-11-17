package com.quantflow.common.constant;

/**
 * 国际化消息键接口
 * 所有多语言消息键都应该实现此接口，便于统一管理和扩展
 * 
 * 使用场景：
 * 1. 系统核心模块：MessageKeys（已实现）
 * 2. 交易模块：TradingMessageKeys（可扩展）
 * 3. 其他业务模块：各自实现此接口
 * 
 * @author quantflow
 */
public interface I18nMessageKey {
    
    /**
     * 获取消息键（数字编码）
     * 用于在国际化资源文件中查找对应的消息
     * 
     * @return 消息键字符串（数字编码）
     */
    String getKey();
    
    /**
     * 获取中文说明（仅用于后端调试和代码可读性）
     * 注意：此方法返回的中文说明不会参与实际的消息返回
     * 实际返回的消息由国际化资源文件根据key和当前Locale决定
     * 
     * @return 中文说明
     */
    String getMsg();
    
    /**
     * 获取消息键（别名方法，更简洁）
     * 
     * @return 消息键字符串（数字编码）
     */
    default String key() {
        return getKey();
    }
}

