package com.quantflow.system.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;

/**
 * 统一多语言翻译表 数据层
 * 
 * @author quantflow
 */
public interface SysI18nTranslationMapper
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
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param fieldName 字段名
     * @param locale 语言代码
     * @return 多语言翻译信息
     */
    public SysI18nTranslation selectI18nTranslation(@Param("entityType") String entityType, 
                                                      @Param("entityId") Long entityId, 
                                                      @Param("fieldName") String fieldName, 
                                                      @Param("locale") String locale);

    /**
     * 根据实体类型、实体ID和语言代码查询所有字段的翻译
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param locale 语言代码
     * @return 多语言翻译集合（字段名 -> 翻译文本的映射）
     */
    public List<SysI18nTranslation> selectI18nTranslationsByEntity(@Param("entityType") String entityType, 
                                                                     @Param("entityId") Long entityId, 
                                                                     @Param("locale") String locale);

    /**
     * 批量查询翻译（用于批量加载）
     * 
     * @param entityType 实体类型
     * @param entityIds 实体ID集合
     * @param fieldName 字段名
     * @param locale 语言代码
     * @return 多语言翻译集合
     */
    public List<SysI18nTranslation> selectI18nTranslationsBatch(@Param("entityType") String entityType, 
                                                                  @Param("entityIds") List<Long> entityIds, 
                                                                  @Param("fieldName") String fieldName, 
                                                                  @Param("locale") String locale);

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
     * 删除多语言翻译
     * 
     * @param id 多语言翻译主键
     * @return 结果
     */
    public int deleteI18nTranslationById(Long id);

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
    public int deleteI18nTranslationByEntity(@Param("entityType") String entityType, @Param("entityId") Long entityId);

    /**
     * 批量删除多语言翻译（根据实体类型和实体ID集合）
     * 
     * @param entityType 实体类型
     * @param entityIds 需要删除的实体ID集合
     * @return 结果
     */
    public int deleteI18nTranslationByEntities(@Param("entityType") String entityType, @Param("entityIds") Long[] entityIds);
}

