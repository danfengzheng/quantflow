package com.quantflow.system.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.quantflow.common.constant.CacheConstants;
import com.quantflow.common.constant.Constants;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;
import com.quantflow.common.core.redis.RedisCache;
import com.quantflow.common.utils.StringUtils;
import com.quantflow.system.mapper.SysI18nTranslationMapper;
import com.quantflow.system.service.ISysI18nTranslationService;

/**
 * 多语言翻译 业务层处理
 * 
 * @author quantflow
 */
@Service
public class SysI18nTranslationServiceImpl implements ISysI18nTranslationService
{
    @Autowired
    private SysI18nTranslationMapper translationMapper;

    @Autowired
    private RedisCache redisCache;

    /**
     * 缓存过期时间（秒），默认7天
     */
    private static final long CACHE_EXPIRE_TIME = 7 * 24 * 60 * 60;

    /**
     * 查询多语言翻译列表
     */
    @Override
    public List<SysI18nTranslation> selectI18nTranslationList(SysI18nTranslation translation)
    {
        return translationMapper.selectI18nTranslationList(translation);
    }

    /**
     * 根据实体类型、实体ID、字段名和语言代码查询翻译
     * 如果找不到指定语言的翻译，会回退到默认语言（zh-CN）
     */
    @Override
    public String getTranslation(String entityType, Long entityId, String fieldName, String locale)
    {
        if (StringUtils.isEmpty(entityType) || entityId == null || StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(locale))
        {
            return null;
        }

        // 先从 Redis 缓存获取
        String cacheKey = getCacheKey(entityType, entityId, fieldName, locale);
        String cachedTranslation = redisCache.getCacheObject(cacheKey);
        if (cachedTranslation != null)
        {
            return cachedTranslation;
        }

        // 缓存未命中，从数据库查询
        String translation = getTranslationFromDb(entityType, entityId, fieldName, locale);
        
        // 如果找到翻译，写入缓存
        if (translation != null)
        {
            redisCache.setCacheObject(cacheKey, translation, (int) CACHE_EXPIRE_TIME, TimeUnit.SECONDS);
        }

        return translation;
    }

    /**
     * 从数据库查询翻译（不经过缓存）
     */
    private String getTranslationFromDb(String entityType, Long entityId, String fieldName, String locale)
    {
        // 先尝试获取指定语言的翻译
        SysI18nTranslation translation = translationMapper.selectI18nTranslation(entityType, entityId, fieldName, locale);
        if (translation != null && StringUtils.isNotEmpty(translation.getTranslation()))
        {
            return translation.getTranslation();
        }

        // 如果找不到，且不是默认语言，尝试获取默认语言的翻译
        String defaultLocaleStr = localeToString(Constants.DEFAULT_LOCALE);
        if (!defaultLocaleStr.equals(locale))
        {
            translation = translationMapper.selectI18nTranslation(entityType, entityId, fieldName, defaultLocaleStr);
            if (translation != null && StringUtils.isNotEmpty(translation.getTranslation()))
            {
                return translation.getTranslation();
            }
        }

        return null;
    }

    /**
     * 根据实体类型、实体ID和语言代码查询所有字段的翻译
     */
    @Override
    public Map<String, String> getTranslationsByEntity(String entityType, Long entityId, String locale)
    {
        if (StringUtils.isEmpty(entityType) || entityId == null || StringUtils.isEmpty(locale))
        {
            return new HashMap<>();
        }

        // 先从 Redis 缓存获取（使用实体级别的缓存 key）
        String entityCacheKey = getEntityCacheKey(entityType, entityId, locale);
        Map<String, String> cachedTranslations = redisCache.getCacheMap(entityCacheKey);
        if (cachedTranslations != null && !cachedTranslations.isEmpty())
        {
            return cachedTranslations;
        }

        // 缓存未命中，从数据库查询
        List<SysI18nTranslation> translations = translationMapper.selectI18nTranslationsByEntity(entityType, entityId, locale);
        
        // 如果找不到指定语言的翻译，尝试获取默认语言的翻译
        String defaultLocaleStr = localeToString(Constants.DEFAULT_LOCALE);
        if (translations.isEmpty() && !defaultLocaleStr.equals(locale))
        {
            translations = translationMapper.selectI18nTranslationsByEntity(entityType, entityId, defaultLocaleStr);
        }

        Map<String, String> result = translations.stream()
            .collect(Collectors.toMap(
                SysI18nTranslation::getFieldName,
                SysI18nTranslation::getTranslation,
                (existing, replacement) -> existing
            ));

        // 写入缓存
        if (!result.isEmpty())
        {
            redisCache.setCacheMap(entityCacheKey, result);
            redisCache.expire(entityCacheKey, CACHE_EXPIRE_TIME);
        }

        return result;
    }

