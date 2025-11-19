-- ============================================
-- 菜单多语言初始化脚本
-- ============================================
-- 说明：为所有菜单添加多语言翻译（zh-CN, zh-TW, en-US, ja-JP）
-- 执行前请确保 sys_i18n_translation 表已创建

-- 删除已存在的菜单多语言数据（可选，如果需要重新初始化）
-- DELETE FROM `sys_i18n_translation` WHERE `entity_type` = 'menu' AND `field_name` = 'menu_name';

-- ============================================
-- 一级菜单
-- ============================================
-- 1	系统管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1, 'menu_name', 'zh-CN', '系统管理', 'admin', NOW(), NOW()),
('menu', 1, 'menu_name', 'zh-TW', '系統管理', 'admin', NOW(), NOW()),
('menu', 1, 'menu_name', 'en-US', 'System Management', 'admin', NOW(), NOW()),
('menu', 1, 'menu_name', 'ja-JP', 'システム管理', 'admin', NOW(), NOW());

-- 2	系统监控
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2, 'menu_name', 'zh-CN', '系统监控', 'admin', NOW(), NOW()),
('menu', 2, 'menu_name', 'zh-TW', '系統監控', 'admin', NOW(), NOW()),
('menu', 2, 'menu_name', 'en-US', 'System Monitor', 'admin', NOW(), NOW()),
('menu', 2, 'menu_name', 'ja-JP', 'システム監視', 'admin', NOW(), NOW());

-- 3	系统工具
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 3, 'menu_name', 'zh-CN', '系统工具', 'admin', NOW(), NOW()),
('menu', 3, 'menu_name', 'zh-TW', '系統工具', 'admin', NOW(), NOW()),
('menu', 3, 'menu_name', 'en-US', 'System Tools', 'admin', NOW(), NOW()),
('menu', 3, 'menu_name', 'ja-JP', 'システムツール', 'admin', NOW(), NOW());

-- 4	若依官网
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 4, 'menu_name', 'zh-CN', '若依官网', 'admin', NOW(), NOW()),
('menu', 4, 'menu_name', 'zh-TW', '若依官網', 'admin', NOW(), NOW()),
('menu', 4, 'menu_name', 'en-US', 'RuoYi Official', 'admin', NOW(), NOW()),
('menu', 4, 'menu_name', 'ja-JP', 'RuoYi公式サイト', 'admin', NOW(), NOW());

-- ============================================
-- 二级菜单
-- ============================================
-- 100	用户管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 100, 'menu_name', 'zh-CN', '用户管理', 'admin', NOW(), NOW()),
('menu', 100, 'menu_name', 'zh-TW', '用戶管理', 'admin', NOW(), NOW()),
('menu', 100, 'menu_name', 'en-US', 'User Management', 'admin', NOW(), NOW()),
('menu', 100, 'menu_name', 'ja-JP', 'ユーザー管理', 'admin', NOW(), NOW());

-- 101	角色管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 101, 'menu_name', 'zh-CN', '角色管理', 'admin', NOW(), NOW()),
('menu', 101, 'menu_name', 'zh-TW', '角色管理', 'admin', NOW(), NOW()),
('menu', 101, 'menu_name', 'en-US', 'Role Management', 'admin', NOW(), NOW()),
('menu', 101, 'menu_name', 'ja-JP', 'ロール管理', 'admin', NOW(), NOW());

-- 102	菜单管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 102, 'menu_name', 'zh-CN', '菜单管理', 'admin', NOW(), NOW()),
('menu', 102, 'menu_name', 'zh-TW', '菜單管理', 'admin', NOW(), NOW()),
('menu', 102, 'menu_name', 'en-US', 'Menu Management', 'admin', NOW(), NOW()),
('menu', 102, 'menu_name', 'ja-JP', 'メニュー管理', 'admin', NOW(), NOW());

-- 103	部门管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 103, 'menu_name', 'zh-CN', '部门管理', 'admin', NOW(), NOW()),
('menu', 103, 'menu_name', 'zh-TW', '部門管理', 'admin', NOW(), NOW()),
('menu', 103, 'menu_name', 'en-US', 'Department Management', 'admin', NOW(), NOW()),
('menu', 103, 'menu_name', 'ja-JP', '部門管理', 'admin', NOW(), NOW());

-- 104	岗位管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 104, 'menu_name', 'zh-CN', '岗位管理', 'admin', NOW(), NOW()),
('menu', 104, 'menu_name', 'zh-TW', '崗位管理', 'admin', NOW(), NOW()),
('menu', 104, 'menu_name', 'en-US', 'Post Management', 'admin', NOW(), NOW()),
('menu', 104, 'menu_name', 'ja-JP', 'ポスト管理', 'admin', NOW(), NOW());

-- 105	字典管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 105, 'menu_name', 'zh-CN', '字典管理', 'admin', NOW(), NOW()),
('menu', 105, 'menu_name', 'zh-TW', '字典管理', 'admin', NOW(), NOW()),
('menu', 105, 'menu_name', 'en-US', 'Dictionary Management', 'admin', NOW(), NOW()),
('menu', 105, 'menu_name', 'ja-JP', '辞書管理', 'admin', NOW(), NOW());

-- 106	参数设置
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 106, 'menu_name', 'zh-CN', '参数设置', 'admin', NOW(), NOW()),
('menu', 106, 'menu_name', 'zh-TW', '參數設置', 'admin', NOW(), NOW()),
('menu', 106, 'menu_name', 'en-US', 'Parameter Settings', 'admin', NOW(), NOW()),
('menu', 106, 'menu_name', 'ja-JP', 'パラメータ設定', 'admin', NOW(), NOW());

-- 107	通知公告
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 107, 'menu_name', 'zh-CN', '通知公告', 'admin', NOW(), NOW()),
('menu', 107, 'menu_name', 'zh-TW', '通知公告', 'admin', NOW(), NOW()),
('menu', 107, 'menu_name', 'en-US', 'Notice', 'admin', NOW(), NOW()),
('menu', 107, 'menu_name', 'ja-JP', 'お知らせ', 'admin', NOW(), NOW());

-- 108	日志管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 108, 'menu_name', 'zh-CN', '日志管理', 'admin', NOW(), NOW()),
('menu', 108, 'menu_name', 'zh-TW', '日誌管理', 'admin', NOW(), NOW()),
('menu', 108, 'menu_name', 'en-US', 'Log Management', 'admin', NOW(), NOW()),
('menu', 108, 'menu_name', 'ja-JP', 'ログ管理', 'admin', NOW(), NOW());

-- 109	在线用户
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 109, 'menu_name', 'zh-CN', '在线用户', 'admin', NOW(), NOW()),
('menu', 109, 'menu_name', 'zh-TW', '在線用戶', 'admin', NOW(), NOW()),
('menu', 109, 'menu_name', 'en-US', 'Online Users', 'admin', NOW(), NOW()),
('menu', 109, 'menu_name', 'ja-JP', 'オンラインユーザー', 'admin', NOW(), NOW());

-- 110	定时任务
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 110, 'menu_name', 'zh-CN', '定时任务', 'admin', NOW(), NOW()),
('menu', 110, 'menu_name', 'zh-TW', '定時任務', 'admin', NOW(), NOW()),
('menu', 110, 'menu_name', 'en-US', 'Scheduled Tasks', 'admin', NOW(), NOW()),
('menu', 110, 'menu_name', 'ja-JP', 'スケジュールタスク', 'admin', NOW(), NOW());

