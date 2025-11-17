package com.quantflow.common.core.domain.entity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.quantflow.common.core.domain.BaseEntity;

/**
 * 统一多语言翻译表 sys_i18n_translation
 * 
 * 实体类型说明：
 * - menu: 菜单
 * - dict_data: 字典数据
 * - role: 角色（可扩展）
 * - dept: 部门（可扩展）
 * - post: 岗位（可扩展）
 * 
 * @author quantflow
 */
public class SysI18nTranslation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 实体类型 */
    @NotBlank(message = "实体类型不能为空")
    @Size(min = 0, max = 50, message = "实体类型长度不能超过50个字符")
    private String entityType;

    /** 实体ID */
    @NotNull(message = "实体ID不能为空")
    private Long entityId;

    /** 字段名 */
    @NotBlank(message = "字段名不能为空")
    @Size(min = 0, max = 50, message = "字段名长度不能超过50个字符")
    private String fieldName;

    /** 语言代码 */
    @NotBlank(message = "语言代码不能为空")
    @Size(min = 0, max = 10, message = "语言代码长度不能超过10个字符")
    private String locale;

    /** 翻译文本 */
    @NotBlank(message = "翻译文本不能为空")
    @Size(min = 0, max = 500, message = "翻译文本长度不能超过500个字符")
    private String translation;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getEntityType()
    {
        return entityType;
    }

    public void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }

    public Long getEntityId()
    {
        return entityId;
    }

    public void setEntityId(Long entityId)
    {
        this.entityId = entityId;
    }

    public String getFieldName()
    {
        return fieldName;
    }

    public void setFieldName(String fieldName)
    {
        this.fieldName = fieldName;
    }

    public String getLocale()
    {
        return locale;
    }

    public void setLocale(String locale)
    {
        this.locale = locale;
    }

    public String getTranslation()
    {
        return translation;
    }

    public void setTranslation(String translation)
    {
        this.translation = translation;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("entityType", getEntityType())
            .append("entityId", getEntityId())
            .append("fieldName", getFieldName())
            .append("locale", getLocale())
            .append("translation", getTranslation())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}

