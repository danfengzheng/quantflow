-- ============================================
-- 字典类型多语言初始化脚本
-- ============================================
-- 说明：为所有字典类型添加多语言翻译（zh-CN, zh-TW, en-US, ja-JP）
-- 执行前请确保 sys_i18n_translation 表已创建

-- 删除已存在的字典类型多语言数据（可选，如果需要重新初始化）
-- DELETE FROM `sys_i18n_translation` WHERE `entity_type` = 'dict_type' AND `field_name` = 'dict_name';

-- ============================================
-- 系统字典类型
-- ============================================
-- 1	用户性别
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 1, 'dict_name', 'zh-CN', '用户性别', 'admin', NOW(), NOW()),
('dict_type', 1, 'dict_name', 'zh-TW', '用戶性別', 'admin', NOW(), NOW()),
('dict_type', 1, 'dict_name', 'en-US', 'User Gender', 'admin', NOW(), NOW()),
('dict_type', 1, 'dict_name', 'ja-JP', 'ユーザー性別', 'admin', NOW(), NOW());

-- 2	菜单状态
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 2, 'dict_name', 'zh-CN', '菜单状态', 'admin', NOW(), NOW()),
('dict_type', 2, 'dict_name', 'zh-TW', '菜單狀態', 'admin', NOW(), NOW()),
('dict_type', 2, 'dict_name', 'en-US', 'Menu Status', 'admin', NOW(), NOW()),
('dict_type', 2, 'dict_name', 'ja-JP', 'メニューステータス', 'admin', NOW(), NOW());

-- 3	系统开关
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 3, 'dict_name', 'zh-CN', '系统开关', 'admin', NOW(), NOW()),
('dict_type', 3, 'dict_name', 'zh-TW', '系統開關', 'admin', NOW(), NOW()),
('dict_type', 3, 'dict_name', 'en-US', 'System Switch', 'admin', NOW(), NOW()),
('dict_type', 3, 'dict_name', 'ja-JP', 'システムスイッチ', 'admin', NOW(), NOW());

-- 4	任务状态
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 4, 'dict_name', 'zh-CN', '任务状态', 'admin', NOW(), NOW()),
('dict_type', 4, 'dict_name', 'zh-TW', '任務狀態', 'admin', NOW(), NOW()),
('dict_type', 4, 'dict_name', 'en-US', 'Task Status', 'admin', NOW(), NOW()),
('dict_type', 4, 'dict_name', 'ja-JP', 'タスクステータス', 'admin', NOW(), NOW());

-- 5	任务分组
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 5, 'dict_name', 'zh-CN', '任务分组', 'admin', NOW(), NOW()),
('dict_type', 5, 'dict_name', 'zh-TW', '任務分組', 'admin', NOW(), NOW()),
('dict_type', 5, 'dict_name', 'en-US', 'Task Group', 'admin', NOW(), NOW()),
('dict_type', 5, 'dict_name', 'ja-JP', 'タスクグループ', 'admin', NOW(), NOW());

-- 6	系统是否
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 6, 'dict_name', 'zh-CN', '系统是否', 'admin', NOW(), NOW()),
('dict_type', 6, 'dict_name', 'zh-TW', '系統是否', 'admin', NOW(), NOW()),
('dict_type', 6, 'dict_name', 'en-US', 'System Yes/No', 'admin', NOW(), NOW()),
('dict_type', 6, 'dict_name', 'ja-JP', 'システムはい/いいえ', 'admin', NOW(), NOW());

-- 7	通知类型
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 7, 'dict_name', 'zh-CN', '通知类型', 'admin', NOW(), NOW()),
('dict_type', 7, 'dict_name', 'zh-TW', '通知類型', 'admin', NOW(), NOW()),
('dict_type', 7, 'dict_name', 'en-US', 'Notice Type', 'admin', NOW(), NOW()),
('dict_type', 7, 'dict_name', 'ja-JP', '通知タイプ', 'admin', NOW(), NOW());

-- 8	通知状态
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 8, 'dict_name', 'zh-CN', '通知状态', 'admin', NOW(), NOW()),
('dict_type', 8, 'dict_name', 'zh-TW', '通知狀態', 'admin', NOW(), NOW()),
('dict_type', 8, 'dict_name', 'en-US', 'Notice Status', 'admin', NOW(), NOW()),
('dict_type', 8, 'dict_name', 'ja-JP', '通知ステータス', 'admin', NOW(), NOW());

-- 9	操作类型
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 9, 'dict_name', 'zh-CN', '操作类型', 'admin', NOW(), NOW()),
('dict_type', 9, 'dict_name', 'zh-TW', '操作類型', 'admin', NOW(), NOW()),
('dict_type', 9, 'dict_name', 'en-US', 'Operation Type', 'admin', NOW(), NOW()),
('dict_type', 9, 'dict_name', 'ja-JP', '操作タイプ', 'admin', NOW(), NOW());

-- 10	系统状态
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 10, 'dict_name', 'zh-CN', '系统状态', 'admin', NOW(), NOW()),
('dict_type', 10, 'dict_name', 'zh-TW', '系統狀態', 'admin', NOW(), NOW()),
('dict_type', 10, 'dict_name', 'en-US', 'System Status', 'admin', NOW(), NOW()),
('dict_type', 10, 'dict_name', 'ja-JP', 'システムステータス', 'admin', NOW(), NOW());

