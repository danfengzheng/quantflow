package com.quantflow.common.exception;

import com.quantflow.common.constant.I18nMessageKey;
import com.quantflow.common.utils.MessageUtils;

/**
 * 业务异常
 * 
 * 使用规范：
 * 1. 【推荐】使用消息键枚举：new ServiceException(MessageKeys.USER_NOT_EXISTS)
 * 2. 【推荐】使用消息键枚举带参数：new ServiceException(MessageKeys.USER_IMPORT_FAILED, failureNum, errorMsg)
 * 3. 【推荐】使用其他模块的消息键：new ServiceException(TradingMessageKeys.ORDER_FAILED)
 * 4. 【不推荐】直接传入消息：new ServiceException("错误消息") - 已标记为@Deprecated，仅用于向后兼容
 * 
 * @author ruoyi
 */
public final class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     *
     * 和 {@link CommonResult#getDetailMessage()} 一致的设计
     */
    private String detailMessage;

    /**
     * 空构造方法，避免反序列化问题
     */
    public ServiceException()
    {
    }

    /**
     * 构造方法：使用消息键接口（推荐，支持所有实现I18nMessageKey的枚举）
     * 
     * @param messageKey 消息键（实现I18nMessageKey接口）
     * @param args 消息参数
     */
    public ServiceException(I18nMessageKey messageKey, Object... args)
    {
        this.message = MessageUtils.message(messageKey.getKey(), args);
    }

    /**
     * 构造方法：使用消息键接口和错误码（推荐）
     * 
     * @param messageKey 消息键（实现I18nMessageKey接口）
     * @param code 错误码
     * @param args 消息参数
     */
    public ServiceException(I18nMessageKey messageKey, Integer code, Object... args)
    {
        this.message = MessageUtils.message(messageKey.getKey(), args);
        this.code = code;
    }

    /**
     * 构造方法：直接传入消息（已废弃，不推荐使用）
     * 
     * @deprecated 请使用 new ServiceException(MessageKeys.XXX) 替代
     * @param message 错误消息
     */
    @Deprecated
    public ServiceException(String message)
    {
        this.message = message;
    }

    /**
     * 构造方法：直接传入消息和错误码（已废弃，不推荐使用）
     * 
     * @deprecated 请使用 new ServiceException(MessageKeys.XXX, code) 替代
     * @param message 错误消息
     * @param code 错误码
     */
    @Deprecated
    public ServiceException(String message, Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public String getDetailMessage()
    {
        return detailMessage;
    }

    @Override
    public String getMessage()
    {
        return message;
    }

    public Integer getCode()
    {
        return code;
    }

    public ServiceException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public ServiceException setDetailMessage(String detailMessage)
    {
        this.detailMessage = detailMessage;
        return this;
    }
}