-- 111	数据监控
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 111, 'menu_name', 'zh-CN', '数据监控', 'admin', NOW(), NOW()),
('menu', 111, 'menu_name', 'zh-TW', '數據監控', 'admin', NOW(), NOW()),
('menu', 111, 'menu_name', 'en-US', 'Data Monitor', 'admin', NOW(), NOW()),
('menu', 111, 'menu_name', 'ja-JP', 'データ監視', 'admin', NOW(), NOW());

-- 112	服务监控
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 112, 'menu_name', 'zh-CN', '服务监控', 'admin', NOW(), NOW()),
('menu', 112, 'menu_name', 'zh-TW', '服務監控', 'admin', NOW(), NOW()),
('menu', 112, 'menu_name', 'en-US', 'Service Monitor', 'admin', NOW(), NOW()),
('menu', 112, 'menu_name', 'ja-JP', 'サービス監視', 'admin', NOW(), NOW());

-- 113	缓存监控
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 113, 'menu_name', 'zh-CN', '缓存监控', 'admin', NOW(), NOW()),
('menu', 113, 'menu_name', 'zh-TW', '緩存監控', 'admin', NOW(), NOW()),
('menu', 113, 'menu_name', 'en-US', 'Cache Monitor', 'admin', NOW(), NOW()),
('menu', 113, 'menu_name', 'ja-JP', 'キャッシュ監視', 'admin', NOW(), NOW());

-- 114	缓存列表
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 114, 'menu_name', 'zh-CN', '缓存列表', 'admin', NOW(), NOW()),
('menu', 114, 'menu_name', 'zh-TW', '緩存列表', 'admin', NOW(), NOW()),
('menu', 114, 'menu_name', 'en-US', 'Cache List', 'admin', NOW(), NOW()),
('menu', 114, 'menu_name', 'ja-JP', 'キャッシュリスト', 'admin', NOW(), NOW());

-- 115	表单构建
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 115, 'menu_name', 'zh-CN', '表单构建', 'admin', NOW(), NOW()),
('menu', 115, 'menu_name', 'zh-TW', '表單構建', 'admin', NOW(), NOW()),
('menu', 115, 'menu_name', 'en-US', 'Form Builder', 'admin', NOW(), NOW()),
('menu', 115, 'menu_name', 'ja-JP', 'フォームビルダー', 'admin', NOW(), NOW());

-- 116	代码生成
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 116, 'menu_name', 'zh-CN', '代码生成', 'admin', NOW(), NOW()),
('menu', 116, 'menu_name', 'zh-TW', '代碼生成', 'admin', NOW(), NOW()),
('menu', 116, 'menu_name', 'en-US', 'Code Generation', 'admin', NOW(), NOW()),
('menu', 116, 'menu_name', 'ja-JP', 'コード生成', 'admin', NOW(), NOW());

-- 117	系统接口
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 117, 'menu_name', 'zh-CN', '系统接口', 'admin', NOW(), NOW()),
('menu', 117, 'menu_name', 'zh-TW', '系統接口', 'admin', NOW(), NOW()),
('menu', 117, 'menu_name', 'en-US', 'System API', 'admin', NOW(), NOW()),
('menu', 117, 'menu_name', 'ja-JP', 'システムAPI', 'admin', NOW(), NOW());

-- ============================================
-- 三级菜单（按钮）
-- ============================================
-- 500	操作日志
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 500, 'menu_name', 'zh-CN', '操作日志', 'admin', NOW(), NOW()),
('menu', 500, 'menu_name', 'zh-TW', '操作日誌', 'admin', NOW(), NOW()),
('menu', 500, 'menu_name', 'en-US', 'Operation Log', 'admin', NOW(), NOW()),
('menu', 500, 'menu_name', 'ja-JP', '操作ログ', 'admin', NOW(), NOW());

-- 501	登录日志
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 501, 'menu_name', 'zh-CN', '登录日志', 'admin', NOW(), NOW()),
('menu', 501, 'menu_name', 'zh-TW', '登錄日誌', 'admin', NOW(), NOW()),
('menu', 501, 'menu_name', 'en-US', 'Login Log', 'admin', NOW(), NOW()),
('menu', 501, 'menu_name', 'ja-JP', 'ログインログ', 'admin', NOW(), NOW());

-- 1000	用户查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1000, 'menu_name', 'zh-CN', '用户查询', 'admin', NOW(), NOW()),
('menu', 1000, 'menu_name', 'zh-TW', '用戶查詢', 'admin', NOW(), NOW()),
('menu', 1000, 'menu_name', 'en-US', 'User Query', 'admin', NOW(), NOW()),
('menu', 1000, 'menu_name', 'ja-JP', 'ユーザー検索', 'admin', NOW(), NOW());

-- 1001	用户新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1001, 'menu_name', 'zh-CN', '用户新增', 'admin', NOW(), NOW()),
('menu', 1001, 'menu_name', 'zh-TW', '用戶新增', 'admin', NOW(), NOW()),
('menu', 1001, 'menu_name', 'en-US', 'User Add', 'admin', NOW(), NOW()),
('menu', 1001, 'menu_name', 'ja-JP', 'ユーザー追加', 'admin', NOW(), NOW());

-- 1002	用户修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1002, 'menu_name', 'zh-CN', '用户修改', 'admin', NOW(), NOW()),
('menu', 1002, 'menu_name', 'zh-TW', '用戶修改', 'admin', NOW(), NOW()),
('menu', 1002, 'menu_name', 'en-US', 'User Edit', 'admin', NOW(), NOW()),
('menu', 1002, 'menu_name', 'ja-JP', 'ユーザー編集', 'admin', NOW(), NOW());

-- 1003	用户删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1003, 'menu_name', 'zh-CN', '用户删除', 'admin', NOW(), NOW()),
('menu', 1003, 'menu_name', 'zh-TW', '用戶刪除', 'admin', NOW(), NOW()),
('menu', 1003, 'menu_name', 'en-US', 'User Delete', 'admin', NOW(), NOW()),
('menu', 1003, 'menu_name', 'ja-JP', 'ユーザー削除', 'admin', NOW(), NOW());

-- 1004	用户导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1004, 'menu_name', 'zh-CN', '用户导出', 'admin', NOW(), NOW()),
('menu', 1004, 'menu_name', 'zh-TW', '用戶導出', 'admin', NOW(), NOW()),
('menu', 1004, 'menu_name', 'en-US', 'User Export', 'admin', NOW(), NOW()),
('menu', 1004, 'menu_name', 'ja-JP', 'ユーザーエクスポート', 'admin', NOW(), NOW());

-- 1005	用户导入
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1005, 'menu_name', 'zh-CN', '用户导入', 'admin', NOW(), NOW()),
('menu', 1005, 'menu_name', 'zh-TW', '用戶導入', 'admin', NOW(), NOW()),
('menu', 1005, 'menu_name', 'en-US', 'User Import', 'admin', NOW(), NOW()),
('menu', 1005, 'menu_name', 'ja-JP', 'ユーザーインポート', 'admin', NOW(), NOW());

-- 1006	重置密码
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1006, 'menu_name', 'zh-CN', '重置密码', 'admin', NOW(), NOW()),
('menu', 1006, 'menu_name', 'zh-TW', '重置密碼', 'admin', NOW(), NOW()),
('menu', 1006, 'menu_name', 'en-US', 'Reset Password', 'admin', NOW(), NOW()),
('menu', 1006, 'menu_name', 'ja-JP', 'パスワードリセット', 'admin', NOW(), NOW());

