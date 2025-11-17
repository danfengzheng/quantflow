package com.quantflow.system.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.quantflow.common.constant.UserConstants;
import com.quantflow.common.core.domain.entity.SysDictData;
import com.quantflow.common.core.domain.entity.SysDictType;
import com.quantflow.common.constant.MessageKeys;
import com.quantflow.common.exception.ServiceException;
import com.quantflow.common.utils.DictUtils;
import com.quantflow.common.utils.I18nUtils;
import com.quantflow.common.utils.StringUtils;
import com.quantflow.common.core.domain.entity.SysI18nTranslation;
import com.quantflow.system.mapper.SysDictDataMapper;
import com.quantflow.system.mapper.SysDictTypeMapper;
import com.quantflow.system.service.ISysDictTypeService;
import com.quantflow.system.service.ISysI18nTranslationService;

/**
 * 字典 业务层处理
 * 
 * @author ruoyi
 */
@Service
public class SysDictTypeServiceImpl implements ISysDictTypeService
{
    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private ISysI18nTranslationService i18nTranslationService;

    /**
     * 项目启动时，初始化字典到缓存
     */
    @PostConstruct
    public void init()
    {
        loadingDictCache();
    }

    /**
     * 根据条件分页查询字典类型
     * 
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType)
    {
        List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeList(dictType);
        // 加载多语言翻译
        if (StringUtils.isNotEmpty(dictTypeList))
        {
            String locale = I18nUtils.getCurrentLocale();
            for (SysDictType type : dictTypeList)
            {
                String translation = i18nTranslationService.getTranslation("dict_type", type.getDictId(), "dict_name", locale);
                if (StringUtils.isNotEmpty(translation))
                {
                    type.setDictName(translation);
                }
            }
        }
        return dictTypeList;
    }

    /**
     * 根据所有字典类型
     * 
     * @return 字典类型集合信息
     */
    @Override
    public List<SysDictType> selectDictTypeAll()
    {
        List<SysDictType> dictTypeList = dictTypeMapper.selectDictTypeAll();
        // 加载多语言翻译
        if (StringUtils.isNotEmpty(dictTypeList))
        {
            String locale = I18nUtils.getCurrentLocale();
            for (SysDictType type : dictTypeList)
            {
                String translation = i18nTranslationService.getTranslation("dict_type", type.getDictId(), "dict_name", locale);
                if (StringUtils.isNotEmpty(translation))
                {
                    type.setDictName(translation);
                }
            }
        }
        return dictTypeList;
    }

    /**
     * 根据字典类型查询字典数据
     * 
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<SysDictData> selectDictDataByType(String dictType)
    {
        List<SysDictData> dictDatas = DictUtils.getDictCache(dictType);
        if (StringUtils.isEmpty(dictDatas))
        {
            dictDatas = dictDataMapper.selectDictDataByType(dictType);
            if (StringUtils.isNotEmpty(dictDatas))
            {
                DictUtils.setDictCache(dictType, dictDatas);
            }
        }
        
        // 加载多语言翻译（从缓存或数据库获取的数据都需要加载多语言）
        if (StringUtils.isNotEmpty(dictDatas))
        {
            String locale = I18nUtils.getCurrentLocale();
            for (SysDictData data : dictDatas)
            {
                String translation = i18nTranslationService.getTranslation("dict_data", data.getDictCode(), "dict_label", locale);
                if (StringUtils.isNotEmpty(translation))
                {
                    data.setDictLabel(translation);
                }
            }
        }
        
        return dictDatas;
    }

    /**
     * 根据字典类型ID查询信息
     * 
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeById(Long dictId)
    {
        SysDictType dictType = dictTypeMapper.selectDictTypeById(dictId);
        // 加载多语言翻译
        if (dictType != null)
        {
            String locale = I18nUtils.getCurrentLocale();
            String translation = i18nTranslationService.getTranslation("dict_type", dictType.getDictId(), "dict_name", locale);
            if (StringUtils.isNotEmpty(translation))
            {
                dictType.setDictName(translation);
            }
        }
        return dictType;
    }

    /**
     * 根据字典类型查询信息
     * 
     * @param dictType 字典类型
     * @return 字典类型
     */
    @Override
    public SysDictType selectDictTypeByType(String dictType)
    {
        SysDictType dictTypeObj = dictTypeMapper.selectDictTypeByType(dictType);
        // 加载多语言翻译
        if (dictTypeObj != null)
        {
            String locale = I18nUtils.getCurrentLocale();
            String translation = i18nTranslationService.getTranslation("dict_type", dictTypeObj.getDictId(), "dict_name", locale);
            if (StringUtils.isNotEmpty(translation))
            {
                dictTypeObj.setDictName(translation);
            }
        }
        return dictTypeObj;
    }

