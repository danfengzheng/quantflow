package com.quantflow.web.controller.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.system.service.ISysI18nTranslationService;

/**
 * 多语言翻译管理
 * 
 * @author quantflow
 */
@RestController
@RequestMapping("/system/i18n")
public class SysI18nTranslationController extends BaseController
{
    @Autowired
    private ISysI18nTranslationService i18nTranslationService;

    /**
     * 获取实体的多语言翻译
     * 
     * @param entityType 实体类型（menu, dict_data等）
     * @param entityId 实体ID
     * @return 多语言翻译列表
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:query')")
    @GetMapping("/{entityType}/{entityId}")
    public AjaxResult getTranslations(@PathVariable String entityType, @PathVariable Long entityId)
    {
        SysI18nTranslation query = new SysI18nTranslation();
        query.setEntityType(entityType);
        query.setEntityId(entityId);
        List<SysI18nTranslation> translations = i18nTranslationService.selectI18nTranslationList(query);
        return success(translations);
    }

    /**
     * 获取实体的某个字段的多语言翻译
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param fieldName 字段名
     * @return 多语言翻译Map（locale -> translation）
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:query')")
    @GetMapping("/{entityType}/{entityId}/{fieldName}")
    public AjaxResult getFieldTranslations(
        @PathVariable String entityType, 
        @PathVariable Long entityId, 
        @PathVariable String fieldName)
    {
        // 获取所有支持的语言
        String[] locales = {"zh-CN", "zh-TW", "en-US", "ja-JP"};
        Map<String, String> translations = new java.util.HashMap<>();
        
        for (String locale : locales)
        {
            String translation = i18nTranslationService.getTranslation(entityType, entityId, fieldName, locale);
            if (translation != null)
            {
                translations.put(locale, translation);
            }
        }
        
        return success(translations);
    }

    /**
     * 批量保存实体的多语言翻译
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param translations 翻译数据 Map<fieldName, Map<locale, translation>>
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:edit')")
    @Log(title = "多语言翻译", businessType = BusinessType.UPDATE)
    @PutMapping("/{entityType}/{entityId}")
    public AjaxResult saveTranslations(
        @PathVariable String entityType,
        @PathVariable Long entityId,
        @RequestBody Map<String, Map<String, String>> translations)
    {
        try
        {
            for (Map.Entry<String, Map<String, String>> fieldEntry : translations.entrySet())
            {
                String fieldName = fieldEntry.getKey();
                Map<String, String> localeTranslations = fieldEntry.getValue();
                
                for (Map.Entry<String, String> localeEntry : localeTranslations.entrySet())
                {
                    String locale = localeEntry.getKey();
                    String translation = localeEntry.getValue();
                    
                    if (translation != null && !translation.trim().isEmpty())
                    {
                        SysI18nTranslation i18nTranslation = new SysI18nTranslation();
                        i18nTranslation.setEntityType(entityType);
                        i18nTranslation.setEntityId(entityId);
                        i18nTranslation.setFieldName(fieldName);
                        i18nTranslation.setLocale(locale);
                        i18nTranslation.setTranslation(translation.trim());
                        i18nTranslation.setUpdateBy(getUsername());
                        
                        i18nTranslationService.saveOrUpdateI18nTranslation(i18nTranslation);
                    }
                }
            }
            
            return success();
        }
        catch (Exception e)
        {
            return error("保存多语言翻译失败：" + e.getMessage());
        }
    }

    /**
     * 保存单个字段的多语言翻译
     * 
     * @param entityType 实体类型
     * @param entityId 实体ID
     * @param fieldName 字段名
     * @param translations 翻译数据 Map<locale, translation>
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:edit')")
    @Log(title = "多语言翻译", businessType = BusinessType.UPDATE)
    @PutMapping("/{entityType}/{entityId}/{fieldName}")
    public AjaxResult saveFieldTranslations(
        @PathVariable String entityType,
        @PathVariable Long entityId,
        @PathVariable String fieldName,
        @RequestBody Map<String, String> translations)
    {
        try
        {
            for (Map.Entry<String, String> entry : translations.entrySet())
            {
                String locale = entry.getKey();
                String translation = entry.getValue();
                
                if (translation != null && !translation.trim().isEmpty())
                {
                    SysI18nTranslation i18nTranslation = new SysI18nTranslation();
                    i18nTranslation.setEntityType(entityType);
                    i18nTranslation.setEntityId(entityId);
                    i18nTranslation.setFieldName(fieldName);
                    i18nTranslation.setLocale(locale);
                    i18nTranslation.setTranslation(translation.trim());
                    i18nTranslation.setUpdateBy(getUsername());
                    
                    i18nTranslationService.saveOrUpdateI18nTranslation(i18nTranslation);
                }
            }
            
            return success();
        }
        catch (Exception e)
        {
            return error("保存多语言翻译失败：" + e.getMessage());
        }
    }
}

