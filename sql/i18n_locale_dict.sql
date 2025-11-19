-- ============================================
-- 多语言代码字典初始化脚本
-- ============================================
-- 说明：创建多语言代码字典类型和字典数据，用于多语言管理页面
-- 执行前请确保 sys_dict_type 和 sys_dict_data 表已创建

-- ============================================
-- 1. 创建字典类型
-- ============================================
INSERT INTO `sys_dict_type` (`dict_name`, `dict_type`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES ('多语言代码', 'sys_i18n_locale', '0', 'admin', NOW(), '', NULL, '多语言代码列表（用于多语言管理）')
ON DUPLICATE KEY UPDATE 
  `dict_name` = '多语言代码',
  `status` = '0',
  `remark` = '多语言代码列表（用于多语言管理）';

-- ============================================
-- 2. 创建字典数据
-- ============================================
-- 获取字典类型ID（如果已存在）
SET @dict_type_id = (SELECT `dict_id` FROM `sys_dict_type` WHERE `dict_type` = 'sys_i18n_locale' LIMIT 1);

-- 简体中文 (zh-CN)
INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES (1, '简体中文', 'zh-CN', 'sys_i18n_locale', '🇨🇳', 'success', 'Y', '0', 'admin', NOW(), '', NULL, '简体中文|🇨🇳')
ON DUPLICATE KEY UPDATE 
  `dict_label` = '简体中文',
  `dict_sort` = 1,
  `css_class` = '🇨🇳',
  `list_class` = 'success',
  `is_default` = 'Y',
  `status` = '0',
  `remark` = '简体中文|🇨🇳';

-- 繁体中文 (zh-TW)
INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES (2, '繁体中文', 'zh-TW', 'sys_i18n_locale', '🇹🇼', 'info', 'N', '0', 'admin', NOW(), '', NULL, '繁体中文|🇹🇼')
ON DUPLICATE KEY UPDATE 
  `dict_label` = '繁体中文',
  `dict_sort` = 2,
  `css_class` = '🇹🇼',
  `list_class` = 'info',
  `is_default` = 'N',
  `status` = '0',
  `remark` = '繁体中文|🇹🇼';

-- 英文 (en-US)
INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES (3, 'English', 'en-US', 'sys_i18n_locale', '🇺🇸', 'warning', 'N', '0', 'admin', NOW(), '', NULL, 'English|🇺🇸')
ON DUPLICATE KEY UPDATE 
  `dict_label` = 'English',
  `dict_sort` = 3,
  `css_class` = '🇺🇸',
  `list_class` = 'warning',
  `is_default` = 'N',
  `status` = '0',
  `remark` = 'English|🇺🇸';

-- 日文 (ja-JP)
INSERT INTO `sys_dict_data` (`dict_sort`, `dict_label`, `dict_value`, `dict_type`, `css_class`, `list_class`, `is_default`, `status`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES (4, '日本語', 'ja-JP', 'sys_i18n_locale', '🇯🇵', 'danger', 'N', '0', 'admin', NOW(), '', NULL, '日本語|🇯🇵')
ON DUPLICATE KEY UPDATE 
  `dict_label` = '日本語',
  `dict_sort` = 4,
  `css_class` = '🇯🇵',
  `list_class` = 'danger',
  `is_default` = 'N',
  `status` = '0',
  `remark` = '日本語|🇯🇵';

