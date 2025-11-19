-- ============================================
-- 多语言管理菜单初始化脚本
-- ============================================
-- 说明：添加多语言管理菜单项到系统菜单

-- 插入多语言管理菜单（父菜单：系统管理）
-- 注意：如果菜单ID已存在，请修改下面的菜单ID
SET @menu_id = 2036;  -- 多语言管理主菜单ID
SET @menu_query_id = 2037;  -- 多语言查询按钮ID
SET @menu_add_id = 2038;  -- 多语言新增按钮ID
SET @menu_edit_id = 2039;  -- 多语言修改按钮ID
SET @menu_remove_id = 2040;  -- 多语言删除按钮ID

INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES (@menu_id, '多语言管理', 1, 8, 'i18n', 'system/i18n/index', 1, 0, 'C', '0', '0', 'system:i18n:list', 'international', 'admin', NOW(), '', NULL, '多语言翻译管理菜单')
ON DUPLICATE KEY UPDATE 
  `menu_name` = '多语言管理',
  `parent_id` = 1,
  `order_num` = 8,
  `path` = 'i18n',
  `component` = 'system/i18n/index',
  `perms` = 'system:i18n:list',
  `icon` = 'international',
  `remark` = '多语言翻译管理菜单';

-- 插入多语言管理按钮权限
INSERT INTO `sys_menu` (`menu_id`, `menu_name`, `parent_id`, `order_num`, `path`, `component`, `is_frame`, `is_cache`, `menu_type`, `visible`, `status`, `perms`, `icon`, `create_by`, `create_time`, `update_by`, `update_time`, `remark`) 
VALUES 
(@menu_query_id, '多语言查询', @menu_id, 1, '', '', 1, 0, 'F', '0', '0', 'system:i18n:query', '#', 'admin', NOW(), '', NULL, ''),
(@menu_add_id, '多语言新增', @menu_id, 2, '', '', 1, 0, 'F', '0', '0', 'system:i18n:add', '#', 'admin', NOW(), '', NULL, ''),
(@menu_edit_id, '多语言修改', @menu_id, 3, '', '', 1, 0, 'F', '0', '0', 'system:i18n:edit', '#', 'admin', NOW(), '', NULL, ''),
(@menu_remove_id, '多语言删除', @menu_id, 4, '', '', 1, 0, 'F', '0', '0', 'system:i18n:remove', '#', 'admin', NOW(), '', NULL, '')
ON DUPLICATE KEY UPDATE 
  `menu_name` = VALUES(`menu_name`),
  `parent_id` = @menu_id,
  `order_num` = VALUES(`order_num`),
  `perms` = VALUES(`perms`);

-- 为超级管理员角色分配多语言管理权限（假设角色ID为1）
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, @menu_id
WHERE NOT EXISTS (
    SELECT 1 FROM `sys_role_menu` WHERE `role_id` = 1 AND `menu_id` = @menu_id
);

-- 为超级管理员角色分配多语言管理按钮权限
INSERT INTO `sys_role_menu` (`role_id`, `menu_id`)
SELECT 1, menu_id FROM `sys_menu` WHERE `parent_id` = @menu_id
AND NOT EXISTS (
    SELECT 1 FROM `sys_role_menu` rm 
    INNER JOIN `sys_menu` m ON rm.menu_id = m.menu_id 
    WHERE rm.role_id = 1 AND m.parent_id = @menu_id
);

