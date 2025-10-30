package com.quantflow.trading.common.config;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * 缓存配置
 */
@Configuration
public class CacheConfig {

    /**
     * 行情数据缓存（短期，5秒过期）
     */
    @Bean("marketDataCache")
    public Cache<String, Object> marketDataCache() {
        return Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(5, TimeUnit.SECONDS)
                .recordStats()
                .build();
    }

    /**
     * 策略状态缓存（长期，5分钟过期）
     */
    @Bean("strategyCache")
    public Cache<String, Object> strategyCache() {
        return Caffeine.newBuilder()
                .maximumSize(500)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .recordStats()
                .build();
    }

    /**
     * 账户持仓缓存（中期，30秒过期）
     */
    @Bean("accountCache")
    public Cache<String, Object> accountCache() {
        return Caffeine.newBuilder()
                .maximumSize(200)
                .expireAfterWrite(30, TimeUnit.SECONDS)
                .recordStats()
                .build();
    }
}