-- 1007	角色查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1007, 'menu_name', 'zh-CN', '角色查询', 'admin', NOW(), NOW()),
('menu', 1007, 'menu_name', 'zh-TW', '角色查詢', 'admin', NOW(), NOW()),
('menu', 1007, 'menu_name', 'en-US', 'Role Query', 'admin', NOW(), NOW()),
('menu', 1007, 'menu_name', 'ja-JP', 'ロール検索', 'admin', NOW(), NOW());

-- 1008	角色新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1008, 'menu_name', 'zh-CN', '角色新增', 'admin', NOW(), NOW()),
('menu', 1008, 'menu_name', 'zh-TW', '角色新增', 'admin', NOW(), NOW()),
('menu', 1008, 'menu_name', 'en-US', 'Role Add', 'admin', NOW(), NOW()),
('menu', 1008, 'menu_name', 'ja-JP', 'ロール追加', 'admin', NOW(), NOW());

-- 1009	角色修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1009, 'menu_name', 'zh-CN', '角色修改', 'admin', NOW(), NOW()),
('menu', 1009, 'menu_name', 'zh-TW', '角色修改', 'admin', NOW(), NOW()),
('menu', 1009, 'menu_name', 'en-US', 'Role Edit', 'admin', NOW(), NOW()),
('menu', 1009, 'menu_name', 'ja-JP', 'ロール編集', 'admin', NOW(), NOW());

-- 1010	角色删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1010, 'menu_name', 'zh-CN', '角色删除', 'admin', NOW(), NOW()),
('menu', 1010, 'menu_name', 'zh-TW', '角色刪除', 'admin', NOW(), NOW()),
('menu', 1010, 'menu_name', 'en-US', 'Role Delete', 'admin', NOW(), NOW()),
('menu', 1010, 'menu_name', 'ja-JP', 'ロール削除', 'admin', NOW(), NOW());

-- 1011	角色导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1011, 'menu_name', 'zh-CN', '角色导出', 'admin', NOW(), NOW()),
('menu', 1011, 'menu_name', 'zh-TW', '角色導出', 'admin', NOW(), NOW()),
('menu', 1011, 'menu_name', 'en-US', 'Role Export', 'admin', NOW(), NOW()),
('menu', 1011, 'menu_name', 'ja-JP', 'ロールエクスポート', 'admin', NOW(), NOW());

-- 1012	菜单查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1012, 'menu_name', 'zh-CN', '菜单查询', 'admin', NOW(), NOW()),
('menu', 1012, 'menu_name', 'zh-TW', '菜單查詢', 'admin', NOW(), NOW()),
('menu', 1012, 'menu_name', 'en-US', 'Menu Query', 'admin', NOW(), NOW()),
('menu', 1012, 'menu_name', 'ja-JP', 'メニュー検索', 'admin', NOW(), NOW());

-- 1013	菜单新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1013, 'menu_name', 'zh-CN', '菜单新增', 'admin', NOW(), NOW()),
('menu', 1013, 'menu_name', 'zh-TW', '菜單新增', 'admin', NOW(), NOW()),
('menu', 1013, 'menu_name', 'en-US', 'Menu Add', 'admin', NOW(), NOW()),
('menu', 1013, 'menu_name', 'ja-JP', 'メニュー追加', 'admin', NOW(), NOW());

-- 1014	菜单修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1014, 'menu_name', 'zh-CN', '菜单修改', 'admin', NOW(), NOW()),
('menu', 1014, 'menu_name', 'zh-TW', '菜單修改', 'admin', NOW(), NOW()),
('menu', 1014, 'menu_name', 'en-US', 'Menu Edit', 'admin', NOW(), NOW()),
('menu', 1014, 'menu_name', 'ja-JP', 'メニュー編集', 'admin', NOW(), NOW());

-- 1015	菜单删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1015, 'menu_name', 'zh-CN', '菜单删除', 'admin', NOW(), NOW()),
('menu', 1015, 'menu_name', 'zh-TW', '菜單刪除', 'admin', NOW(), NOW()),
('menu', 1015, 'menu_name', 'en-US', 'Menu Delete', 'admin', NOW(), NOW()),
('menu', 1015, 'menu_name', 'ja-JP', 'メニュー削除', 'admin', NOW(), NOW());

-- 1016	部门查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1016, 'menu_name', 'zh-CN', '部门查询', 'admin', NOW(), NOW()),
('menu', 1016, 'menu_name', 'zh-TW', '部門查詢', 'admin', NOW(), NOW()),
('menu', 1016, 'menu_name', 'en-US', 'Dept Query', 'admin', NOW(), NOW()),
('menu', 1016, 'menu_name', 'ja-JP', '部門検索', 'admin', NOW(), NOW());

-- 1017	部门新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1017, 'menu_name', 'zh-CN', '部门新增', 'admin', NOW(), NOW()),
('menu', 1017, 'menu_name', 'zh-TW', '部門新增', 'admin', NOW(), NOW()),
('menu', 1017, 'menu_name', 'en-US', 'Dept Add', 'admin', NOW(), NOW()),
('menu', 1017, 'menu_name', 'ja-JP', '部門追加', 'admin', NOW(), NOW());

-- 1018	部门修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1018, 'menu_name', 'zh-CN', '部门修改', 'admin', NOW(), NOW()),
('menu', 1018, 'menu_name', 'zh-TW', '部門修改', 'admin', NOW(), NOW()),
('menu', 1018, 'menu_name', 'en-US', 'Dept Edit', 'admin', NOW(), NOW()),
('menu', 1018, 'menu_name', 'ja-JP', '部門編集', 'admin', NOW(), NOW());

-- 1019	部门删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1019, 'menu_name', 'zh-CN', '部门删除', 'admin', NOW(), NOW()),
('menu', 1019, 'menu_name', 'zh-TW', '部門刪除', 'admin', NOW(), NOW()),
('menu', 1019, 'menu_name', 'en-US', 'Dept Delete', 'admin', NOW(), NOW()),
('menu', 1019, 'menu_name', 'ja-JP', '部門削除', 'admin', NOW(), NOW());

-- 1020	岗位查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1020, 'menu_name', 'zh-CN', '岗位查询', 'admin', NOW(), NOW()),
('menu', 1020, 'menu_name', 'zh-TW', '崗位查詢', 'admin', NOW(), NOW()),
('menu', 1020, 'menu_name', 'en-US', 'Post Query', 'admin', NOW(), NOW()),
('menu', 1020, 'menu_name', 'ja-JP', 'ポスト検索', 'admin', NOW(), NOW());

-- 1021	岗位新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1021, 'menu_name', 'zh-CN', '岗位新增', 'admin', NOW(), NOW()),
('menu', 1021, 'menu_name', 'zh-TW', '崗位新增', 'admin', NOW(), NOW()),
('menu', 1021, 'menu_name', 'en-US', 'Post Add', 'admin', NOW(), NOW()),
('menu', 1021, 'menu_name', 'ja-JP', 'ポスト追加', 'admin', NOW(), NOW());

-- 1022	岗位修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1022, 'menu_name', 'zh-CN', '岗位修改', 'admin', NOW(), NOW()),
('menu', 1022, 'menu_name', 'zh-TW', '崗位修改', 'admin', NOW(), NOW()),
('menu', 1022, 'menu_name', 'en-US', 'Post Edit', 'admin', NOW(), NOW()),
('menu', 1022, 'menu_name', 'ja-JP', 'ポスト編集', 'admin', NOW(), NOW());