    /**
     * 批量获取翻译（用于批量加载，提高性能）
     */
    @Override
    public Map<Long, String> getTranslationsBatch(String entityType, List<Long> entityIds, String fieldName, String locale)
    {
        if (StringUtils.isEmpty(entityType) || entityIds == null || entityIds.isEmpty() || StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(locale))
        {
            return new HashMap<>();
        }

        Map<Long, String> result = new HashMap<>();
        List<Long> uncachedIds = new java.util.ArrayList<>();

        // 先从缓存获取
        for (Long entityId : entityIds)
        {
            String cacheKey = getCacheKey(entityType, entityId, fieldName, locale);
            String cachedTranslation = redisCache.getCacheObject(cacheKey);
            if (cachedTranslation != null)
            {
                result.put(entityId, cachedTranslation);
            }
            else
            {
                uncachedIds.add(entityId);
            }
        }

        // 对于缓存未命中的，从数据库批量查询
        if (!uncachedIds.isEmpty())
        {
            List<SysI18nTranslation> translations = translationMapper.selectI18nTranslationsBatch(entityType, uncachedIds, fieldName, locale);
            
            // 如果找不到指定语言的翻译，尝试获取默认语言的翻译
            String defaultLocaleStr = localeToString(Constants.DEFAULT_LOCALE);
            if (translations.isEmpty() && !defaultLocaleStr.equals(locale))
            {
                translations = translationMapper.selectI18nTranslationsBatch(entityType, uncachedIds, fieldName, defaultLocaleStr);
            }

            // 将查询结果加入结果集并写入缓存
            for (SysI18nTranslation translation : translations)
            {
                result.put(translation.getEntityId(), translation.getTranslation());
                String cacheKey = getCacheKey(entityType, translation.getEntityId(), fieldName, locale);
                redisCache.setCacheObject(cacheKey, translation.getTranslation(), (int) CACHE_EXPIRE_TIME, TimeUnit.SECONDS);
            }
        }

        return result;
    }

    /**
     * 新增多语言翻译
     */
    @Override
    @Transactional
    public int insertI18nTranslation(SysI18nTranslation translation)
    {
        int result = translationMapper.insertI18nTranslation(translation);
        if (result > 0)
        {
            // 更新缓存
            updateCache(translation);
        }
        return result;
    }

    /**
     * 修改多语言翻译
     */
    @Override
    @Transactional
    public int updateI18nTranslation(SysI18nTranslation translation)
    {
        int result = translationMapper.updateI18nTranslation(translation);
        if (result > 0)
        {
            // 更新缓存
            updateCache(translation);
        }
        return result;
    }

    /**
     * 批量删除多语言翻译
     */
    @Override
    @Transactional
    public int deleteI18nTranslationByIds(Long[] ids)
    {
        // 先查询要删除的翻译信息，用于清理缓存
        List<SysI18nTranslation> translations = new java.util.ArrayList<>();
        for (Long id : ids)
        {
            SysI18nTranslation translation = new SysI18nTranslation();
            translation.setId(id);
            List<SysI18nTranslation> list = translationMapper.selectI18nTranslationList(translation);
            if (!list.isEmpty())
            {
                translations.addAll(list);
            }
        }

        int result = translationMapper.deleteI18nTranslationByIds(ids);
        if (result > 0)
        {
            // 清理缓存
            for (SysI18nTranslation translation : translations)
            {
                clearCache(translation);
            }
        }
        return result;
    }

    /**
     * 根据实体类型和实体ID删除多语言翻译
     */
    @Override
    @Transactional
    public int deleteI18nTranslationByEntity(String entityType, Long entityId)
    {
        int result = translationMapper.deleteI18nTranslationByEntity(entityType, entityId);
        if (result > 0)
        {
            // 清理该实体的所有语言缓存
            clearEntityCache(entityType, entityId);
        }
        return result;
    }

