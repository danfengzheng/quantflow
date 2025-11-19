-- ============================================
-- 多语言代码字典数据多语言初始化脚本
-- ============================================
-- 说明：为多语言代码字典数据添加多语言翻译
-- 执行前请确保：
-- 1. sys_i18n_translation 表已创建
-- 2. sys_dict_data 表中已存在 sys_i18n_locale 类型的字典数据

-- 获取字典数据的 dict_code（需要先执行 i18n_locale_dict.sql）
-- 简体中文 (zh-CN)
SET @dict_code_zh_cn = (SELECT `dict_code` FROM `sys_dict_data` WHERE `dict_type` = 'sys_i18n_locale' AND `dict_value` = 'zh-CN' LIMIT 1);
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_cn, 'dict_label', 'zh-CN', '简体中文', 'admin', NOW(), NOW()
WHERE @dict_code_zh_cn IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '简体中文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_cn, 'dict_label', 'zh-TW', '簡體中文', 'admin', NOW(), NOW()
WHERE @dict_code_zh_cn IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '簡體中文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_cn, 'dict_label', 'en-US', 'Simplified Chinese', 'admin', NOW(), NOW()
WHERE @dict_code_zh_cn IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = 'Simplified Chinese', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_cn, 'dict_label', 'ja-JP', '簡体中国語', 'admin', NOW(), NOW()
WHERE @dict_code_zh_cn IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '簡体中国語', `update_time` = NOW();

-- 繁体中文 (zh-TW)
SET @dict_code_zh_tw = (SELECT `dict_code` FROM `sys_dict_data` WHERE `dict_type` = 'sys_i18n_locale' AND `dict_value` = 'zh-TW' LIMIT 1);
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_tw, 'dict_label', 'zh-CN', '繁体中文', 'admin', NOW(), NOW()
WHERE @dict_code_zh_tw IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '繁体中文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_tw, 'dict_label', 'zh-TW', '繁體中文', 'admin', NOW(), NOW()
WHERE @dict_code_zh_tw IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '繁體中文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_tw, 'dict_label', 'en-US', 'Traditional Chinese', 'admin', NOW(), NOW()
WHERE @dict_code_zh_tw IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = 'Traditional Chinese', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_zh_tw, 'dict_label', 'ja-JP', '繁体中国語', 'admin', NOW(), NOW()
WHERE @dict_code_zh_tw IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '繁体中国語', `update_time` = NOW();

-- 英文 (en-US)
SET @dict_code_en_us = (SELECT `dict_code` FROM `sys_dict_data` WHERE `dict_type` = 'sys_i18n_locale' AND `dict_value` = 'en-US' LIMIT 1);
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_en_us, 'dict_label', 'zh-CN', '英文', 'admin', NOW(), NOW()
WHERE @dict_code_en_us IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '英文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_en_us, 'dict_label', 'zh-TW', '英文', 'admin', NOW(), NOW()
WHERE @dict_code_en_us IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '英文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_en_us, 'dict_label', 'en-US', 'English', 'admin', NOW(), NOW()
WHERE @dict_code_en_us IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = 'English', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_en_us, 'dict_label', 'ja-JP', '英語', 'admin', NOW(), NOW()
WHERE @dict_code_en_us IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '英語', `update_time` = NOW();

-- 日文 (ja-JP)
SET @dict_code_ja_jp = (SELECT `dict_code` FROM `sys_dict_data` WHERE `dict_type` = 'sys_i18n_locale' AND `dict_value` = 'ja-JP' LIMIT 1);
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_ja_jp, 'dict_label', 'zh-CN', '日文', 'admin', NOW(), NOW()
WHERE @dict_code_ja_jp IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '日文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_ja_jp, 'dict_label', 'zh-TW', '日文', 'admin', NOW(), NOW()
WHERE @dict_code_ja_jp IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '日文', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_ja_jp, 'dict_label', 'en-US', 'Japanese', 'admin', NOW(), NOW()
WHERE @dict_code_ja_jp IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = 'Japanese', `update_time` = NOW();

INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) 
SELECT 'dict_data', @dict_code_ja_jp, 'dict_label', 'ja-JP', '日本語', 'admin', NOW(), NOW()
WHERE @dict_code_ja_jp IS NOT NULL
ON DUPLICATE KEY UPDATE `translation` = '日本語', `update_time` = NOW();