-- 1023	岗位删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1023, 'menu_name', 'zh-CN', '岗位删除', 'admin', NOW(), NOW()),
('menu', 1023, 'menu_name', 'zh-TW', '崗位刪除', 'admin', NOW(), NOW()),
('menu', 1023, 'menu_name', 'en-US', 'Post Delete', 'admin', NOW(), NOW()),
('menu', 1023, 'menu_name', 'ja-JP', 'ポスト削除', 'admin', NOW(), NOW());

-- 1024	岗位导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1024, 'menu_name', 'zh-CN', '岗位导出', 'admin', NOW(), NOW()),
('menu', 1024, 'menu_name', 'zh-TW', '崗位導出', 'admin', NOW(), NOW()),
('menu', 1024, 'menu_name', 'en-US', 'Post Export', 'admin', NOW(), NOW()),
('menu', 1024, 'menu_name', 'ja-JP', 'ポストエクスポート', 'admin', NOW(), NOW());

-- 1025	字典查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1025, 'menu_name', 'zh-CN', '字典查询', 'admin', NOW(), NOW()),
('menu', 1025, 'menu_name', 'zh-TW', '字典查詢', 'admin', NOW(), NOW()),
('menu', 1025, 'menu_name', 'en-US', 'Dict Query', 'admin', NOW(), NOW()),
('menu', 1025, 'menu_name', 'ja-JP', '辞書検索', 'admin', NOW(), NOW());

-- 1026	字典新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1026, 'menu_name', 'zh-CN', '字典新增', 'admin', NOW(), NOW()),
('menu', 1026, 'menu_name', 'zh-TW', '字典新增', 'admin', NOW(), NOW()),
('menu', 1026, 'menu_name', 'en-US', 'Dict Add', 'admin', NOW(), NOW()),
('menu', 1026, 'menu_name', 'ja-JP', '辞書追加', 'admin', NOW(), NOW());

-- 1027	字典修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1027, 'menu_name', 'zh-CN', '字典修改', 'admin', NOW(), NOW()),
('menu', 1027, 'menu_name', 'zh-TW', '字典修改', 'admin', NOW(), NOW()),
('menu', 1027, 'menu_name', 'en-US', 'Dict Edit', 'admin', NOW(), NOW()),
('menu', 1027, 'menu_name', 'ja-JP', '辞書編集', 'admin', NOW(), NOW());

-- 1028	字典删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1028, 'menu_name', 'zh-CN', '字典删除', 'admin', NOW(), NOW()),
('menu', 1028, 'menu_name', 'zh-TW', '字典刪除', 'admin', NOW(), NOW()),
('menu', 1028, 'menu_name', 'en-US', 'Dict Delete', 'admin', NOW(), NOW()),
('menu', 1028, 'menu_name', 'ja-JP', '辞書削除', 'admin', NOW(), NOW());

-- 1029	字典导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1029, 'menu_name', 'zh-CN', '字典导出', 'admin', NOW(), NOW()),
('menu', 1029, 'menu_name', 'zh-TW', '字典導出', 'admin', NOW(), NOW()),
('menu', 1029, 'menu_name', 'en-US', 'Dict Export', 'admin', NOW(), NOW()),
('menu', 1029, 'menu_name', 'ja-JP', '辞書エクスポート', 'admin', NOW(), NOW());

-- 1030	参数查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1030, 'menu_name', 'zh-CN', '参数查询', 'admin', NOW(), NOW()),
('menu', 1030, 'menu_name', 'zh-TW', '參數查詢', 'admin', NOW(), NOW()),
('menu', 1030, 'menu_name', 'en-US', 'Parameter Query', 'admin', NOW(), NOW()),
('menu', 1030, 'menu_name', 'ja-JP', 'パラメータ検索', 'admin', NOW(), NOW());

-- 1031	参数新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1031, 'menu_name', 'zh-CN', '参数新增', 'admin', NOW(), NOW()),
('menu', 1031, 'menu_name', 'zh-TW', '參數新增', 'admin', NOW(), NOW()),
('menu', 1031, 'menu_name', 'en-US', 'Parameter Add', 'admin', NOW(), NOW()),
('menu', 1031, 'menu_name', 'ja-JP', 'パラメータ追加', 'admin', NOW(), NOW());

-- 1032	参数修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1032, 'menu_name', 'zh-CN', '参数修改', 'admin', NOW(), NOW()),
('menu', 1032, 'menu_name', 'zh-TW', '參數修改', 'admin', NOW(), NOW()),
('menu', 1032, 'menu_name', 'en-US', 'Parameter Edit', 'admin', NOW(), NOW()),
('menu', 1032, 'menu_name', 'ja-JP', 'パラメータ編集', 'admin', NOW(), NOW());

-- 1033	参数删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1033, 'menu_name', 'zh-CN', '参数删除', 'admin', NOW(), NOW()),
('menu', 1033, 'menu_name', 'zh-TW', '參數刪除', 'admin', NOW(), NOW()),
('menu', 1033, 'menu_name', 'en-US', 'Parameter Delete', 'admin', NOW(), NOW()),
('menu', 1033, 'menu_name', 'ja-JP', 'パラメータ削除', 'admin', NOW(), NOW());

-- 1034	参数导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1034, 'menu_name', 'zh-CN', '参数导出', 'admin', NOW(), NOW()),
('menu', 1034, 'menu_name', 'zh-TW', '參數導出', 'admin', NOW(), NOW()),
('menu', 1034, 'menu_name', 'en-US', 'Parameter Export', 'admin', NOW(), NOW()),
('menu', 1034, 'menu_name', 'ja-JP', 'パラメータエクスポート', 'admin', NOW(), NOW());

-- 1035	公告查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1035, 'menu_name', 'zh-CN', '公告查询', 'admin', NOW(), NOW()),
('menu', 1035, 'menu_name', 'zh-TW', '公告查詢', 'admin', NOW(), NOW()),
('menu', 1035, 'menu_name', 'en-US', 'Notice Query', 'admin', NOW(), NOW()),
('menu', 1035, 'menu_name', 'ja-JP', 'お知らせ検索', 'admin', NOW(), NOW());

-- 1036	公告新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1036, 'menu_name', 'zh-CN', '公告新增', 'admin', NOW(), NOW()),
('menu', 1036, 'menu_name', 'zh-TW', '公告新增', 'admin', NOW(), NOW()),
('menu', 1036, 'menu_name', 'en-US', 'Notice Add', 'admin', NOW(), NOW()),
('menu', 1036, 'menu_name', 'ja-JP', 'お知らせ追加', 'admin', NOW(), NOW());

-- 1037	公告修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1037, 'menu_name', 'zh-CN', '公告修改', 'admin', NOW(), NOW()),
('menu', 1037, 'menu_name', 'zh-TW', '公告修改', 'admin', NOW(), NOW()),
('menu', 1037, 'menu_name', 'en-US', 'Notice Edit', 'admin', NOW(), NOW()),
('menu', 1037, 'menu_name', 'ja-JP', 'お知らせ編集', 'admin', NOW(), NOW());

-- 1038	公告删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1038, 'menu_name', 'zh-CN', '公告删除', 'admin', NOW(), NOW()),
('menu', 1038, 'menu_name', 'zh-TW', '公告刪除', 'admin', NOW(), NOW()),
('menu', 1038, 'menu_name', 'en-US', 'Notice Delete', 'admin', NOW(), NOW()),
('menu', 1038, 'menu_name', 'ja-JP', 'お知らせ削除', 'admin', NOW(), NOW());

