package com.quantflow.system.service;

import java.util.List;
import java.util.Map;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;

/**
 * 多语言翻译 业务层
 * 
 * @author quantflow
 */
public interface ISysI18nTranslationService
{
    /**
     * 查询多语言翻译列表
     * 
     * @param translation 多语言翻译信息
     * @return 多语言翻译集合
     */
    public List<SysI18nTranslation> selectI18nTranslationList(SysI18nTranslation translation);

    /**
     * 根据实体类型、实体ID、字段名和语言代码查询翻译
     * 如果找不到指定语言的翻译，会回退到默认语言（zh-CN）
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param fieldName 字段名
     * @param locale 语言代码
     * @return 翻译文本，如果找不到返回null
     */
    public String getTranslation(String entityType, Long entityId, String fieldName, String locale);

    /**
     * 根据实体类型、实体ID和语言代码查询所有字段的翻译
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param locale 语言代码
     * @return 字段名 -> 翻译文本的映射
     */
    public Map<String, String> getTranslationsByEntity(String entityType, Long entityId, String locale);

    /**
     * 批量获取翻译（用于批量加载，提高性能）
     * 
     * @param entityType 实体类型
     * @param entityIds 实体ID集合
     * @param fieldName 字段名
     * @param locale 语言代码
     * @return 实体ID -> 翻译文本的映射
     */
    public Map<Long, String> getTranslationsBatch(String entityType, List<Long> entityIds, String fieldName, String locale);

    /**
     * 新增多语言翻译
     * 
     * @param translation 多语言翻译信息
     * @return 结果
     */
    public int insertI18nTranslation(SysI18nTranslation translation);

    /**
     * 修改多语言翻译
     * 
     * @param translation 多语言翻译信息
     * @return 结果
     */
    public int updateI18nTranslation(SysI18nTranslation translation);

    /**
     * 批量删除多语言翻译
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteI18nTranslationByIds(Long[] ids);

    /**
     * 根据实体类型和实体ID删除多语言翻译
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @return 结果
     */
    public int deleteI18nTranslationByEntity(String entityType, Long entityId);

    /**
     * 批量删除多语言翻译（根据实体类型和实体ID集合）
     * 
     * @param entityType 实体类型
     * @param entityIds 需要删除的实体ID集合
     * @return 结果
     */
    public int deleteI18nTranslationByEntities(String entityType, Long[] entityIds);

    /**
     * 保存或更新多语言翻译（如果存在则更新，不存在则新增）
     * 
     * @param translation 多语言翻译信息
     * @return 结果
     */
    public int saveOrUpdateI18nTranslation(SysI18nTranslation translation);
}

