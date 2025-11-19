-- ============================================
-- 多语言管理菜单多语言初始化脚本
-- ============================================
-- 说明：为多语言管理菜单及其按钮添加多语言翻译（zh-CN, zh-TW, en-US, ja-JP）
-- 执行前请确保：
-- 1. sys_i18n_translation 表已创建
-- 2. 已执行 menu_i18n_management.sql 创建菜单

-- ============================================
-- 多语言管理主菜单 (menu_id = 2000)
-- ============================================
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2000, 'menu_name', 'zh-CN', '多语言管理', 'admin', NOW(), NOW()),
('menu', 2000, 'menu_name', 'zh-TW', '多語言管理', 'admin', NOW(), NOW()),
('menu', 2000, 'menu_name', 'en-US', 'Multi-language Management', 'admin', NOW(), NOW()),
('menu', 2000, 'menu_name', 'ja-JP', '多言語管理', 'admin', NOW(), NOW());

-- 菜单备注
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2000, 'remark', 'zh-CN', '多语言翻译管理菜单', 'admin', NOW(), NOW()),
('menu', 2000, 'remark', 'zh-TW', '多語言翻譯管理菜單', 'admin', NOW(), NOW()),
('menu', 2000, 'remark', 'en-US', 'Multi-language Translation Management Menu', 'admin', NOW(), NOW()),
('menu', 2000, 'remark', 'ja-JP', '多言語翻訳管理メニュー', 'admin', NOW(), NOW());

-- ============================================
-- 多语言管理按钮权限
-- ============================================
-- 多语言查询 (menu_id = 2001)
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2001, 'menu_name', 'zh-CN', '多语言查询', 'admin', NOW(), NOW()),
('menu', 2001, 'menu_name', 'zh-TW', '多語言查詢', 'admin', NOW(), NOW()),
('menu', 2001, 'menu_name', 'en-US', 'Query Translation', 'admin', NOW(), NOW()),
('menu', 2001, 'menu_name', 'ja-JP', '翻訳照会', 'admin', NOW(), NOW());

-- 多语言新增 (menu_id = 2002)
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2002, 'menu_name', 'zh-CN', '多语言新增', 'admin', NOW(), NOW()),
('menu', 2002, 'menu_name', 'zh-TW', '多語言新增', 'admin', NOW(), NOW()),
('menu', 2002, 'menu_name', 'en-US', 'Add Translation', 'admin', NOW(), NOW()),
('menu', 2002, 'menu_name', 'ja-JP', '翻訳追加', 'admin', NOW(), NOW());

-- 多语言修改 (menu_id = 2003)
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2003, 'menu_name', 'zh-CN', '多语言修改', 'admin', NOW(), NOW()),
('menu', 2003, 'menu_name', 'zh-TW', '多語言修改', 'admin', NOW(), NOW()),
('menu', 2003, 'menu_name', 'en-US', 'Edit Translation', 'admin', NOW(), NOW()),
('menu', 2003, 'menu_name', 'ja-JP', '翻訳編集', 'admin', NOW(), NOW());

-- 多语言删除 (menu_id = 2004)
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2004, 'menu_name', 'zh-CN', '多语言删除', 'admin', NOW(), NOW()),
('menu', 2004, 'menu_name', 'zh-TW', '多語言刪除', 'admin', NOW(), NOW()),
('menu', 2004, 'menu_name', 'en-US', 'Delete Translation', 'admin', NOW(), NOW()),
('menu', 2004, 'menu_name', 'ja-JP', '翻訳削除', 'admin', NOW(), NOW());