-- 1039	操作查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1039, 'menu_name', 'zh-CN', '操作查询', 'admin', NOW(), NOW()),
('menu', 1039, 'menu_name', 'zh-TW', '操作查詢', 'admin', NOW(), NOW()),
('menu', 1039, 'menu_name', 'en-US', 'Operation Query', 'admin', NOW(), NOW()),
('menu', 1039, 'menu_name', 'ja-JP', '操作検索', 'admin', NOW(), NOW());

-- 1040	操作删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1040, 'menu_name', 'zh-CN', '操作删除', 'admin', NOW(), NOW()),
('menu', 1040, 'menu_name', 'zh-TW', '操作刪除', 'admin', NOW(), NOW()),
('menu', 1040, 'menu_name', 'en-US', 'Operation Delete', 'admin', NOW(), NOW()),
('menu', 1040, 'menu_name', 'ja-JP', '操作削除', 'admin', NOW(), NOW());

-- 1041	日志导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1041, 'menu_name', 'zh-CN', '日志导出', 'admin', NOW(), NOW()),
('menu', 1041, 'menu_name', 'zh-TW', '日誌導出', 'admin', NOW(), NOW()),
('menu', 1041, 'menu_name', 'en-US', 'Log Export', 'admin', NOW(), NOW()),
('menu', 1041, 'menu_name', 'ja-JP', 'ログエクスポート', 'admin', NOW(), NOW());

-- 1042	登录查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1042, 'menu_name', 'zh-CN', '登录查询', 'admin', NOW(), NOW()),
('menu', 1042, 'menu_name', 'zh-TW', '登錄查詢', 'admin', NOW(), NOW()),
('menu', 1042, 'menu_name', 'en-US', 'Login Query', 'admin', NOW(), NOW()),
('menu', 1042, 'menu_name', 'ja-JP', 'ログイン検索', 'admin', NOW(), NOW());

-- 1043	登录删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1043, 'menu_name', 'zh-CN', '登录删除', 'admin', NOW(), NOW()),
('menu', 1043, 'menu_name', 'zh-TW', '登錄刪除', 'admin', NOW(), NOW()),
('menu', 1043, 'menu_name', 'en-US', 'Login Delete', 'admin', NOW(), NOW()),
('menu', 1043, 'menu_name', 'ja-JP', 'ログイン削除', 'admin', NOW(), NOW());

-- 1044	日志导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1044, 'menu_name', 'zh-CN', '日志导出', 'admin', NOW(), NOW()),
('menu', 1044, 'menu_name', 'zh-TW', '日誌導出', 'admin', NOW(), NOW()),
('menu', 1044, 'menu_name', 'en-US', 'Log Export', 'admin', NOW(), NOW()),
('menu', 1044, 'menu_name', 'ja-JP', 'ログエクスポート', 'admin', NOW(), NOW());

-- 1045	账户解锁
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1045, 'menu_name', 'zh-CN', '账户解锁', 'admin', NOW(), NOW()),
('menu', 1045, 'menu_name', 'zh-TW', '賬戶解鎖', 'admin', NOW(), NOW()),
('menu', 1045, 'menu_name', 'en-US', 'Account Unlock', 'admin', NOW(), NOW()),
('menu', 1045, 'menu_name', 'ja-JP', 'アカウントロック解除', 'admin', NOW(), NOW());

-- 1046	在线查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1046, 'menu_name', 'zh-CN', '在线查询', 'admin', NOW(), NOW()),
('menu', 1046, 'menu_name', 'zh-TW', '在線查詢', 'admin', NOW(), NOW()),
('menu', 1046, 'menu_name', 'en-US', 'Online Query', 'admin', NOW(), NOW()),
('menu', 1046, 'menu_name', 'ja-JP', 'オンライン検索', 'admin', NOW(), NOW());

-- 1047	批量强退
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1047, 'menu_name', 'zh-CN', '批量强退', 'admin', NOW(), NOW()),
('menu', 1047, 'menu_name', 'zh-TW', '批量強退', 'admin', NOW(), NOW()),
('menu', 1047, 'menu_name', 'en-US', 'Batch Force Logout', 'admin', NOW(), NOW()),
('menu', 1047, 'menu_name', 'ja-JP', '一括強制ログアウト', 'admin', NOW(), NOW());

-- 1048	单条强退
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1048, 'menu_name', 'zh-CN', '单条强退', 'admin', NOW(), NOW()),
('menu', 1048, 'menu_name', 'zh-TW', '單條強退', 'admin', NOW(), NOW()),
('menu', 1048, 'menu_name', 'en-US', 'Single Force Logout', 'admin', NOW(), NOW()),
('menu', 1048, 'menu_name', 'ja-JP', '個別強制ログアウト', 'admin', NOW(), NOW());

-- 1049	任务查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1049, 'menu_name', 'zh-CN', '任务查询', 'admin', NOW(), NOW()),
('menu', 1049, 'menu_name', 'zh-TW', '任務查詢', 'admin', NOW(), NOW()),
('menu', 1049, 'menu_name', 'en-US', 'Task Query', 'admin', NOW(), NOW()),
('menu', 1049, 'menu_name', 'ja-JP', 'タスク検索', 'admin', NOW(), NOW());

-- 1050	任务新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1050, 'menu_name', 'zh-CN', '任务新增', 'admin', NOW(), NOW()),
('menu', 1050, 'menu_name', 'zh-TW', '任務新增', 'admin', NOW(), NOW()),
('menu', 1050, 'menu_name', 'en-US', 'Task Add', 'admin', NOW(), NOW()),
('menu', 1050, 'menu_name', 'ja-JP', 'タスク追加', 'admin', NOW(), NOW());

-- 1051	任务修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1051, 'menu_name', 'zh-CN', '任务修改', 'admin', NOW(), NOW()),
('menu', 1051, 'menu_name', 'zh-TW', '任務修改', 'admin', NOW(), NOW()),
('menu', 1051, 'menu_name', 'en-US', 'Task Edit', 'admin', NOW(), NOW()),
('menu', 1051, 'menu_name', 'ja-JP', 'タスク編集', 'admin', NOW(), NOW());

-- 1052	任务删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1052, 'menu_name', 'zh-CN', '任务删除', 'admin', NOW(), NOW()),
('menu', 1052, 'menu_name', 'zh-TW', '任務刪除', 'admin', NOW(), NOW()),
('menu', 1052, 'menu_name', 'en-US', 'Task Delete', 'admin', NOW(), NOW()),
('menu', 1052, 'menu_name', 'ja-JP', 'タスク削除', 'admin', NOW(), NOW());

-- 1053	状态修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1053, 'menu_name', 'zh-CN', '状态修改', 'admin', NOW(), NOW()),
('menu', 1053, 'menu_name', 'zh-TW', '狀態修改', 'admin', NOW(), NOW()),
('menu', 1053, 'menu_name', 'en-US', 'Status Edit', 'admin', NOW(), NOW()),
('menu', 1053, 'menu_name', 'ja-JP', 'ステータス編集', 'admin', NOW(), NOW());

-- 1054	任务导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1054, 'menu_name', 'zh-CN', '任务导出', 'admin', NOW(), NOW()),
('menu', 1054, 'menu_name', 'zh-TW', '任務導出', 'admin', NOW(), NOW()),
('menu', 1054, 'menu_name', 'en-US', 'Task Export', 'admin', NOW(), NOW()),
('menu', 1054, 'menu_name', 'ja-JP', 'タスクエクスポート', 'admin', NOW(), NOW());

