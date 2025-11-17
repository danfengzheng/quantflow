package com.quantflow.web.controller.system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.quantflow.common.annotation.Log;
import com.quantflow.common.core.controller.BaseController;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;
import com.quantflow.common.core.page.TableDataInfo;
import com.quantflow.common.enums.BusinessType;
import com.quantflow.common.utils.StringUtils;
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
     * 获取多语言翻译列表
     * 
     * @param translation 查询条件
     * @return 多语言翻译列表
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysI18nTranslation translation)
    {
        startPage();
        List<SysI18nTranslation> list = i18nTranslationService.selectI18nTranslationList(translation);
        return getDataTable(list);
    }

    /**
     * 根据ID获取多语言翻译详细信息
     * 
     * @param id 翻译ID
     * @return 多语言翻译信息
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:query')")
    @GetMapping("/{id}")
    public AjaxResult getInfo(@PathVariable Long id)
    {
        SysI18nTranslation query = new SysI18nTranslation();
        query.setId(id);
        List<SysI18nTranslation> list = i18nTranslationService.selectI18nTranslationList(query);
        if (list != null && !list.isEmpty())
        {
            return success(list.get(0));
        }
        return error("翻译记录不存在");
    }

    /**
     * 获取实体的多语言翻译
     * 
     * @param entityType 实体类型（menu, dict_data等）
     * @param entityId 实体ID
     * @return 多语言翻译列表
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:query')")
    @GetMapping("/entity/{entityType}/{entityId}")
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
    @GetMapping("/entity/{entityType}/{entityId}/{fieldName}")
    public AjaxResult getFieldTranslations(
        @PathVariable String entityType, 
        @PathVariable Long entityId, 
        @PathVariable String fieldName)
    {
        Map<String, String> translations = i18nTranslationService.getTranslationsByEntityAndField(entityType, entityId, fieldName);
        return success(translations);
    }

    /**
     * 新增多语言翻译
     * 
     * @param translation 多语言翻译信息
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:add')")
    @Log(title = "多语言翻译", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SysI18nTranslation translation)
    {
        if (StringUtils.isEmpty(translation.getEntityType()) || translation.getEntityId() == null
            || StringUtils.isEmpty(translation.getFieldName()) || StringUtils.isEmpty(translation.getLocale())
            || StringUtils.isEmpty(translation.getTranslation()))
        {
            return error("参数不完整");
        }
        translation.setCreateBy(getUsername());
        return toAjax(i18nTranslationService.insertI18nTranslation(translation));
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
    @PutMapping("/entity/{entityType}/{entityId}")
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
    @PutMapping("/entity/{entityType}/{entityId}/{fieldName}")
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

    /**
     * 修改多语言翻译
     * 
     * @param translation 多语言翻译信息
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:edit')")
    @Log(title = "多语言翻译", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SysI18nTranslation translation)
    {
        if (translation.getId() == null)
        {
            return error("翻译ID不能为空");
        }
        if (StringUtils.isEmpty(translation.getTranslation()))
        {
            return error("翻译内容不能为空");
        }
        translation.setUpdateBy(getUsername());
        return toAjax(i18nTranslationService.updateI18nTranslation(translation));
    }

    /**
     * 删除多语言翻译
     * 
     * @param ids 需要删除的翻译ID数组
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:remove')")
    @Log(title = "多语言翻译", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(i18nTranslationService.deleteI18nTranslationByIds(ids));
    }

    /**
     * 刷新所有多语言翻译的Redis缓存
     * 
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:remove')")
    @Log(title = "多语言翻译", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache")
    public AjaxResult refreshCache()
    {
        i18nTranslationService.clearAllI18nCache();
        return success("缓存刷新成功");
    }

    /**
     * 根据实体类型刷新多语言翻译的Redis缓存
     * 
     * @param entityType 实体类型
     * @return 结果
     */
    @PreAuthorize("@ss.hasPermi('system:i18n:remove')")
    @Log(title = "多语言翻译", businessType = BusinessType.CLEAN)
    @DeleteMapping("/refreshCache/{entityType}")
    public AjaxResult refreshCacheByEntityType(@PathVariable String entityType)
    {
        if (StringUtils.isEmpty(entityType))
        {
            return error("实体类型不能为空");
        }
        i18nTranslationService.clearI18nCacheByEntityType(entityType);
        return success("缓存刷新成功");
    }
}