    /**
     * 批量删除多语言翻译（根据实体类型和实体ID集合）
     */
    @Override
    @Transactional
    public int deleteI18nTranslationByEntities(String entityType, Long[] entityIds)
    {
        int result = translationMapper.deleteI18nTranslationByEntities(entityType, entityIds);
        if (result > 0)
        {
            // 清理这些实体的所有语言缓存
            for (Long entityId : entityIds)
            {
                clearEntityCache(entityType, entityId);
            }
        }
        return result;
    }

    /**
     * 保存或更新多语言翻译（如果存在则更新，不存在则新增）
     */
    @Override
    @Transactional
    public int saveOrUpdateI18nTranslation(SysI18nTranslation translation)
    {
        SysI18nTranslation existing = translationMapper.selectI18nTranslation(
            translation.getEntityType(),
            translation.getEntityId(),
            translation.getFieldName(),
            translation.getLocale()
        );

        int result;
        if (existing != null)
        {
            translation.setId(existing.getId());
            result = translationMapper.updateI18nTranslation(translation);
        }
        else
        {
            result = translationMapper.insertI18nTranslation(translation);
        }

        if (result > 0)
        {
            // 更新缓存
            updateCache(translation);
        }

        return result;
    }

    /**
     * 将 Locale 转换为字符串格式（如：zh-CN）
     * 
     * @param locale Locale 对象
     * @return 语言代码字符串
     */
    private String localeToString(Locale locale)
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

    /**
     * 获取缓存 key（单个翻译）
     * 格式：sys_i18n:{entityType}:{entityId}:{fieldName}:{locale}
     */
    private String getCacheKey(String entityType, Long entityId, String fieldName, String locale)
    {
        return CacheConstants.SYS_I18N_KEY + entityType + ":" + entityId + ":" + fieldName + ":" + locale;
    }

    /**
     * 获取实体级别的缓存 key（所有字段的翻译）
     * 格式：sys_i18n:entity:{entityType}:{entityId}:{locale}
     */
    private String getEntityCacheKey(String entityType, Long entityId, String locale)
    {
        return CacheConstants.SYS_I18N_KEY + "entity:" + entityType + ":" + entityId + ":" + locale;
    }

    /**
     * 更新缓存
     */
    private void updateCache(SysI18nTranslation translation)
    {
        if (translation == null)
        {
            return;
        }

        // 更新单个翻译的缓存
        String cacheKey = getCacheKey(translation.getEntityType(), translation.getEntityId(), 
            translation.getFieldName(), translation.getLocale());
        redisCache.setCacheObject(cacheKey, translation.getTranslation(), (int) CACHE_EXPIRE_TIME, TimeUnit.SECONDS);

        // 清除实体级别的缓存（因为字段可能已更新）
        clearEntityCache(translation.getEntityType(), translation.getEntityId());
    }

    /**
     * 清除单个翻译的缓存
     */
    private void clearCache(SysI18nTranslation translation)
    {
        if (translation == null)
        {
            return;
        }

        String cacheKey = getCacheKey(translation.getEntityType(), translation.getEntityId(), 
            translation.getFieldName(), translation.getLocale());
        redisCache.deleteObject(cacheKey);

        // 清除实体级别的缓存
        clearEntityCache(translation.getEntityType(), translation.getEntityId());
    }

    /**
     * 清除实体的所有语言缓存
     */
    private void clearEntityCache(String entityType, Long entityId)
    {
        if (StringUtils.isEmpty(entityType) || entityId == null)
        {
            return;
        }

        // 清除所有语言的实体级别缓存（使用通配符匹配）
        String pattern = CacheConstants.SYS_I18N_KEY + "entity:" + entityType + ":" + entityId + ":*";
        try
        {
            java.util.Collection<String> keys = redisCache.keys(pattern);
            if (keys != null && !keys.isEmpty())
            {
                redisCache.deleteObject(keys);
            }
        }
        catch (Exception e)
        {
            // 忽略异常，继续执行
        }

        // 清除所有语言的单个翻译缓存
        String singlePattern = CacheConstants.SYS_I18N_KEY + entityType + ":" + entityId + ":*";
        try
        {
            java.util.Collection<String> keys = redisCache.keys(singlePattern);
            if (keys != null && !keys.isEmpty())
            {
                redisCache.deleteObject(keys);
            }
        }
        catch (Exception e)
        {
            // 忽略异常，继续执行
        }
    }
}