-- 1055	生成查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1055, 'menu_name', 'zh-CN', '生成查询', 'admin', NOW(), NOW()),
('menu', 1055, 'menu_name', 'zh-TW', '生成查詢', 'admin', NOW(), NOW()),
('menu', 1055, 'menu_name', 'en-US', 'Generate Query', 'admin', NOW(), NOW()),
('menu', 1055, 'menu_name', 'ja-JP', '生成検索', 'admin', NOW(), NOW());

-- 1056	生成修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1056, 'menu_name', 'zh-CN', '生成修改', 'admin', NOW(), NOW()),
('menu', 1056, 'menu_name', 'zh-TW', '生成修改', 'admin', NOW(), NOW()),
('menu', 1056, 'menu_name', 'en-US', 'Generate Edit', 'admin', NOW(), NOW()),
('menu', 1056, 'menu_name', 'ja-JP', '生成編集', 'admin', NOW(), NOW());

-- 1057	生成删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1057, 'menu_name', 'zh-CN', '生成删除', 'admin', NOW(), NOW()),
('menu', 1057, 'menu_name', 'zh-TW', '生成刪除', 'admin', NOW(), NOW()),
('menu', 1057, 'menu_name', 'en-US', 'Generate Delete', 'admin', NOW(), NOW()),
('menu', 1057, 'menu_name', 'ja-JP', '生成削除', 'admin', NOW(), NOW());

-- 1058	导入代码
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1058, 'menu_name', 'zh-CN', '导入代码', 'admin', NOW(), NOW()),
('menu', 1058, 'menu_name', 'zh-TW', '導入代碼', 'admin', NOW(), NOW()),
('menu', 1058, 'menu_name', 'en-US', 'Import Code', 'admin', NOW(), NOW()),
('menu', 1058, 'menu_name', 'ja-JP', 'コードインポート', 'admin', NOW(), NOW());

-- 1059	预览代码
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1059, 'menu_name', 'zh-CN', '预览代码', 'admin', NOW(), NOW()),
('menu', 1059, 'menu_name', 'zh-TW', '預覽代碼', 'admin', NOW(), NOW()),
('menu', 1059, 'menu_name', 'en-US', 'Preview Code', 'admin', NOW(), NOW()),
('menu', 1059, 'menu_name', 'ja-JP', 'コードプレビュー', 'admin', NOW(), NOW());

-- 1060	生成代码
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 1060, 'menu_name', 'zh-CN', '生成代码', 'admin', NOW(), NOW()),
('menu', 1060, 'menu_name', 'zh-TW', '生成代碼', 'admin', NOW(), NOW()),
('menu', 1060, 'menu_name', 'en-US', 'Generate Code', 'admin', NOW(), NOW()),
('menu', 1060, 'menu_name', 'ja-JP', 'コード生成', 'admin', NOW(), NOW());

-- ============================================
-- 量化交易模块
-- ============================================
-- 2000	量化交易
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2000, 'menu_name', 'zh-CN', '量化交易', 'admin', NOW(), NOW()),
('menu', 2000, 'menu_name', 'zh-TW', '量化交易', 'admin', NOW(), NOW()),
('menu', 2000, 'menu_name', 'en-US', 'Quantitative Trading', 'admin', NOW(), NOW()),
('menu', 2000, 'menu_name', 'ja-JP', '定量取引', 'admin', NOW(), NOW());

-- 2001	交易仪表盘
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2001, 'menu_name', 'zh-CN', '交易仪表盘', 'admin', NOW(), NOW()),
('menu', 2001, 'menu_name', 'zh-TW', '交易儀表盤', 'admin', NOW(), NOW()),
('menu', 2001, 'menu_name', 'en-US', 'Trading Dashboard', 'admin', NOW(), NOW()),
('menu', 2001, 'menu_name', 'ja-JP', '取引ダッシュボード', 'admin', NOW(), NOW());

-- 2002	行情管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2002, 'menu_name', 'zh-CN', '行情管理', 'admin', NOW(), NOW()),
('menu', 2002, 'menu_name', 'zh-TW', '行情管理', 'admin', NOW(), NOW()),
('menu', 2002, 'menu_name', 'en-US', 'Market Data', 'admin', NOW(), NOW()),
('menu', 2002, 'menu_name', 'ja-JP', '相場管理', 'admin', NOW(), NOW());

-- 2003	策略管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2003, 'menu_name', 'zh-CN', '策略管理', 'admin', NOW(), NOW()),
('menu', 2003, 'menu_name', 'zh-TW', '策略管理', 'admin', NOW(), NOW()),
('menu', 2003, 'menu_name', 'en-US', 'Strategy Management', 'admin', NOW(), NOW()),
('menu', 2003, 'menu_name', 'ja-JP', '戦略管理', 'admin', NOW(), NOW());

-- 2004	订单管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2004, 'menu_name', 'zh-CN', '订单管理', 'admin', NOW(), NOW()),
('menu', 2004, 'menu_name', 'zh-TW', '訂單管理', 'admin', NOW(), NOW()),
('menu', 2004, 'menu_name', 'en-US', 'Order Management', 'admin', NOW(), NOW()),
('menu', 2004, 'menu_name', 'ja-JP', '注文管理', 'admin', NOW(), NOW());

-- 2005	账户
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2005, 'menu_name', 'zh-CN', '账户', 'admin', NOW(), NOW()),
('menu', 2005, 'menu_name', 'zh-TW', '賬戶', 'admin', NOW(), NOW()),
('menu', 2005, 'menu_name', 'en-US', 'Account', 'admin', NOW(), NOW()),
('menu', 2005, 'menu_name', 'ja-JP', 'アカウント', 'admin', NOW(), NOW());

-- 2006	账户查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2006, 'menu_name', 'zh-CN', '账户查询', 'admin', NOW(), NOW()),
('menu', 2006, 'menu_name', 'zh-TW', '賬戶查詢', 'admin', NOW(), NOW()),
('menu', 2006, 'menu_name', 'en-US', 'Account Query', 'admin', NOW(), NOW()),
('menu', 2006, 'menu_name', 'ja-JP', 'アカウント検索', 'admin', NOW(), NOW());

-- 2007	账户新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2007, 'menu_name', 'zh-CN', '账户新增', 'admin', NOW(), NOW()),
('menu', 2007, 'menu_name', 'zh-TW', '賬戶新增', 'admin', NOW(), NOW()),
('menu', 2007, 'menu_name', 'en-US', 'Account Add', 'admin', NOW(), NOW()),
('menu', 2007, 'menu_name', 'ja-JP', 'アカウント追加', 'admin', NOW(), NOW());

-- 2008	账户修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2008, 'menu_name', 'zh-CN', '账户修改', 'admin', NOW(), NOW()),
('menu', 2008, 'menu_name', 'zh-TW', '賬戶修改', 'admin', NOW(), NOW()),
('menu', 2008, 'menu_name', 'en-US', 'Account Edit', 'admin', NOW(), NOW()),
('menu', 2008, 'menu_name', 'ja-JP', 'アカウント編集', 'admin', NOW(), NOW());

-- 2009	账户删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2009, 'menu_name', 'zh-CN', '账户删除', 'admin', NOW(), NOW()),
('menu', 2009, 'menu_name', 'zh-TW', '賬戶刪除', 'admin', NOW(), NOW()),
('menu', 2009, 'menu_name', 'en-US', 'Account Delete', 'admin', NOW(), NOW()),
('menu', 2009, 'menu_name', 'ja-JP', 'アカウント削除', 'admin', NOW(), NOW());

