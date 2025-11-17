package com.quantflow.framework.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import com.quantflow.common.constant.Constants;

import java.util.Locale;

/**
 * 自定义LocaleResolver
 * 优先级：1. 请求头Accept-Language 2. 配置文件中的默认语言 3. 系统默认语言（zh-CN）
 * 
 * 支持的语言：
 * - 简体中文 (zh-CN) - 默认语言
 * - 繁体中文 (zh-TW)
 * - 英文 (en-US)
 * - 日语 (ja-JP)
 * 
 * 支持的语言代码格式：
 * - 完整格式：zh-CN, zh-TW, en-US, ja-JP
 * - 简化格式：zh (映射到zh-CN), en (映射到en-US), ja (映射到ja-JP)
 * 
 * 配置方式：
 * 在 application.yml 中配置：
 * i18n:
 *   default-locale: zh-CN  # 可选值：zh-CN, zh-TW, en-US, ja-JP
 * 
 * @author quantflow
 */
@Component
public class CustomLocaleResolver implements LocaleResolver {

    /**
     * 默认语言（从配置文件读取，如果未配置则使用系统默认）
     */
    private final Locale defaultLocale;

    /**
     * 构造函数：从配置文件读取默认语言
     * 
     * @param defaultLocaleStr 配置文件中设置的默认语言字符串（如：zh-CN）
     */
    public CustomLocaleResolver(@Value("${i18n.default-locale:zh-CN}") String defaultLocaleStr) {
        this.defaultLocale = parseLocaleFromString(defaultLocaleStr);
    }

    /**
     * 语言请求头名称
     */
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    @Override
    @NonNull
    public Locale resolveLocale(@NonNull HttpServletRequest request) {
        // 1. 优先从请求头Accept-Language读取
        String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE_HEADER);
        
        if (StringUtils.hasText(acceptLanguage)) {
            // 解析Accept-Language头，格式如：zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7
            Locale locale = parseAcceptLanguage(acceptLanguage);
            if (locale != null) {
                return locale;
            }
        }

        // 2. 如果没有Accept-Language或解析失败，使用配置的默认语言
        return defaultLocale != null ? defaultLocale : Constants.DEFAULT_LOCALE;
    }

    @Override
    public void setLocale(@NonNull HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale) {
        // 不支持通过setLocale设置，因为我们的策略是基于请求头的
        // 如果需要切换语言，可以通过LocaleChangeInterceptor的lang参数
    }

    /**
     * 解析Accept-Language请求头
     * 支持格式：zh-CN, zh-CN;q=0.9, en-US;q=0.8, en;q=0.7
     * 支持的语言：简体中文(zh-CN)、繁体中文(zh-TW)、英文(en-US)、日语(ja-JP)
     * 
     * @param acceptLanguage Accept-Language请求头值
     * @return Locale对象，如果解析失败返回null
     */
    private Locale parseAcceptLanguage(String acceptLanguage) {
        if (!StringUtils.hasText(acceptLanguage)) {
            return null;
        }

        try {
            // 按逗号分割，取第一个（优先级最高的）
            String[] languages = acceptLanguage.split(",");
            if (languages.length > 0) {
                String firstLanguage = languages[0].trim();
                
                // 移除质量值（q=0.9等）
                if (firstLanguage.contains(";")) {
                    firstLanguage = firstLanguage.split(";")[0].trim();
                }

                // 解析语言代码，支持格式：zh-CN, zh-TW, en-US, ja-JP, zh, en, ja等
                if (firstLanguage.contains("-") || firstLanguage.contains("_")) {
                    String[] parts = firstLanguage.split("[-_]");
                    if (parts.length == 2) {
                        String language = parts[0].toLowerCase();
                        String country = parts[1].toUpperCase();
                        return new Locale(language, country);
                    }
                } else {
                    // 只有语言代码，如：zh, en, ja
                    // 进行语言映射：zh -> zh-CN, en -> en-US, ja -> ja-JP
                    String language = firstLanguage.toLowerCase();
                    return mapLanguageToLocale(language);
                }
            }
        } catch (Exception e) {
            // 解析失败，返回null，将使用默认语言
            return null;
        }

        return null;
    }

    /**
     * 将语言代码映射到Locale
     * 支持的语言映射：
     * - zh -> zh-CN (简体中文)
     * - en -> en-US (英文)
     * - ja -> ja-JP (日语)
     * 
     * @param language 语言代码（小写）
     * @return Locale对象
     */
    private Locale mapLanguageToLocale(String language) {
        switch (language) {
            case "zh":
                // 默认映射到简体中文
                return Locale.SIMPLIFIED_CHINESE;
            case "en":
                return Locale.US;
            case "ja":
                return Locale.JAPAN;
            default:
                return new Locale(language);
        }
    }

    /**
     * 从字符串解析Locale对象
     * 支持格式：zh-CN, zh_TW, en-US, ja-JP, zh, en, ja
     * 
     * @param localeStr 语言字符串
     * @return Locale对象，如果解析失败返回系统默认Locale
     */
    private Locale parseLocaleFromString(String localeStr) {
        if (!StringUtils.hasText(localeStr)) {
            return Constants.DEFAULT_LOCALE;
        }

        try {
            String trimmed = localeStr.trim();
            
            // 如果包含分隔符（-或_），解析为完整格式
            if (trimmed.contains("-") || trimmed.contains("_")) {
                String[] parts = trimmed.split("[-_]");
                if (parts.length == 2) {
                    String language = parts[0].toLowerCase();
                    String country = parts[1].toUpperCase();
                    return new Locale(language, country);
                }
            } else {
                // 只有语言代码，进行映射
                return mapLanguageToLocale(trimmed.toLowerCase());
            }
        } catch (Exception e) {
            // 解析失败，返回系统默认
            return Constants.DEFAULT_LOCALE;
        }

        // 解析失败，返回系统默认
        return Constants.DEFAULT_LOCALE;
    }
}

