package com.quantflow.common.core.domain;

import java.io.Serializable;
import com.quantflow.common.constant.HttpStatus;
import com.quantflow.common.constant.I18nMessageKey;
import com.quantflow.common.constant.MessageKeys;
import com.quantflow.common.utils.MessageUtils;

/**
 * 响应信息主体
 *
 * @author ruoyi
 */
public class R<T> implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = HttpStatus.SUCCESS;

    /** 失败 */
    public static final int FAIL = HttpStatus.ERROR;

    private int code;

    private String msg;

    private T data;

    public static <T> R<T> ok()
    {
        return restResult(null, SUCCESS, MessageUtils.message(MessageKeys.OPERATION_SUCCESS));
    }

    public static <T> R<T> ok(T data)
    {
        return restResult(data, SUCCESS, MessageUtils.message(MessageKeys.OPERATION_SUCCESS));
    }

    public static <T> R<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> R<T> fail()
    {
        return restResult(null, FAIL, MessageUtils.message(MessageKeys.OPERATION_FAILED));
    }

    public static <T> R<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> R<T> fail(T data)
    {
        return restResult(data, FAIL, MessageUtils.message(MessageKeys.OPERATION_FAILED));
    }

    public static <T> R<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> R<T> fail(int code, String msg)
    {
        return restResult(null, code, msg);
    }

    /**
     * 返回成功消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @return 成功消息
     */
    public static <T> R<T> ok(I18nMessageKey messageKey)
    {
        return ok(null, messageKey);
    }

    /**
     * 返回成功消息（支持多语言）
     * 
     * @param data 数据对象
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 成功消息
     */
    public static <T> R<T> ok(T data, I18nMessageKey messageKey, Object... args)
    {
        String msg = args != null && args.length > 0 
            ? MessageUtils.message(messageKey, args) 
            : MessageUtils.message(messageKey);
        return restResult(data, SUCCESS, msg);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 错误消息
     */
    public static <T> R<T> fail(I18nMessageKey messageKey, Object... args)
    {
        return fail(null, messageKey, args);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param data 数据对象
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 错误消息
     */
    public static <T> R<T> fail(T data, I18nMessageKey messageKey, Object... args)
    {
        String msg = args != null && args.length > 0 
            ? MessageUtils.message(messageKey, args) 
            : MessageUtils.message(messageKey);
        return restResult(data, FAIL, msg);
    }

    /**
     * 返回错误消息（支持多语言）
     * 
     * @param code 状态码
     * @param messageKey 消息键
     * @param args 消息参数
     * @return 错误消息
     */
    public static <T> R<T> fail(int code, I18nMessageKey messageKey, Object... args)
    {
        String msg = args != null && args.length > 0 
            ? MessageUtils.message(messageKey, args) 
            : MessageUtils.message(messageKey);
        return restResult(null, code, msg);
    }

    private static <T> R<T> restResult(T data, int code, String msg)
    {
        R<T> apiResult = new R<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public T getData()
    {
        return data;
    }

    public void setData(T data)
    {
        this.data = data;
    }

    public static <T> Boolean isError(R<T> ret)
    {
        return !isSuccess(ret);
    }

    public static <T> Boolean isSuccess(R<T> ret)
    {
        return R.SUCCESS == ret.getCode();
    }
}