-- 2010	账户导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2010, 'menu_name', 'zh-CN', '账户导出', 'admin', NOW(), NOW()),
('menu', 2010, 'menu_name', 'zh-TW', '賬戶導出', 'admin', NOW(), NOW()),
('menu', 2010, 'menu_name', 'en-US', 'Account Export', 'admin', NOW(), NOW()),
('menu', 2010, 'menu_name', 'ja-JP', 'アカウントエクスポート', 'admin', NOW(), NOW());

-- 2011	策略查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2011, 'menu_name', 'zh-CN', '策略查询', 'admin', NOW(), NOW()),
('menu', 2011, 'menu_name', 'zh-TW', '策略查詢', 'admin', NOW(), NOW()),
('menu', 2011, 'menu_name', 'en-US', 'Strategy Query', 'admin', NOW(), NOW()),
('menu', 2011, 'menu_name', 'ja-JP', '戦略検索', 'admin', NOW(), NOW());

-- 2012	策略新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2012, 'menu_name', 'zh-CN', '策略新增', 'admin', NOW(), NOW()),
('menu', 2012, 'menu_name', 'zh-TW', '策略新增', 'admin', NOW(), NOW()),
('menu', 2012, 'menu_name', 'en-US', 'Strategy Add', 'admin', NOW(), NOW()),
('menu', 2012, 'menu_name', 'ja-JP', '戦略追加', 'admin', NOW(), NOW());

-- 2013	策略修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2013, 'menu_name', 'zh-CN', '策略修改', 'admin', NOW(), NOW()),
('menu', 2013, 'menu_name', 'zh-TW', '策略修改', 'admin', NOW(), NOW()),
('menu', 2013, 'menu_name', 'en-US', 'Strategy Edit', 'admin', NOW(), NOW()),
('menu', 2013, 'menu_name', 'ja-JP', '戦略編集', 'admin', NOW(), NOW());

-- 2014	策略删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2014, 'menu_name', 'zh-CN', '策略删除', 'admin', NOW(), NOW()),
('menu', 2014, 'menu_name', 'zh-TW', '策略刪除', 'admin', NOW(), NOW()),
('menu', 2014, 'menu_name', 'en-US', 'Strategy Delete', 'admin', NOW(), NOW()),
('menu', 2014, 'menu_name', 'ja-JP', '戦略削除', 'admin', NOW(), NOW());

-- 2015	策略导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2015, 'menu_name', 'zh-CN', '策略导出', 'admin', NOW(), NOW()),
('menu', 2015, 'menu_name', 'zh-TW', '策略導出', 'admin', NOW(), NOW()),
('menu', 2015, 'menu_name', 'en-US', 'Strategy Export', 'admin', NOW(), NOW()),
('menu', 2015, 'menu_name', 'ja-JP', '戦略エクスポート', 'admin', NOW(), NOW());

-- 2016	订单查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2016, 'menu_name', 'zh-CN', '订单查询', 'admin', NOW(), NOW()),
('menu', 2016, 'menu_name', 'zh-TW', '訂單查詢', 'admin', NOW(), NOW()),
('menu', 2016, 'menu_name', 'en-US', 'Order Query', 'admin', NOW(), NOW()),
('menu', 2016, 'menu_name', 'ja-JP', '注文検索', 'admin', NOW(), NOW());

-- 2017	订单新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2017, 'menu_name', 'zh-CN', '订单新增', 'admin', NOW(), NOW()),
('menu', 2017, 'menu_name', 'zh-TW', '訂單新增', 'admin', NOW(), NOW()),
('menu', 2017, 'menu_name', 'en-US', 'Order Add', 'admin', NOW(), NOW()),
('menu', 2017, 'menu_name', 'ja-JP', '注文追加', 'admin', NOW(), NOW());

-- 2018	订单修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2018, 'menu_name', 'zh-CN', '订单修改', 'admin', NOW(), NOW()),
('menu', 2018, 'menu_name', 'zh-TW', '訂單修改', 'admin', NOW(), NOW()),
('menu', 2018, 'menu_name', 'en-US', 'Order Edit', 'admin', NOW(), NOW()),
('menu', 2018, 'menu_name', 'ja-JP', '注文編集', 'admin', NOW(), NOW());

-- 2019	订单删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2019, 'menu_name', 'zh-CN', '订单删除', 'admin', NOW(), NOW()),
('menu', 2019, 'menu_name', 'zh-TW', '訂單刪除', 'admin', NOW(), NOW()),
('menu', 2019, 'menu_name', 'en-US', 'Order Delete', 'admin', NOW(), NOW()),
('menu', 2019, 'menu_name', 'ja-JP', '注文削除', 'admin', NOW(), NOW());

-- 2020	订单导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2020, 'menu_name', 'zh-CN', '订单导出', 'admin', NOW(), NOW()),
('menu', 2020, 'menu_name', 'zh-TW', '訂單導出', 'admin', NOW(), NOW()),
('menu', 2020, 'menu_name', 'en-US', 'Order Export', 'admin', NOW(), NOW()),
('menu', 2020, 'menu_name', 'ja-JP', '注文エクスポート', 'admin', NOW(), NOW());

-- 2021	仓位管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2021, 'menu_name', 'zh-CN', '仓位管理', 'admin', NOW(), NOW()),
('menu', 2021, 'menu_name', 'zh-TW', '倉位管理', 'admin', NOW(), NOW()),
('menu', 2021, 'menu_name', 'en-US', 'Position Management', 'admin', NOW(), NOW()),
('menu', 2021, 'menu_name', 'ja-JP', 'ポジション管理', 'admin', NOW(), NOW());

-- 2022	交易统计
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2022, 'menu_name', 'zh-CN', '交易统计', 'admin', NOW(), NOW()),
('menu', 2022, 'menu_name', 'zh-TW', '交易統計', 'admin', NOW(), NOW()),
('menu', 2022, 'menu_name', 'en-US', 'Trading Statistics', 'admin', NOW(), NOW()),
('menu', 2022, 'menu_name', 'ja-JP', '取引統計', 'admin', NOW(), NOW());

-- 2023	历史回撤
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2023, 'menu_name', 'zh-CN', '历史回撤', 'admin', NOW(), NOW()),
('menu', 2023, 'menu_name', 'zh-TW', '歷史回撤', 'admin', NOW(), NOW()),
('menu', 2023, 'menu_name', 'en-US', 'Historical Drawdown', 'admin', NOW(), NOW()),
('menu', 2023, 'menu_name', 'ja-JP', '履歴ドローダウン', 'admin', NOW(), NOW());

-- 2024	回测查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2024, 'menu_name', 'zh-CN', '回测查询', 'admin', NOW(), NOW()),
('menu', 2024, 'menu_name', 'zh-TW', '回測查詢', 'admin', NOW(), NOW()),
('menu', 2024, 'menu_name', 'en-US', 'Backtest Query', 'admin', NOW(), NOW()),
('menu', 2024, 'menu_name', 'ja-JP', 'バックテスト検索', 'admin', NOW(), NOW());

-- 2025	回测新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2025, 'menu_name', 'zh-CN', '回测新增', 'admin', NOW(), NOW()),
('menu', 2025, 'menu_name', 'zh-TW', '回測新增', 'admin', NOW(), NOW()),
('menu', 2025, 'menu_name', 'en-US', 'Backtest Add', 'admin', NOW(), NOW()),
('menu', 2025, 'menu_name', 'ja-JP', 'バックテスト追加', 'admin', NOW(), NOW());