    /**
     * 批量删除字典类型信息
     * 
     * @param dictIds 需要删除的字典ID
     */
    @Override
    public void deleteDictTypeByIds(Long[] dictIds)
    {
        for (Long dictId : dictIds)
        {
            SysDictType dictType = selectDictTypeById(dictId);
            if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0)
            {
                throw new ServiceException(MessageKeys.DICT_ASSIGNED_CANNOT_DELETE, dictType.getDictName());
            }
            // 删除字典类型的多语言翻译
            i18nTranslationService.deleteI18nTranslationByEntity("dict_type", dictId);
            dictTypeMapper.deleteDictTypeById(dictId);
            DictUtils.removeDictCache(dictType.getDictType());
        }
    }

    /**
     * 加载字典缓存数据
     */
    @Override
    public void loadingDictCache()
    {
        SysDictData dictData = new SysDictData();
        dictData.setStatus("0");
        Map<String, List<SysDictData>> dictDataMap = dictDataMapper.selectDictDataList(dictData).stream().collect(Collectors.groupingBy(SysDictData::getDictType));
        for (Map.Entry<String, List<SysDictData>> entry : dictDataMap.entrySet())
        {
            DictUtils.setDictCache(entry.getKey(), entry.getValue().stream().sorted(Comparator.comparing(SysDictData::getDictSort)).collect(Collectors.toList()));
        }
    }

    /**
     * 清空字典缓存数据
     */
    @Override
    public void clearDictCache()
    {
        DictUtils.clearDictCache();
    }

    /**
     * 重置字典缓存数据
     */
    @Override
    public void resetDictCache()
    {
        clearDictCache();
        loadingDictCache();
    }

    /**
     * 新增保存字典类型信息
     * 
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(SysDictType dict)
    {
        int row = dictTypeMapper.insertDictType(dict);
        if (row > 0 && dict.getDictId() != null)
        {
            // 自动保存默认语言（zh-CN）的翻译
            saveDictTypeI18n(dict.getDictId(), dict.getDictName());
            DictUtils.setDictCache(dict.getDictType(), null);
        }
        return row;
    }

    /**
     * 保存字典类型多语言翻译
     */
    private void saveDictTypeI18n(Long dictId, String dictName)
    {
        if (dictId == null || StringUtils.isEmpty(dictName))
        {
            return;
        }
        
        String defaultLocale = I18nUtils.localeToString(com.quantflow.common.constant.Constants.DEFAULT_LOCALE);
        
        SysI18nTranslation translation = new SysI18nTranslation();
        translation.setEntityType("dict_type");
        translation.setEntityId(dictId);
        translation.setFieldName("dict_name");
        translation.setLocale(defaultLocale);
        translation.setTranslation(dictName);
        translation.setCreateBy(com.quantflow.common.utils.SecurityUtils.getUsername());
        i18nTranslationService.saveOrUpdateI18nTranslation(translation);
    }

    /**
     * 修改保存字典类型信息
     * 
     * @param dict 字典类型信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDictType(SysDictType dict)
    {
        SysDictType oldDict = dictTypeMapper.selectDictTypeById(dict.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dict.getDictType());
        int row = dictTypeMapper.updateDictType(dict);
        if (row > 0 && dict.getDictId() != null)
        {
            // 自动更新默认语言（zh-CN）的翻译
            saveDictTypeI18n(dict.getDictId(), dict.getDictName());
            List<SysDictData> dictDatas = dictDataMapper.selectDictDataByType(dict.getDictType());
            DictUtils.setDictCache(dict.getDictType(), dictDatas);
        }
        return row;
    }

    /**
     * 校验字典类型称是否唯一
     * 
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public boolean checkDictTypeUnique(SysDictType dict)
    {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        SysDictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue())
        {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }
}
