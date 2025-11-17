package com.quantflow.common.utils;

import com.quantflow.common.constant.I18nMessageKey;
import com.quantflow.common.constant.Constants;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import com.quantflow.common.utils.spring.SpringUtils;

import java.util.Locale;

/**
 * 获取i18n资源文件
 * 
 * @author ruoyi
 */
public class MessageUtils
{
    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param code 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(String code, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        Locale locale = LocaleContextHolder.getLocale();
        // 如果LocaleContextHolder中没有Locale，使用默认Locale
        if (locale == null) {
            locale = Constants.DEFAULT_LOCALE;
            // 同时设置到LocaleContextHolder，以便后续使用
            LocaleContextHolder.setLocale(locale);
        }
        try {
            return messageSource.getMessage(code, args, locale);
        } catch (org.springframework.context.NoSuchMessageException e) {
            // 如果找不到消息，尝试使用默认Locale
            if (!locale.equals(Constants.DEFAULT_LOCALE)) {
                try {
                    return messageSource.getMessage(code, args, Constants.DEFAULT_LOCALE);
                } catch (org.springframework.context.NoSuchMessageException ex) {
                    // 如果默认Locale也找不到，返回消息键本身
                    return code;
                }
            } else {
                // 如果已经是默认Locale还找不到，返回消息键本身
                return code;
            }
        }
    }

    /**
     * 根据消息键和参数 获取消息 委托给spring messageSource
     *
     * @param messageKey 消息键
     * @param args 参数
     * @return 获取国际化翻译值
     */
    public static String message(I18nMessageKey messageKey, Object... args)
    {
        MessageSource messageSource = SpringUtils.getBean(MessageSource.class);
        Locale locale = LocaleContextHolder.getLocale();
        // 如果LocaleContextHolder中没有Locale，使用默认Locale
        if (locale == null) {
            locale = Constants.DEFAULT_LOCALE;
            // 同时设置到LocaleContextHolder，以便后续使用
            LocaleContextHolder.setLocale(locale);
        }
        try {
            return messageSource.getMessage(messageKey.getKey(), args, locale);
        } catch (org.springframework.context.NoSuchMessageException e) {
            // 如果找不到消息，尝试使用默认Locale
            if (!locale.equals(Constants.DEFAULT_LOCALE)) {
                try {
                    return messageSource.getMessage(messageKey.getKey(), args, Constants.DEFAULT_LOCALE);
                } catch (org.springframework.context.NoSuchMessageException ex) {
                    // 如果默认Locale也找不到，返回消息键本身
                    return messageKey.getKey();
                }
            } else {
                // 如果已经是默认Locale还找不到，返回消息键本身
                return messageKey.getKey();
            }
        }
    }
}