-- 2026	回测修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2026, 'menu_name', 'zh-CN', '回测修改', 'admin', NOW(), NOW()),
('menu', 2026, 'menu_name', 'zh-TW', '回測修改', 'admin', NOW(), NOW()),
('menu', 2026, 'menu_name', 'en-US', 'Backtest Edit', 'admin', NOW(), NOW()),
('menu', 2026, 'menu_name', 'ja-JP', 'バックテスト編集', 'admin', NOW(), NOW());

-- 2027	回测删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2027, 'menu_name', 'zh-CN', '回测删除', 'admin', NOW(), NOW()),
('menu', 2027, 'menu_name', 'zh-TW', '回測刪除', 'admin', NOW(), NOW()),
('menu', 2027, 'menu_name', 'en-US', 'Backtest Delete', 'admin', NOW(), NOW()),
('menu', 2027, 'menu_name', 'ja-JP', 'バックテスト削除', 'admin', NOW(), NOW());

-- 2028	回测导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2028, 'menu_name', 'zh-CN', '回测导出', 'admin', NOW(), NOW()),
('menu', 2028, 'menu_name', 'zh-TW', '回測導出', 'admin', NOW(), NOW()),
('menu', 2028, 'menu_name', 'en-US', 'Backtest Export', 'admin', NOW(), NOW()),
('menu', 2028, 'menu_name', 'ja-JP', 'バックテストエクスポート', 'admin', NOW(), NOW());

-- 2029	市场信号分析
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2029, 'menu_name', 'zh-CN', '市场信号分析', 'admin', NOW(), NOW()),
('menu', 2029, 'menu_name', 'zh-TW', '市場信號分析', 'admin', NOW(), NOW()),
('menu', 2029, 'menu_name', 'en-US', 'Market Signal Analysis', 'admin', NOW(), NOW()),
('menu', 2029, 'menu_name', 'ja-JP', '市場シグナル分析', 'admin', NOW(), NOW());

-- 2030	市场信号查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2030, 'menu_name', 'zh-CN', '市场信号查询', 'admin', NOW(), NOW()),
('menu', 2030, 'menu_name', 'zh-TW', '市場信號查詢', 'admin', NOW(), NOW()),
('menu', 2030, 'menu_name', 'en-US', 'Market Signal Query', 'admin', NOW(), NOW()),
('menu', 2030, 'menu_name', 'ja-JP', '市場シグナル検索', 'admin', NOW(), NOW());

-- 2031	市场信号新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2031, 'menu_name', 'zh-CN', '市场信号新增', 'admin', NOW(), NOW()),
('menu', 2031, 'menu_name', 'zh-TW', '市場信號新增', 'admin', NOW(), NOW()),
('menu', 2031, 'menu_name', 'en-US', 'Market Signal Add', 'admin', NOW(), NOW()),
('menu', 2031, 'menu_name', 'ja-JP', '市場シグナル追加', 'admin', NOW(), NOW());

-- 2032	市场信号修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2032, 'menu_name', 'zh-CN', '市场信号修改', 'admin', NOW(), NOW()),
('menu', 2032, 'menu_name', 'zh-TW', '市場信號修改', 'admin', NOW(), NOW()),
('menu', 2032, 'menu_name', 'en-US', 'Market Signal Edit', 'admin', NOW(), NOW()),
('menu', 2032, 'menu_name', 'ja-JP', '市場シグナル編集', 'admin', NOW(), NOW());

-- 2033	市场信号删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2033, 'menu_name', 'zh-CN', '市场信号删除', 'admin', NOW(), NOW()),
('menu', 2033, 'menu_name', 'zh-TW', '市場信號刪除', 'admin', NOW(), NOW()),
('menu', 2033, 'menu_name', 'en-US', 'Market Signal Delete', 'admin', NOW(), NOW()),
('menu', 2033, 'menu_name', 'ja-JP', '市場シグナル削除', 'admin', NOW(), NOW());

-- 2034	市场信号导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2034, 'menu_name', 'zh-CN', '市场信号导出', 'admin', NOW(), NOW()),
('menu', 2034, 'menu_name', 'zh-TW', '市場信號導出', 'admin', NOW(), NOW()),
('menu', 2034, 'menu_name', 'en-US', 'Market Signal Export', 'admin', NOW(), NOW()),
('menu', 2034, 'menu_name', 'ja-JP', '市場シグナルエクスポート', 'admin', NOW(), NOW());

-- 2035	市场信号分析
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2035, 'menu_name', 'zh-CN', '市场信号分析', 'admin', NOW(), NOW()),
('menu', 2035, 'menu_name', 'zh-TW', '市場信號分析', 'admin', NOW(), NOW()),
('menu', 2035, 'menu_name', 'en-US', 'Market Signal Analysis', 'admin', NOW(), NOW()),
('menu', 2035, 'menu_name', 'ja-JP', '市場シグナル分析', 'admin', NOW(), NOW());

-- 2036	多语言管理
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2036, 'menu_name', 'zh-CN', '多语言管理', 'admin', NOW(), NOW()),
('menu', 2036, 'menu_name', 'zh-TW', '多語言管理', 'admin', NOW(), NOW()),
('menu', 2036, 'menu_name', 'en-US', 'I18n Management', 'admin', NOW(), NOW()),
('menu', 2036, 'menu_name', 'ja-JP', '多言語管理', 'admin', NOW(), NOW());

-- 2037	多语言查询
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2037, 'menu_name', 'zh-CN', '多语言查询', 'admin', NOW(), NOW()),
('menu', 2037, 'menu_name', 'zh-TW', '多語言查詢', 'admin', NOW(), NOW()),
('menu', 2037, 'menu_name', 'en-US', 'I18n Query', 'admin', NOW(), NOW()),
('menu', 2037, 'menu_name', 'ja-JP', '多言語検索', 'admin', NOW(), NOW());

-- 2038	多语言新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2038, 'menu_name', 'zh-CN', '多语言新增', 'admin', NOW(), NOW()),
('menu', 2038, 'menu_name', 'zh-TW', '多語言新增', 'admin', NOW(), NOW()),
('menu', 2038, 'menu_name', 'en-US', 'I18n Add', 'admin', NOW(), NOW()),
('menu', 2038, 'menu_name', 'ja-JP', '多言語追加', 'admin', NOW(), NOW());

-- 2039	多语言修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2039, 'menu_name', 'zh-CN', '多语言修改', 'admin', NOW(), NOW()),
('menu', 2039, 'menu_name', 'zh-TW', '多語言修改', 'admin', NOW(), NOW()),
('menu', 2039, 'menu_name', 'en-US', 'I18n Edit', 'admin', NOW(), NOW()),
('menu', 2039, 'menu_name', 'ja-JP', '多言語編集', 'admin', NOW(), NOW());

-- 2040	多语言删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('menu', 2040, 'menu_name', 'zh-CN', '多语言删除', 'admin', NOW(), NOW()),
('menu', 2040, 'menu_name', 'zh-TW', '多語言刪除', 'admin', NOW(), NOW()),
('menu', 2040, 'menu_name', 'en-US', 'I18n Delete', 'admin', NOW(), NOW()),
('menu', 2040, 'menu_name', 'ja-JP', '多言語削除', 'admin', NOW(), NOW());

