package com.quantflow.common.core.domain;

import java.util.HashMap;
import java.util.Objects;
import com.quantflow.common.constant.HttpStatus;
import com.quantflow.common.constant.I18nMessageKey;
import com.quantflow.common.constant.MessageKeys;
import com.quantflow.common.utils.MessageUtils;
import com.quantflow.common.utils.StringUtils;

/**
 * 操作消息提醒
 * 
 * @author ruoyi
 */
public class AjaxResult extends HashMap<String, Object>
{
    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    /**
     * 初始化一个新创建的 AjaxResult 对象，使其表示一个空消息。
     */
    public AjaxResult()
    {
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     */
    public AjaxResult(int code, String msg)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    /**
     * 初始化一个新创建的 AjaxResult 对象
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @param data 数据对象
     */
    public AjaxResult(int code, String msg, Object data)
    {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data))
        {
            super.put(DATA_TAG, data);
        }
    }

    /**
     * 返回成功消息
     * 
     * @return 成功消息
     */
    public static AjaxResult success()
    {
        return AjaxResult.success(MessageKeys.OPERATION_SUCCESS);
    }

    /**
     * 返回成功数据
     * 
     * @return 成功消息
     */
    public static AjaxResult success(Object data)
    {
        return AjaxResult.success(MessageKeys.OPERATION_SUCCESS, data, (Object[]) null);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @return 成功消息
     */
    public static AjaxResult success(String msg)
    {
        return AjaxResult.success(msg, null);
    }

    /**
     * 返回成功消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static AjaxResult success(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回成功消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @return 成功消息
     */
    public static AjaxResult success(I18nMessageKey messageKey)
    {
        return AjaxResult.success(messageKey, null, (Object[]) null);
    }

    /**
     * 返回成功消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 成功消息
     */
    public static AjaxResult success(I18nMessageKey messageKey, Object... args)
    {
        return AjaxResult.success(messageKey, null, args);
    }

    /**
     * 返回成功消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param data 数据对象
     * @param args 消息参数
     * @return 成功消息
     */
    public static AjaxResult success(I18nMessageKey messageKey, Object data, Object[] args)
    {
        String msg = args != null && args.length > 0 
            ? MessageUtils.message(messageKey, args) 
            : MessageUtils.message(messageKey);
        return new AjaxResult(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static AjaxResult warn(String msg)
    {
        return AjaxResult.warn(msg, null);
    }

    /**
     * 返回警告消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static AjaxResult warn(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @return 错误消息
     */
    public static AjaxResult error()
    {
        return AjaxResult.error(MessageKeys.OPERATION_FAILED);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(String msg)
    {
        return AjaxResult.error(msg, null);
    }

    /**
     * 返回错误消息
     * 
     * @param msg 返回内容
     * @param data 数据对象
     * @return 错误消息
     */
    public static AjaxResult error(String msg, Object data)
    {
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     * 
     * @param code 状态码
     * @param msg 返回内容
     * @return 错误消息
     */
    public static AjaxResult error(int code, String msg)
    {
        return new AjaxResult(code, msg, null);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @return 错误消息
     */
    public static AjaxResult error(I18nMessageKey messageKey)
    {
        return AjaxResult.error(messageKey, null, (Object[]) null);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 错误消息
     */
    public static AjaxResult error(I18nMessageKey messageKey, Object... args)
    {
        return AjaxResult.error(messageKey, null, args);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param data 数据对象
     * @param args 消息参数
     * @return 错误消息
     */
    public static AjaxResult error(I18nMessageKey messageKey, Object data, Object[] args)
    {
        String msg = args != null && args.length > 0 
            ? MessageUtils.message(messageKey, args) 
            : MessageUtils.message(messageKey);
        return new AjaxResult(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param code 状态码
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 错误消息
     */
    public static AjaxResult error(int code, I18nMessageKey messageKey, Object... args)
    {
        return new AjaxResult(code, MessageUtils.message(messageKey, args), null);
    }

    /**
     * 返回警告消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @return 警告消息
     */
    public static AjaxResult warn(I18nMessageKey messageKey)
    {
        return AjaxResult.warn(messageKey, null, (Object[]) null);
    }

    /**
     * 返回警告消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 警告消息
     */
    public static AjaxResult warn(I18nMessageKey messageKey, Object... args)
    {
        return AjaxResult.warn(messageKey, null, args);
    }

    /**
     * 返回警告消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param data 数据对象
     * @param args 消息参数
     * @return 警告消息
     */
    public static AjaxResult warn(I18nMessageKey messageKey, Object data, Object[] args)
    {
        String msg = args != null && args.length > 0 
            ? MessageUtils.message(messageKey, args) 
            : MessageUtils.message(messageKey);
        return new AjaxResult(HttpStatus.WARN, msg, data);
    }

    /**
     * 是否为成功消息
     *
     * @return 结果
     */
    public boolean isSuccess()
    {
        return Objects.equals(HttpStatus.SUCCESS, this.get(CODE_TAG));
    }

    /**
     * 是否为警告消息
     *
     * @return 结果
     */
    public boolean isWarn()
    {
        return Objects.equals(HttpStatus.WARN, this.get(CODE_TAG));
    }

    /**
     * 是否为错误消息
     *
     * @return 结果
     */
    public boolean isError()
    {
        return Objects.equals(HttpStatus.ERROR, this.get(CODE_TAG));
    }

    /**
     * 方便链式调用
     *
     * @param key 键
     * @param value 值
     * @return 数据对象
     */
    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }
}
