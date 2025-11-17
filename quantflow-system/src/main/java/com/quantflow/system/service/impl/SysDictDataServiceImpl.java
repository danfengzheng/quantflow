package com.quantflow.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.quantflow.common.constant.Constants;
import com.quantflow.common.core.domain.entity.SysDictData;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;
import com.quantflow.common.utils.DictUtils;
import com.quantflow.common.utils.I18nUtils;
import com.quantflow.common.utils.SecurityUtils;
import com.quantflow.common.utils.StringUtils;
import com.quantflow.system.mapper.SysDictDataMapper;
import com.quantflow.system.service.ISysDictDataService;
import com.quantflow.system.service.ISysI18nTranslationService;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysDictDataServiceImpl implements ISysDictDataService
{
    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private ISysI18nTranslationService i18nTranslationService;

    /**
     * 根据条件分页查询字典数据
     * 
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData)
    {
        List<SysDictData> list = dictDataMapper.selectDictDataList(dictData);
        // 加载多语言翻译
        String locale = I18nUtils.getCurrentLocale();
        for (SysDictData data : list)
        {
            String translation = i18nTranslationService.getTranslation("dict_data", data.getDictCode(), "dict_label", locale);
            if (StringUtils.isNotEmpty(translation))
            {
                data.setDictLabel(translation);
            }
        }
        return list;
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     * 
     * @param dictType 字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue)
    {
        String defaultLabel = dictDataMapper.selectDictLabel(dictType, dictValue);
        if (StringUtils.isEmpty(defaultLabel))
        {
            return defaultLabel;
        }
        
        // 查询字典数据ID
        SysDictData dictData = dictDataMapper.selectDictDataByTypeAndValue(dictType, dictValue);
        if (dictData == null)
        {
            return defaultLabel;
        }
        
        // 加载多语言翻译
        String locale = I18nUtils.getCurrentLocale();
        String translation = i18nTranslationService.getTranslation("dict_data", dictData.getDictCode(), "dict_label", locale);
        return StringUtils.isNotEmpty(translation) ? translation : defaultLabel;
    }

    /**
     * 根据字典数据ID查询信息
     * 
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public SysDictData selectDictDataById(Long dictCode)
    {
        SysDictData data = dictDataMapper.selectDictDataById(dictCode);
        // 加载多语言翻译
        if (data != null)
        {
            String locale = I18nUtils.getCurrentLocale();
            String translation = i18nTranslationService.getTranslation("dict_data", data.getDictCode(), "dict_label", locale);
            if (StringUtils.isNotEmpty(translation))
            {
                data.setDictLabel(translation);
            }
        }
        return data;
    }

    /**
     * 批量删除字典数据信息
     * 
     * @param dictCodes 需要删除的字典数据ID
     */
    @Override
    public void deleteDictDataByIds(Long[] dictCodes)
    {
        for (Long dictCode : dictCodes)
        {
            SysDictData data = selectDictDataById(dictCode);
            // 删除字典数据的多语言翻译
            i18nTranslationService.deleteI18nTranslationByEntity("dict_data", dictCode);
            dictDataMapper.deleteDictDataById(dictCode);
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
    }

    /**
     * 新增保存字典数据信息
     * 
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(SysDictData data)
    {
        int row = dictDataMapper.insertDictData(data);
        if (row > 0)
        {
            // 自动保存默认语言（zh-CN）的翻译
            if (data.getDictCode() != null && StringUtils.isNotEmpty(data.getDictLabel()))
            {
                saveDictDataI18n(data.getDictCode(), data.getDictLabel());
            }
            
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 保存字典数据多语言翻译
     */
    private void saveDictDataI18n(Long dictCode, String dictLabel)
    {
        String defaultLocale = I18nUtils.localeToString(Constants.DEFAULT_LOCALE);
        
        SysI18nTranslation translation = new SysI18nTranslation();
        translation.setEntityType("dict_data");
        translation.setEntityId(dictCode);
        translation.setFieldName("dict_label");
        translation.setLocale(defaultLocale);
        translation.setTranslation(dictLabel);
        translation.setCreateBy(SecurityUtils.getUsername());
        i18nTranslationService.saveOrUpdateI18nTranslation(translation);
    }

    /**
     * 修改保存字典数据信息
     * 
     * @param data 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(SysDictData data)
    {
        int row = dictDataMapper.updateDictData(data);
        if (row > 0)
        {
            // 自动更新默认语言（zh-CN）的翻译
            if (data.getDictCode() != null && StringUtils.isNotEmpty(data.getDictLabel()))
            {
                saveDictDataI18n(data.getDictCode(), data.getDictLabel());
            }
            
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(data.getDictType());
            DictUtils.setDictCache(data.getDictType(), dictDatas);
        }
        return row;
    }
}