-- ============================================
-- 量化交易模块字典类型
-- ============================================
-- 100	交易所类型
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 100, 'dict_name', 'zh-CN', '交易所类型', 'admin', NOW(), NOW()),
('dict_type', 100, 'dict_name', 'zh-TW', '交易所類型', 'admin', NOW(), NOW()),
('dict_type', 100, 'dict_name', 'en-US', 'Exchange Type', 'admin', NOW(), NOW()),
('dict_type', 100, 'dict_name', 'ja-JP', '取引所タイプ', 'admin', NOW(), NOW());

-- 101	订单方向
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 101, 'dict_name', 'zh-CN', '订单方向', 'admin', NOW(), NOW()),
('dict_type', 101, 'dict_name', 'zh-TW', '訂單方向', 'admin', NOW(), NOW()),
('dict_type', 101, 'dict_name', 'en-US', 'Order Direction', 'admin', NOW(), NOW()),
('dict_type', 101, 'dict_name', 'ja-JP', '注文方向', 'admin', NOW(), NOW());

-- 102	订单类型
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 102, 'dict_name', 'zh-CN', '订单类型', 'admin', NOW(), NOW()),
('dict_type', 102, 'dict_name', 'zh-TW', '訂單類型', 'admin', NOW(), NOW()),
('dict_type', 102, 'dict_name', 'en-US', 'Order Type', 'admin', NOW(), NOW()),
('dict_type', 102, 'dict_name', 'ja-JP', '注文タイプ', 'admin', NOW(), NOW());

-- 103	策略类型
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 103, 'dict_name', 'zh-CN', '策略类型', 'admin', NOW(), NOW()),
('dict_type', 103, 'dict_name', 'zh-TW', '策略類型', 'admin', NOW(), NOW()),
('dict_type', 103, 'dict_name', 'en-US', 'Strategy Type', 'admin', NOW(), NOW()),
('dict_type', 103, 'dict_name', 'ja-JP', '戦略タイプ', 'admin', NOW(), NOW());

-- 104	订单状态
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 104, 'dict_name', 'zh-CN', '订单状态', 'admin', NOW(), NOW()),
('dict_type', 104, 'dict_name', 'zh-TW', '訂單狀態', 'admin', NOW(), NOW()),
('dict_type', 104, 'dict_name', 'en-US', 'Order Status', 'admin', NOW(), NOW()),
('dict_type', 104, 'dict_name', 'ja-JP', '注文ステータス', 'admin', NOW(), NOW());

-- 105	K线周期
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 105, 'dict_name', 'zh-CN', 'K线周期', 'admin', NOW(), NOW()),
('dict_type', 105, 'dict_name', 'zh-TW', 'K線週期', 'admin', NOW(), NOW()),
('dict_type', 105, 'dict_name', 'en-US', 'K-Line Period', 'admin', NOW(), NOW()),
('dict_type', 105, 'dict_name', 'ja-JP', 'K線周期', 'admin', NOW(), NOW());

-- 106	策略状态
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 106, 'dict_name', 'zh-CN', '策略状态', 'admin', NOW(), NOW()),
('dict_type', 106, 'dict_name', 'zh-TW', '策略狀態', 'admin', NOW(), NOW()),
('dict_type', 106, 'dict_name', 'en-US', 'Strategy Status', 'admin', NOW(), NOW()),
('dict_type', 106, 'dict_name', 'ja-JP', '戦略ステータス', 'admin', NOW(), NOW());

-- 107	交易对配置
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 107, 'dict_name', 'zh-CN', '交易对配置', 'admin', NOW(), NOW()),
('dict_type', 107, 'dict_name', 'zh-TW', '交易對配置', 'admin', NOW(), NOW()),
('dict_type', 107, 'dict_name', 'en-US', 'Trading Pair Config', 'admin', NOW(), NOW()),
('dict_type', 107, 'dict_name', 'ja-JP', '取引ペア設定', 'admin', NOW(), NOW());

-- 108	交易对分组
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 108, 'dict_name', 'zh-CN', '交易对分组', 'admin', NOW(), NOW()),
('dict_type', 108, 'dict_name', 'zh-TW', '交易對分組', 'admin', NOW(), NOW()),
('dict_type', 108, 'dict_name', 'en-US', 'Trading Pair Group', 'admin', NOW(), NOW()),
('dict_type', 108, 'dict_name', 'ja-JP', '取引ペアグループ', 'admin', NOW(), NOW());

-- 109	风险等级
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_type', 109, 'dict_name', 'zh-CN', '风险等级', 'admin', NOW(), NOW()),
('dict_type', 109, 'dict_name', 'zh-TW', '風險等級', 'admin', NOW(), NOW()),
('dict_type', 109, 'dict_name', 'en-US', 'Risk Level', 'admin', NOW(), NOW()),
('dict_type', 109, 'dict_name', 'ja-JP', 'リスクレベル', 'admin', NOW(), NOW());

