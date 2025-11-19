-- ============================================
-- 部门多语言初始化脚本
-- ============================================
-- 说明：为所有部门添加多语言翻译（zh-CN, zh-TW, en-US, ja-JP）
-- 执行前请确保 sys_i18n_translation 表已创建

-- 删除已存在的部门多语言数据（可选，如果需要重新初始化）
-- DELETE FROM `sys_i18n_translation` WHERE `entity_type` = 'dept' AND `field_name` = 'dept_name';

-- ============================================
-- 系统部门
-- ============================================
-- 100	若依科技
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 100, 'dept_name', 'zh-CN', '若依科技', 'admin', NOW(), NOW()),
('dept', 100, 'dept_name', 'zh-TW', '若依科技', 'admin', NOW(), NOW()),
('dept', 100, 'dept_name', 'en-US', 'RuoYi Technology', 'admin', NOW(), NOW()),
('dept', 100, 'dept_name', 'ja-JP', '若依科技', 'admin', NOW(), NOW());

-- 101	深圳总公司
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 101, 'dept_name', 'zh-CN', '深圳总公司', 'admin', NOW(), NOW()),
('dept', 101, 'dept_name', 'zh-TW', '深圳總公司', 'admin', NOW(), NOW()),
('dept', 101, 'dept_name', 'en-US', 'Shenzhen Headquarters', 'admin', NOW(), NOW()),
('dept', 101, 'dept_name', 'ja-JP', '深圳本社', 'admin', NOW(), NOW());

-- 102	长沙分公司
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 102, 'dept_name', 'zh-CN', '长沙分公司', 'admin', NOW(), NOW()),
('dept', 102, 'dept_name', 'zh-TW', '長沙分公司', 'admin', NOW(), NOW()),
('dept', 102, 'dept_name', 'en-US', 'Changsha Branch', 'admin', NOW(), NOW()),
('dept', 102, 'dept_name', 'ja-JP', '長沙支社', 'admin', NOW(), NOW());

-- 103	研发部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 103, 'dept_name', 'zh-CN', '研发部门', 'admin', NOW(), NOW()),
('dept', 103, 'dept_name', 'zh-TW', '研發部門', 'admin', NOW(), NOW()),
('dept', 103, 'dept_name', 'en-US', 'R&D Department', 'admin', NOW(), NOW()),
('dept', 103, 'dept_name', 'ja-JP', '研究開発部門', 'admin', NOW(), NOW());

-- 104	市场部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 104, 'dept_name', 'zh-CN', '市场部门', 'admin', NOW(), NOW()),
('dept', 104, 'dept_name', 'zh-TW', '市場部門', 'admin', NOW(), NOW()),
('dept', 104, 'dept_name', 'en-US', 'Marketing Department', 'admin', NOW(), NOW()),
('dept', 104, 'dept_name', 'ja-JP', 'マーケティング部門', 'admin', NOW(), NOW());

-- 105	测试部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 105, 'dept_name', 'zh-CN', '测试部门', 'admin', NOW(), NOW()),
('dept', 105, 'dept_name', 'zh-TW', '測試部門', 'admin', NOW(), NOW()),
('dept', 105, 'dept_name', 'en-US', 'Testing Department', 'admin', NOW(), NOW()),
('dept', 105, 'dept_name', 'ja-JP', 'テスト部門', 'admin', NOW(), NOW());

-- 106	财务部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 106, 'dept_name', 'zh-CN', '财务部门', 'admin', NOW(), NOW()),
('dept', 106, 'dept_name', 'zh-TW', '財務部門', 'admin', NOW(), NOW()),
('dept', 106, 'dept_name', 'en-US', 'Finance Department', 'admin', NOW(), NOW()),
('dept', 106, 'dept_name', 'ja-JP', '財務部門', 'admin', NOW(), NOW());

-- 107	运维部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 107, 'dept_name', 'zh-CN', '运维部门', 'admin', NOW(), NOW()),
('dept', 107, 'dept_name', 'zh-TW', '運維部門', 'admin', NOW(), NOW()),
('dept', 107, 'dept_name', 'en-US', 'Operations Department', 'admin', NOW(), NOW()),
('dept', 107, 'dept_name', 'ja-JP', '運用保守部門', 'admin', NOW(), NOW());

-- 108	市场部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 108, 'dept_name', 'zh-CN', '市场部门', 'admin', NOW(), NOW()),
('dept', 108, 'dept_name', 'zh-TW', '市場部門', 'admin', NOW(), NOW()),
('dept', 108, 'dept_name', 'en-US', 'Marketing Department', 'admin', NOW(), NOW()),
('dept', 108, 'dept_name', 'ja-JP', 'マーケティング部門', 'admin', NOW(), NOW());

-- 109	财务部门
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dept', 109, 'dept_name', 'zh-CN', '财务部门', 'admin', NOW(), NOW()),
('dept', 109, 'dept_name', 'zh-TW', '財務部門', 'admin', NOW(), NOW()),
('dept', 109, 'dept_name', 'en-US', 'Finance Department', 'admin', NOW(), NOW()),
('dept', 109, 'dept_name', 'ja-JP', '財務部門', 'admin', NOW(), NOW());

