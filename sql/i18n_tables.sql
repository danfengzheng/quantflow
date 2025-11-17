-- ============================================
-- 统一多语言支持表结构
-- ============================================

-- ----------------------------
-- 统一多语言翻译表
-- ----------------------------
DROP TABLE IF EXISTS `sys_i18n_translation`;
CREATE TABLE `sys_i18n_translation` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `entity_type` varchar(50) NOT NULL COMMENT '实体类型（menu-菜单, dict_data-字典数据, role-角色, dept-部门等）',
  `entity_id` bigint NOT NULL COMMENT '实体ID（对应菜单ID、字典编码等）',
  `field_name` varchar(50) NOT NULL COMMENT '字段名（menu_name-菜单名称, dict_label-字典标签, role_name-角色名称等）',
  `locale` varchar(10) NOT NULL COMMENT '语言代码（zh-CN, en-US, zh-TW, ja-JP）',
  `translation` varchar(500) NOT NULL COMMENT '翻译文本',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_entity_field_locale` (`entity_type`, `entity_id`, `field_name`, `locale`),
  KEY `idx_entity` (`entity_type`, `entity_id`),
  KEY `idx_locale` (`locale`),
  KEY `idx_entity_type` (`entity_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='统一多语言翻译表';

-- ----------------------------
-- 数据迁移脚本：将现有菜单数据迁移到多语言表
-- ----------------------------
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_time`, `update_time`)
SELECT 
  'menu' AS `entity_type`,
  `menu_id` AS `entity_id`,
  'menu_name' AS `field_name`,
  'zh-CN' AS `locale`,
  `menu_name` AS `translation`,
  `create_time`,
  `update_time`
FROM `sys_menu`
WHERE `menu_name` IS NOT NULL AND `menu_name` != '';

-- 迁移菜单备注（如果有）
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_time`, `update_time`)
SELECT 
  'menu' AS `entity_type`,
  `menu_id` AS `entity_id`,
  'remark' AS `field_name`,
  'zh-CN' AS `locale`,
  `remark` AS `translation`,
  `create_time`,
  `update_time`
FROM `sys_menu`
WHERE `remark` IS NOT NULL AND `remark` != '';

-- ----------------------------
-- 数据迁移脚本：将现有字典数据迁移到多语言表
-- ----------------------------
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_time`, `update_time`)
SELECT 
  'dict_data' AS `entity_type`,
  `dict_code` AS `entity_id`,
  'dict_label' AS `field_name`,
  'zh-CN' AS `locale`,
  `dict_label` AS `translation`,
  `create_time`,
  `update_time`
FROM `sys_dict_data`
WHERE `dict_label` IS NOT NULL AND `dict_label` != '';

