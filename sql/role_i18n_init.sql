-- ============================================
-- 角色多语言初始化脚本
-- ============================================
-- 说明：为所有角色添加多语言翻译（zh-CN, zh-TW, en-US, ja-JP）
-- 执行前请确保 sys_i18n_translation 表已创建

-- 删除已存在的角色多语言数据（可选，如果需要重新初始化）
-- DELETE FROM `sys_i18n_translation` WHERE `entity_type` = 'role' AND `field_name` = 'role_name';

-- ============================================
-- 系统角色
-- ============================================
-- 1	超级管理员
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('role', 1, 'role_name', 'zh-CN', '超级管理员', 'admin', NOW(), NOW()),
('role', 1, 'role_name', 'zh-TW', '超級管理員', 'admin', NOW(), NOW()),
('role', 1, 'role_name', 'en-US', 'Super Administrator', 'admin', NOW(), NOW()),
('role', 1, 'role_name', 'ja-JP', 'スーパー管理者', 'admin', NOW(), NOW());

-- 2	普通角色
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('role', 2, 'role_name', 'zh-CN', '普通角色', 'admin', NOW(), NOW()),
('role', 2, 'role_name', 'zh-TW', '普通角色', 'admin', NOW(), NOW()),
('role', 2, 'role_name', 'en-US', 'Normal Role', 'admin', NOW(), NOW()),
('role', 2, 'role_name', 'ja-JP', '通常ロール', 'admin', NOW(), NOW());

