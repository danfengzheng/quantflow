package com.quantflow.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 资源文件配置加载
 * 支持多语言：优先级1. 请求头Accept-Language 2. 默认语言配置
 * 
 * 支持的语言：
 * - 简体中文 (zh-CN) - 默认语言
 * - 繁体中文 (zh-TW)
 * - 英文 (en-US)
 * - 日语 (ja-JP)
 * 
 * 使用方式：
 * 1. 通过请求头：Accept-Language: zh-CN, en-US, ja-JP, zh-TW
 * 2. 通过URL参数：?lang=zh-CN, ?lang=en-US, ?lang=ja-JP, ?lang=zh-TW
 * 
 * @author ruoyi
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer
{
    /**
     * 自定义LocaleResolver
     * 优先从请求头Accept-Language读取语言，否则使用配置文件中的默认语言
     * 
     * 配置方式：在 application.yml 中设置 i18n.default-locale
     * 支持的值：zh-CN（简体中文）、zh-TW（繁体中文）、en-US（英文）、ja-JP（日语）
     * 默认值：zh-CN
     */
    @Bean
    public LocaleResolver localeResolver(CustomLocaleResolver customLocaleResolver)
    {
        return customLocaleResolver;
    }

    /**
     * 语言切换拦截器
     * 支持通过lang参数切换语言（如：?lang=en-US）
     * 优先级高于Accept-Language请求头
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor()
    {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry)
    {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
