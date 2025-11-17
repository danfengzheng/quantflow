package com.quantflow.common.utils;

import java.util.Locale;
import org.springframework.context.i18n.LocaleContextHolder;
import com.quantflow.common.constant.Constants;

/**
 * 国际化工具类
 * 
 * @author quantflow
 */
public class I18nUtils
{
    /**
     * 获取当前语言代码（如：zh-CN）
     * 
     * @return 语言代码字符串
     */
    public static String getCurrentLocale()
    {
        Locale locale = LocaleContextHolder.getLocale();
        if (locale == null)
        {
            locale = Constants.DEFAULT_LOCALE;
        }
        return localeToString(locale);
    }

    /**
     * 将 Locale 转换为字符串格式（如：zh-CN）
     * 
     * @param locale Locale 对象
     * @return 语言代码字符串
     */
    public static String localeToString(Locale locale)
    {
        if (locale == null)
        {
            return "zh-CN";
        }
        String language = locale.getLanguage();
        String country = locale.getCountry();
        if (StringUtils.isNotEmpty(country))
        {
            return language + "-" + country;
        }
        return language;
    }
}

