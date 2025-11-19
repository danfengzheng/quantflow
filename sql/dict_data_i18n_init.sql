-- ============================================
-- 字典数据多语言初始化脚本
-- ============================================
-- 说明：为所有字典数据添加多语言翻译（zh-CN, zh-TW, en-US, ja-JP）
-- 执行前请确保 sys_i18n_translation 表已创建

-- 删除已存在的字典数据多语言数据（可选，如果需要重新初始化）
-- DELETE FROM `sys_i18n_translation` WHERE `entity_type` = 'dict_data' AND `field_name` = 'dict_label';

-- ============================================
-- 系统字典数据
-- ============================================
-- 1	男
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 1, 'dict_label', 'zh-CN', '男', 'admin', NOW(), NOW()),
('dict_data', 1, 'dict_label', 'zh-TW', '男', 'admin', NOW(), NOW()),
('dict_data', 1, 'dict_label', 'en-US', 'Male', 'admin', NOW(), NOW()),
('dict_data', 1, 'dict_label', 'ja-JP', '男性', 'admin', NOW(), NOW());

-- 2	女
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 2, 'dict_label', 'zh-CN', '女', 'admin', NOW(), NOW()),
('dict_data', 2, 'dict_label', 'zh-TW', '女', 'admin', NOW(), NOW()),
('dict_data', 2, 'dict_label', 'en-US', 'Female', 'admin', NOW(), NOW()),
('dict_data', 2, 'dict_label', 'ja-JP', '女性', 'admin', NOW(), NOW());

-- 3	未知
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 3, 'dict_label', 'zh-CN', '未知', 'admin', NOW(), NOW()),
('dict_data', 3, 'dict_label', 'zh-TW', '未知', 'admin', NOW(), NOW()),
('dict_data', 3, 'dict_label', 'en-US', 'Unknown', 'admin', NOW(), NOW()),
('dict_data', 3, 'dict_label', 'ja-JP', '不明', 'admin', NOW(), NOW());

-- 4	显示
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 4, 'dict_label', 'zh-CN', '显示', 'admin', NOW(), NOW()),
('dict_data', 4, 'dict_label', 'zh-TW', '顯示', 'admin', NOW(), NOW()),
('dict_data', 4, 'dict_label', 'en-US', 'Show', 'admin', NOW(), NOW()),
('dict_data', 4, 'dict_label', 'ja-JP', '表示', 'admin', NOW(), NOW());

-- 5	隐藏
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 5, 'dict_label', 'zh-CN', '隐藏', 'admin', NOW(), NOW()),
('dict_data', 5, 'dict_label', 'zh-TW', '隱藏', 'admin', NOW(), NOW()),
('dict_data', 5, 'dict_label', 'en-US', 'Hide', 'admin', NOW(), NOW()),
('dict_data', 5, 'dict_label', 'ja-JP', '非表示', 'admin', NOW(), NOW());

-- 6	正常
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 6, 'dict_label', 'zh-CN', '正常', 'admin', NOW(), NOW()),
('dict_data', 6, 'dict_label', 'zh-TW', '正常', 'admin', NOW(), NOW()),
('dict_data', 6, 'dict_label', 'en-US', 'Normal', 'admin', NOW(), NOW()),
('dict_data', 6, 'dict_label', 'ja-JP', '正常', 'admin', NOW(), NOW());

-- 7	停用
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 7, 'dict_label', 'zh-CN', '停用', 'admin', NOW(), NOW()),
('dict_data', 7, 'dict_label', 'zh-TW', '停用', 'admin', NOW(), NOW()),
('dict_data', 7, 'dict_label', 'en-US', 'Disabled', 'admin', NOW(), NOW()),
('dict_data', 7, 'dict_label', 'ja-JP', '無効', 'admin', NOW(), NOW());

-- 8	正常
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 8, 'dict_label', 'zh-CN', '正常', 'admin', NOW(), NOW()),
('dict_data', 8, 'dict_label', 'zh-TW', '正常', 'admin', NOW(), NOW()),
('dict_data', 8, 'dict_label', 'en-US', 'Normal', 'admin', NOW(), NOW()),
('dict_data', 8, 'dict_label', 'ja-JP', '正常', 'admin', NOW(), NOW());

-- 9	暂停
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 9, 'dict_label', 'zh-CN', '暂停', 'admin', NOW(), NOW()),
('dict_data', 9, 'dict_label', 'zh-TW', '暫停', 'admin', NOW(), NOW()),
('dict_data', 9, 'dict_label', 'en-US', 'Pause', 'admin', NOW(), NOW()),
('dict_data', 9, 'dict_label', 'ja-JP', '一時停止', 'admin', NOW(), NOW());

-- 10	默认
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 10, 'dict_label', 'zh-CN', '默认', 'admin', NOW(), NOW()),
('dict_data', 10, 'dict_label', 'zh-TW', '默認', 'admin', NOW(), NOW()),
('dict_data', 10, 'dict_label', 'en-US', 'Default', 'admin', NOW(), NOW()),
('dict_data', 10, 'dict_label', 'ja-JP', 'デフォルト', 'admin', NOW(), NOW());

-- 11	系统
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 11, 'dict_label', 'zh-CN', '系统', 'admin', NOW(), NOW()),
('dict_data', 11, 'dict_label', 'zh-TW', '系統', 'admin', NOW(), NOW()),
('dict_data', 11, 'dict_label', 'en-US', 'System', 'admin', NOW(), NOW()),
('dict_data', 11, 'dict_label', 'ja-JP', 'システム', 'admin', NOW(), NOW());

-- 12	是
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 12, 'dict_label', 'zh-CN', '是', 'admin', NOW(), NOW()),
('dict_data', 12, 'dict_label', 'zh-TW', '是', 'admin', NOW(), NOW()),
('dict_data', 12, 'dict_label', 'en-US', 'Yes', 'admin', NOW(), NOW()),
('dict_data', 12, 'dict_label', 'ja-JP', 'はい', 'admin', NOW(), NOW());

-- 13	否
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 13, 'dict_label', 'zh-CN', '否', 'admin', NOW(), NOW()),
('dict_data', 13, 'dict_label', 'zh-TW', '否', 'admin', NOW(), NOW()),
('dict_data', 13, 'dict_label', 'en-US', 'No', 'admin', NOW(), NOW()),
('dict_data', 13, 'dict_label', 'ja-JP', 'いいえ', 'admin', NOW(), NOW());

-- 14	通知
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 14, 'dict_label', 'zh-CN', '通知', 'admin', NOW(), NOW()),
('dict_data', 14, 'dict_label', 'zh-TW', '通知', 'admin', NOW(), NOW()),
('dict_data', 14, 'dict_label', 'en-US', 'Notice', 'admin', NOW(), NOW()),
('dict_data', 14, 'dict_label', 'ja-JP', '通知', 'admin', NOW(), NOW());

-- 15	公告
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 15, 'dict_label', 'zh-CN', '公告', 'admin', NOW(), NOW()),
('dict_data', 15, 'dict_label', 'zh-TW', '公告', 'admin', NOW(), NOW()),
('dict_data', 15, 'dict_label', 'en-US', 'Announcement', 'admin', NOW(), NOW()),
('dict_data', 15, 'dict_label', 'ja-JP', 'お知らせ', 'admin', NOW(), NOW());

-- 16	正常
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 16, 'dict_label', 'zh-CN', '正常', 'admin', NOW(), NOW()),
('dict_data', 16, 'dict_label', 'zh-TW', '正常', 'admin', NOW(), NOW()),
('dict_data', 16, 'dict_label', 'en-US', 'Normal', 'admin', NOW(), NOW()),
('dict_data', 16, 'dict_label', 'ja-JP', '正常', 'admin', NOW(), NOW());

-- 17	关闭
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 17, 'dict_label', 'zh-CN', '关闭', 'admin', NOW(), NOW()),
('dict_data', 17, 'dict_label', 'zh-TW', '關閉', 'admin', NOW(), NOW()),
('dict_data', 17, 'dict_label', 'en-US', 'Closed', 'admin', NOW(), NOW()),
('dict_data', 17, 'dict_label', 'ja-JP', '閉鎖', 'admin', NOW(), NOW());

-- 18	其他
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 18, 'dict_label', 'zh-CN', '其他', 'admin', NOW(), NOW()),
('dict_data', 18, 'dict_label', 'zh-TW', '其他', 'admin', NOW(), NOW()),
('dict_data', 18, 'dict_label', 'en-US', 'Other', 'admin', NOW(), NOW()),
('dict_data', 18, 'dict_label', 'ja-JP', 'その他', 'admin', NOW(), NOW());

-- 19	新增
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 19, 'dict_label', 'zh-CN', '新增', 'admin', NOW(), NOW()),
('dict_data', 19, 'dict_label', 'zh-TW', '新增', 'admin', NOW(), NOW()),
('dict_data', 19, 'dict_label', 'en-US', 'Add', 'admin', NOW(), NOW()),
('dict_data', 19, 'dict_label', 'ja-JP', '追加', 'admin', NOW(), NOW());

-- 20	修改
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 20, 'dict_label', 'zh-CN', '修改', 'admin', NOW(), NOW()),
('dict_data', 20, 'dict_label', 'zh-TW', '修改', 'admin', NOW(), NOW()),
('dict_data', 20, 'dict_label', 'en-US', 'Edit', 'admin', NOW(), NOW()),
('dict_data', 20, 'dict_label', 'ja-JP', '編集', 'admin', NOW(), NOW());

-- 21	删除
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 21, 'dict_label', 'zh-CN', '删除', 'admin', NOW(), NOW()),
('dict_data', 21, 'dict_label', 'zh-TW', '刪除', 'admin', NOW(), NOW()),
('dict_data', 21, 'dict_label', 'en-US', 'Delete', 'admin', NOW(), NOW()),
('dict_data', 21, 'dict_label', 'ja-JP', '削除', 'admin', NOW(), NOW());

-- 22	授权
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 22, 'dict_label', 'zh-CN', '授权', 'admin', NOW(), NOW()),
('dict_data', 22, 'dict_label', 'zh-TW', '授權', 'admin', NOW(), NOW()),
('dict_data', 22, 'dict_label', 'en-US', 'Authorize', 'admin', NOW(), NOW()),
('dict_data', 22, 'dict_label', 'ja-JP', '承認', 'admin', NOW(), NOW());

-- 23	导出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 23, 'dict_label', 'zh-CN', '导出', 'admin', NOW(), NOW()),
('dict_data', 23, 'dict_label', 'zh-TW', '導出', 'admin', NOW(), NOW()),
('dict_data', 23, 'dict_label', 'en-US', 'Export', 'admin', NOW(), NOW()),
('dict_data', 23, 'dict_label', 'ja-JP', 'エクスポート', 'admin', NOW(), NOW());

-- 24	导入
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 24, 'dict_label', 'zh-CN', '导入', 'admin', NOW(), NOW()),
('dict_data', 24, 'dict_label', 'zh-TW', '導入', 'admin', NOW(), NOW()),
('dict_data', 24, 'dict_label', 'en-US', 'Import', 'admin', NOW(), NOW()),
('dict_data', 24, 'dict_label', 'ja-JP', 'インポート', 'admin', NOW(), NOW());

-- 25	强退
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 25, 'dict_label', 'zh-CN', '强退', 'admin', NOW(), NOW()),
('dict_data', 25, 'dict_label', 'zh-TW', '強退', 'admin', NOW(), NOW()),
('dict_data', 25, 'dict_label', 'en-US', 'Force Logout', 'admin', NOW(), NOW()),
('dict_data', 25, 'dict_label', 'ja-JP', '強制ログアウト', 'admin', NOW(), NOW());

-- 26	生成代码
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 26, 'dict_label', 'zh-CN', '生成代码', 'admin', NOW(), NOW()),
('dict_data', 26, 'dict_label', 'zh-TW', '生成代碼', 'admin', NOW(), NOW()),
('dict_data', 26, 'dict_label', 'en-US', 'Generate Code', 'admin', NOW(), NOW()),
('dict_data', 26, 'dict_label', 'ja-JP', 'コード生成', 'admin', NOW(), NOW());

-- 27	清空数据
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 27, 'dict_label', 'zh-CN', '清空数据', 'admin', NOW(), NOW()),
('dict_data', 27, 'dict_label', 'zh-TW', '清空數據', 'admin', NOW(), NOW()),
('dict_data', 27, 'dict_label', 'en-US', 'Clear Data', 'admin', NOW(), NOW()),
('dict_data', 27, 'dict_label', 'ja-JP', 'データクリア', 'admin', NOW(), NOW());

-- 28	成功
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 28, 'dict_label', 'zh-CN', '成功', 'admin', NOW(), NOW()),
('dict_data', 28, 'dict_label', 'zh-TW', '成功', 'admin', NOW(), NOW()),
('dict_data', 28, 'dict_label', 'en-US', 'Success', 'admin', NOW(), NOW()),
('dict_data', 28, 'dict_label', 'ja-JP', '成功', 'admin', NOW(), NOW());

-- 29	失败
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 29, 'dict_label', 'zh-CN', '失败', 'admin', NOW(), NOW()),
('dict_data', 29, 'dict_label', 'zh-TW', '失敗', 'admin', NOW(), NOW()),
('dict_data', 29, 'dict_label', 'en-US', 'Failed', 'admin', NOW(), NOW()),
('dict_data', 29, 'dict_label', 'ja-JP', '失敗', 'admin', NOW(), NOW());

-- ============================================
-- 量化交易模块字典数据
-- ============================================
-- 100	币安
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 100, 'dict_label', 'zh-CN', '币安', 'admin', NOW(), NOW()),
('dict_data', 100, 'dict_label', 'zh-TW', '幣安', 'admin', NOW(), NOW()),
('dict_data', 100, 'dict_label', 'en-US', 'Binance', 'admin', NOW(), NOW()),
('dict_data', 100, 'dict_label', 'ja-JP', 'バイナンス', 'admin', NOW(), NOW());

-- 101	欧易
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 101, 'dict_label', 'zh-CN', '欧易', 'admin', NOW(), NOW()),
('dict_data', 101, 'dict_label', 'zh-TW', '歐易', 'admin', NOW(), NOW()),
('dict_data', 101, 'dict_label', 'en-US', 'OKX', 'admin', NOW(), NOW()),
('dict_data', 101, 'dict_label', 'ja-JP', 'OKX', 'admin', NOW(), NOW());

-- 102	火币
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 102, 'dict_label', 'zh-CN', '火币', 'admin', NOW(), NOW()),
('dict_data', 102, 'dict_label', 'zh-TW', '火幣', 'admin', NOW(), NOW()),
('dict_data', 102, 'dict_label', 'en-US', 'Huobi', 'admin', NOW(), NOW()),
('dict_data', 102, 'dict_label', 'ja-JP', 'フォビ', 'admin', NOW(), NOW());

-- 103	买入
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 103, 'dict_label', 'zh-CN', '买入', 'admin', NOW(), NOW()),
('dict_data', 103, 'dict_label', 'zh-TW', '買入', 'admin', NOW(), NOW()),
('dict_data', 103, 'dict_label', 'en-US', 'Buy', 'admin', NOW(), NOW()),
('dict_data', 103, 'dict_label', 'ja-JP', '買い', 'admin', NOW(), NOW());

-- 104	卖出
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 104, 'dict_label', 'zh-CN', '卖出', 'admin', NOW(), NOW()),
('dict_data', 104, 'dict_label', 'zh-TW', '賣出', 'admin', NOW(), NOW()),
('dict_data', 104, 'dict_label', 'en-US', 'Sell', 'admin', NOW(), NOW()),
('dict_data', 104, 'dict_label', 'ja-JP', '売り', 'admin', NOW(), NOW());

-- 105	市价单
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 105, 'dict_label', 'zh-CN', '市价单', 'admin', NOW(), NOW()),
('dict_data', 105, 'dict_label', 'zh-TW', '市價單', 'admin', NOW(), NOW()),
('dict_data', 105, 'dict_label', 'en-US', 'Market Order', 'admin', NOW(), NOW()),
('dict_data', 105, 'dict_label', 'ja-JP', '成行注文', 'admin', NOW(), NOW());

-- 106	限价单
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 106, 'dict_label', 'zh-CN', '限价单', 'admin', NOW(), NOW()),
('dict_data', 106, 'dict_label', 'zh-TW', '限價單', 'admin', NOW(), NOW()),
('dict_data', 106, 'dict_label', 'en-US', 'Limit Order', 'admin', NOW(), NOW()),
('dict_data', 106, 'dict_label', 'ja-JP', '指値注文', 'admin', NOW(), NOW());

-- 109	待提交
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 109, 'dict_label', 'zh-CN', '待提交', 'admin', NOW(), NOW()),
('dict_data', 109, 'dict_label', 'zh-TW', '待提交', 'admin', NOW(), NOW()),
('dict_data', 109, 'dict_label', 'en-US', 'Pending', 'admin', NOW(), NOW()),
('dict_data', 109, 'dict_label', 'ja-JP', '保留中', 'admin', NOW(), NOW());

-- 110	已提交
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 110, 'dict_label', 'zh-CN', '已提交', 'admin', NOW(), NOW()),
('dict_data', 110, 'dict_label', 'zh-TW', '已提交', 'admin', NOW(), NOW()),
('dict_data', 110, 'dict_label', 'en-US', 'Submitted', 'admin', NOW(), NOW()),
('dict_data', 110, 'dict_label', 'ja-JP', '提出済み', 'admin', NOW(), NOW());

-- 111	部分成交
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 111, 'dict_label', 'zh-CN', '部分成交', 'admin', NOW(), NOW()),
('dict_data', 111, 'dict_label', 'zh-TW', '部分成交', 'admin', NOW(), NOW()),
('dict_data', 111, 'dict_label', 'en-US', 'Partially Filled', 'admin', NOW(), NOW()),
('dict_data', 111, 'dict_label', 'ja-JP', '一部約定', 'admin', NOW(), NOW());

-- 112	完全成交
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 112, 'dict_label', 'zh-CN', '完全成交', 'admin', NOW(), NOW()),
('dict_data', 112, 'dict_label', 'zh-TW', '完全成交', 'admin', NOW(), NOW()),
('dict_data', 112, 'dict_label', 'en-US', 'Filled', 'admin', NOW(), NOW()),
('dict_data', 112, 'dict_label', 'ja-JP', '約定済み', 'admin', NOW(), NOW());

-- 113	已取消
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 113, 'dict_label', 'zh-CN', '已取消', 'admin', NOW(), NOW()),
('dict_data', 113, 'dict_label', 'zh-TW', '已取消', 'admin', NOW(), NOW()),
('dict_data', 113, 'dict_label', 'en-US', 'Cancelled', 'admin', NOW(), NOW()),
('dict_data', 113, 'dict_label', 'ja-JP', 'キャンセル済み', 'admin', NOW(), NOW());

-- 114	已拒绝
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 114, 'dict_label', 'zh-CN', '已拒绝', 'admin', NOW(), NOW()),
('dict_data', 114, 'dict_label', 'zh-TW', '已拒絕', 'admin', NOW(), NOW()),
('dict_data', 114, 'dict_label', 'en-US', 'Rejected', 'admin', NOW(), NOW()),
('dict_data', 114, 'dict_label', 'ja-JP', '拒否済み', 'admin', NOW(), NOW());

-- 115	失败
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 115, 'dict_label', 'zh-CN', '失败', 'admin', NOW(), NOW()),
('dict_data', 115, 'dict_label', 'zh-TW', '失敗', 'admin', NOW(), NOW()),
('dict_data', 115, 'dict_label', 'en-US', 'Failed', 'admin', NOW(), NOW()),
('dict_data', 115, 'dict_label', 'ja-JP', '失敗', 'admin', NOW(), NOW());

-- 116	均线交叉
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 116, 'dict_label', 'zh-CN', '均线交叉', 'admin', NOW(), NOW()),
('dict_data', 116, 'dict_label', 'zh-TW', '均線交叉', 'admin', NOW(), NOW()),
('dict_data', 116, 'dict_label', 'en-US', 'Moving Average Crossover', 'admin', NOW(), NOW()),
('dict_data', 116, 'dict_label', 'ja-JP', '移動平均クロス', 'admin', NOW(), NOW());

-- 117	网格策略
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 117, 'dict_label', 'zh-CN', '网格策略', 'admin', NOW(), NOW()),
('dict_data', 117, 'dict_label', 'zh-TW', '網格策略', 'admin', NOW(), NOW()),
('dict_data', 117, 'dict_label', 'en-US', 'Grid Strategy', 'admin', NOW(), NOW()),
('dict_data', 117, 'dict_label', 'ja-JP', 'グリッド戦略', 'admin', NOW(), NOW());

-- 118	套利策略
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 118, 'dict_label', 'zh-CN', '套利策略', 'admin', NOW(), NOW()),
('dict_data', 118, 'dict_label', 'zh-TW', '套利策略', 'admin', NOW(), NOW()),
('dict_data', 118, 'dict_label', 'en-US', 'Arbitrage Strategy', 'admin', NOW(), NOW()),
('dict_data', 118, 'dict_label', 'ja-JP', 'アービトラージ戦略', 'admin', NOW(), NOW());

-- 119	自定义
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 119, 'dict_label', 'zh-CN', '自定义', 'admin', NOW(), NOW()),
('dict_data', 119, 'dict_label', 'zh-TW', '自定義', 'admin', NOW(), NOW()),
('dict_data', 119, 'dict_label', 'en-US', 'Custom', 'admin', NOW(), NOW()),
('dict_data', 119, 'dict_label', 'ja-JP', 'カスタム', 'admin', NOW(), NOW());

-- 120	1分钟
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 120, 'dict_label', 'zh-CN', '1分钟', 'admin', NOW(), NOW()),
('dict_data', 120, 'dict_label', 'zh-TW', '1分鐘', 'admin', NOW(), NOW()),
('dict_data', 120, 'dict_label', 'en-US', '1 Minute', 'admin', NOW(), NOW()),
('dict_data', 120, 'dict_label', 'ja-JP', '1分', 'admin', NOW(), NOW());

-- 121	5分钟
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 121, 'dict_label', 'zh-CN', '5分钟', 'admin', NOW(), NOW()),
('dict_data', 121, 'dict_label', 'zh-TW', '5分鐘', 'admin', NOW(), NOW()),
('dict_data', 121, 'dict_label', 'en-US', '5 Minutes', 'admin', NOW(), NOW()),
('dict_data', 121, 'dict_label', 'ja-JP', '5分', 'admin', NOW(), NOW());

-- 122	15分钟
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 122, 'dict_label', 'zh-CN', '15分钟', 'admin', NOW(), NOW()),
('dict_data', 122, 'dict_label', 'zh-TW', '15分鐘', 'admin', NOW(), NOW()),
('dict_data', 122, 'dict_label', 'en-US', '15 Minutes', 'admin', NOW(), NOW()),
('dict_data', 122, 'dict_label', 'ja-JP', '15分', 'admin', NOW(), NOW());

-- 123	1小时
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 123, 'dict_label', 'zh-CN', '1小时', 'admin', NOW(), NOW()),
('dict_data', 123, 'dict_label', 'zh-TW', '1小時', 'admin', NOW(), NOW()),
('dict_data', 123, 'dict_label', 'en-US', '1 Hour', 'admin', NOW(), NOW()),
('dict_data', 123, 'dict_label', 'ja-JP', '1時間', 'admin', NOW(), NOW());

-- 124	4小时
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 124, 'dict_label', 'zh-CN', '4小时', 'admin', NOW(), NOW()),
('dict_data', 124, 'dict_label', 'zh-TW', '4小時', 'admin', NOW(), NOW()),
('dict_data', 124, 'dict_label', 'en-US', '4 Hours', 'admin', NOW(), NOW()),
('dict_data', 124, 'dict_label', 'ja-JP', '4時間', 'admin', NOW(), NOW());

-- 125	1天
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 125, 'dict_label', 'zh-CN', '1天', 'admin', NOW(), NOW()),
('dict_data', 125, 'dict_label', 'zh-TW', '1天', 'admin', NOW(), NOW()),
('dict_data', 125, 'dict_label', 'en-US', '1 Day', 'admin', NOW(), NOW()),
('dict_data', 125, 'dict_label', 'ja-JP', '1日', 'admin', NOW(), NOW());

-- 126	停用
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 126, 'dict_label', 'zh-CN', '停用', 'admin', NOW(), NOW()),
('dict_data', 126, 'dict_label', 'zh-TW', '停用', 'admin', NOW(), NOW()),
('dict_data', 126, 'dict_label', 'en-US', 'Disabled', 'admin', NOW(), NOW()),
('dict_data', 126, 'dict_label', 'ja-JP', '無効', 'admin', NOW(), NOW());

-- 127	启用
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 127, 'dict_label', 'zh-CN', '启用', 'admin', NOW(), NOW()),
('dict_data', 127, 'dict_label', 'zh-TW', '啟用', 'admin', NOW(), NOW()),
('dict_data', 127, 'dict_label', 'en-US', 'Enabled', 'admin', NOW(), NOW()),
('dict_data', 127, 'dict_label', 'ja-JP', '有効', 'admin', NOW(), NOW());

-- 128	运行中
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 128, 'dict_label', 'zh-CN', '运行中', 'admin', NOW(), NOW()),
('dict_data', 128, 'dict_label', 'zh-TW', '運行中', 'admin', NOW(), NOW()),
('dict_data', 128, 'dict_label', 'en-US', 'Running', 'admin', NOW(), NOW()),
('dict_data', 128, 'dict_label', 'ja-JP', '実行中', 'admin', NOW(), NOW());

-- 129	布林带策略
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 129, 'dict_label', 'zh-CN', '布林带策略', 'admin', NOW(), NOW()),
('dict_data', 129, 'dict_label', 'zh-TW', '布林帶策略', 'admin', NOW(), NOW()),
('dict_data', 129, 'dict_label', 'en-US', 'Bollinger Bands Strategy', 'admin', NOW(), NOW()),
('dict_data', 129, 'dict_label', 'ja-JP', 'ボリンジャーバンド戦略', 'admin', NOW(), NOW());

-- 130	突破策略
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 130, 'dict_label', 'zh-CN', '突破策略', 'admin', NOW(), NOW()),
('dict_data', 130, 'dict_label', 'zh-TW', '突破策略', 'admin', NOW(), NOW()),
('dict_data', 130, 'dict_label', 'en-US', 'Breakout Strategy', 'admin', NOW(), NOW()),
('dict_data', 130, 'dict_label', 'ja-JP', 'ブレイクアウト戦略', 'admin', NOW(), NOW());

-- 131	MACD策略
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 131, 'dict_label', 'zh-CN', 'MACD策略', 'admin', NOW(), NOW()),
('dict_data', 131, 'dict_label', 'zh-TW', 'MACD策略', 'admin', NOW(), NOW()),
('dict_data', 131, 'dict_label', 'en-US', 'MACD Strategy', 'admin', NOW(), NOW()),
('dict_data', 131, 'dict_label', 'ja-JP', 'MACD戦略', 'admin', NOW(), NOW());

-- 132	RSI策略
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 132, 'dict_label', 'zh-CN', 'RSI策略', 'admin', NOW(), NOW()),
('dict_data', 132, 'dict_label', 'zh-TW', 'RSI策略', 'admin', NOW(), NOW()),
('dict_data', 132, 'dict_label', 'en-US', 'RSI Strategy', 'admin', NOW(), NOW()),
('dict_data', 132, 'dict_label', 'ja-JP', 'RSI戦略', 'admin', NOW(), NOW());

-- 133	BTC/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 133, 'dict_label', 'zh-CN', 'BTC/USDT', 'admin', NOW(), NOW()),
('dict_data', 133, 'dict_label', 'zh-TW', 'BTC/USDT', 'admin', NOW(), NOW()),
('dict_data', 133, 'dict_label', 'en-US', 'BTC/USDT', 'admin', NOW(), NOW()),
('dict_data', 133, 'dict_label', 'ja-JP', 'BTC/USDT', 'admin', NOW(), NOW());

-- 134	ETH/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 134, 'dict_label', 'zh-CN', 'ETH/USDT', 'admin', NOW(), NOW()),
('dict_data', 134, 'dict_label', 'zh-TW', 'ETH/USDT', 'admin', NOW(), NOW()),
('dict_data', 134, 'dict_label', 'en-US', 'ETH/USDT', 'admin', NOW(), NOW()),
('dict_data', 134, 'dict_label', 'ja-JP', 'ETH/USDT', 'admin', NOW(), NOW());

-- 135	BNB/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 135, 'dict_label', 'zh-CN', 'BNB/USDT', 'admin', NOW(), NOW()),
('dict_data', 135, 'dict_label', 'zh-TW', 'BNB/USDT', 'admin', NOW(), NOW()),
('dict_data', 135, 'dict_label', 'en-US', 'BNB/USDT', 'admin', NOW(), NOW()),
('dict_data', 135, 'dict_label', 'ja-JP', 'BNB/USDT', 'admin', NOW(), NOW());

-- 136	SOL/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 136, 'dict_label', 'zh-CN', 'SOL/USDT', 'admin', NOW(), NOW()),
('dict_data', 136, 'dict_label', 'zh-TW', 'SOL/USDT', 'admin', NOW(), NOW()),
('dict_data', 136, 'dict_label', 'en-US', 'SOL/USDT', 'admin', NOW(), NOW()),
('dict_data', 136, 'dict_label', 'ja-JP', 'SOL/USDT', 'admin', NOW(), NOW());

-- 137	XRP/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 137, 'dict_label', 'zh-CN', 'XRP/USDT', 'admin', NOW(), NOW()),
('dict_data', 137, 'dict_label', 'zh-TW', 'XRP/USDT', 'admin', NOW(), NOW()),
('dict_data', 137, 'dict_label', 'en-US', 'XRP/USDT', 'admin', NOW(), NOW()),
('dict_data', 137, 'dict_label', 'ja-JP', 'XRP/USDT', 'admin', NOW(), NOW());

-- 138	ADA/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 138, 'dict_label', 'zh-CN', 'ADA/USDT', 'admin', NOW(), NOW()),
('dict_data', 138, 'dict_label', 'zh-TW', 'ADA/USDT', 'admin', NOW(), NOW()),
('dict_data', 138, 'dict_label', 'en-US', 'ADA/USDT', 'admin', NOW(), NOW()),
('dict_data', 138, 'dict_label', 'ja-JP', 'ADA/USDT', 'admin', NOW(), NOW());

-- 139	DOGE/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 139, 'dict_label', 'zh-CN', 'DOGE/USDT', 'admin', NOW(), NOW()),
('dict_data', 139, 'dict_label', 'zh-TW', 'DOGE/USDT', 'admin', NOW(), NOW()),
('dict_data', 139, 'dict_label', 'en-US', 'DOGE/USDT', 'admin', NOW(), NOW()),
('dict_data', 139, 'dict_label', 'ja-JP', 'DOGE/USDT', 'admin', NOW(), NOW());

-- 140	POL/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 140, 'dict_label', 'zh-CN', 'POL/USDT', 'admin', NOW(), NOW()),
('dict_data', 140, 'dict_label', 'zh-TW', 'POL/USDT', 'admin', NOW(), NOW()),
('dict_data', 140, 'dict_label', 'en-US', 'POL/USDT', 'admin', NOW(), NOW()),
('dict_data', 140, 'dict_label', 'ja-JP', 'POL/USDT', 'admin', NOW(), NOW());

-- 141	DOT/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 141, 'dict_label', 'zh-CN', 'DOT/USDT', 'admin', NOW(), NOW()),
('dict_data', 141, 'dict_label', 'zh-TW', 'DOT/USDT', 'admin', NOW(), NOW()),
('dict_data', 141, 'dict_label', 'en-US', 'DOT/USDT', 'admin', NOW(), NOW()),
('dict_data', 141, 'dict_label', 'ja-JP', 'DOT/USDT', 'admin', NOW(), NOW());

-- 142	LTC/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 142, 'dict_label', 'zh-CN', 'LTC/USDT', 'admin', NOW(), NOW()),
('dict_data', 142, 'dict_label', 'zh-TW', 'LTC/USDT', 'admin', NOW(), NOW()),
('dict_data', 142, 'dict_label', 'en-US', 'LTC/USDT', 'admin', NOW(), NOW()),
('dict_data', 142, 'dict_label', 'ja-JP', 'LTC/USDT', 'admin', NOW(), NOW());

-- 143	LINK/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 143, 'dict_label', 'zh-CN', 'LINK/USDT', 'admin', NOW(), NOW()),
('dict_data', 143, 'dict_label', 'zh-TW', 'LINK/USDT', 'admin', NOW(), NOW()),
('dict_data', 143, 'dict_label', 'en-US', 'LINK/USDT', 'admin', NOW(), NOW()),
('dict_data', 143, 'dict_label', 'ja-JP', 'LINK/USDT', 'admin', NOW(), NOW());

-- 144	UNI/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 144, 'dict_label', 'zh-CN', 'UNI/USDT', 'admin', NOW(), NOW()),
('dict_data', 144, 'dict_label', 'zh-TW', 'UNI/USDT', 'admin', NOW(), NOW()),
('dict_data', 144, 'dict_label', 'en-US', 'UNI/USDT', 'admin', NOW(), NOW()),
('dict_data', 144, 'dict_label', 'ja-JP', 'UNI/USDT', 'admin', NOW(), NOW());

-- 145	AVAX/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 145, 'dict_label', 'zh-CN', 'AVAX/USDT', 'admin', NOW(), NOW()),
('dict_data', 145, 'dict_label', 'zh-TW', 'AVAX/USDT', 'admin', NOW(), NOW()),
('dict_data', 145, 'dict_label', 'en-US', 'AVAX/USDT', 'admin', NOW(), NOW()),
('dict_data', 145, 'dict_label', 'ja-JP', 'AVAX/USDT', 'admin', NOW(), NOW());

-- 146	ATOM/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 146, 'dict_label', 'zh-CN', 'ATOM/USDT', 'admin', NOW(), NOW()),
('dict_data', 146, 'dict_label', 'zh-TW', 'ATOM/USDT', 'admin', NOW(), NOW()),
('dict_data', 146, 'dict_label', 'en-US', 'ATOM/USDT', 'admin', NOW(), NOW()),
('dict_data', 146, 'dict_label', 'ja-JP', 'ATOM/USDT', 'admin', NOW(), NOW());

-- 147	ETC/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 147, 'dict_label', 'zh-CN', 'ETC/USDT', 'admin', NOW(), NOW()),
('dict_data', 147, 'dict_label', 'zh-TW', 'ETC/USDT', 'admin', NOW(), NOW()),
('dict_data', 147, 'dict_label', 'en-US', 'ETC/USDT', 'admin', NOW(), NOW()),
('dict_data', 147, 'dict_label', 'ja-JP', 'ETC/USDT', 'admin', NOW(), NOW());

-- 148	XLM/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 148, 'dict_label', 'zh-CN', 'XLM/USDT', 'admin', NOW(), NOW()),
('dict_data', 148, 'dict_label', 'zh-TW', 'XLM/USDT', 'admin', NOW(), NOW()),
('dict_data', 148, 'dict_label', 'en-US', 'XLM/USDT', 'admin', NOW(), NOW()),
('dict_data', 148, 'dict_label', 'ja-JP', 'XLM/USDT', 'admin', NOW(), NOW());

-- 149	TRX/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 149, 'dict_label', 'zh-CN', 'TRX/USDT', 'admin', NOW(), NOW()),
('dict_data', 149, 'dict_label', 'zh-TW', 'TRX/USDT', 'admin', NOW(), NOW()),
('dict_data', 149, 'dict_label', 'en-US', 'TRX/USDT', 'admin', NOW(), NOW()),
('dict_data', 149, 'dict_label', 'ja-JP', 'TRX/USDT', 'admin', NOW(), NOW());

-- 150	NEAR/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 150, 'dict_label', 'zh-CN', 'NEAR/USDT', 'admin', NOW(), NOW()),
('dict_data', 150, 'dict_label', 'zh-TW', 'NEAR/USDT', 'admin', NOW(), NOW()),
('dict_data', 150, 'dict_label', 'en-US', 'NEAR/USDT', 'admin', NOW(), NOW()),
('dict_data', 150, 'dict_label', 'ja-JP', 'NEAR/USDT', 'admin', NOW(), NOW());

-- 151	ALGO/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 151, 'dict_label', 'zh-CN', 'ALGO/USDT', 'admin', NOW(), NOW()),
('dict_data', 151, 'dict_label', 'zh-TW', 'ALGO/USDT', 'admin', NOW(), NOW()),
('dict_data', 151, 'dict_label', 'en-US', 'ALGO/USDT', 'admin', NOW(), NOW()),
('dict_data', 151, 'dict_label', 'ja-JP', 'ALGO/USDT', 'admin', NOW(), NOW());

-- 152	VET/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 152, 'dict_label', 'zh-CN', 'VET/USDT', 'admin', NOW(), NOW()),
('dict_data', 152, 'dict_label', 'zh-TW', 'VET/USDT', 'admin', NOW(), NOW()),
('dict_data', 152, 'dict_label', 'en-US', 'VET/USDT', 'admin', NOW(), NOW()),
('dict_data', 152, 'dict_label', 'ja-JP', 'VET/USDT', 'admin', NOW(), NOW());

-- 153	主流币
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 153, 'dict_label', 'zh-CN', '主流币', 'admin', NOW(), NOW()),
('dict_data', 153, 'dict_label', 'zh-TW', '主流幣', 'admin', NOW(), NOW()),
('dict_data', 153, 'dict_label', 'en-US', 'Mainstream', 'admin', NOW(), NOW()),
('dict_data', 153, 'dict_label', 'ja-JP', 'メインストリーム', 'admin', NOW(), NOW());

-- 154	Top10
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 154, 'dict_label', 'zh-CN', 'Top10', 'admin', NOW(), NOW()),
('dict_data', 154, 'dict_label', 'zh-TW', 'Top10', 'admin', NOW(), NOW()),
('dict_data', 154, 'dict_label', 'en-US', 'Top10', 'admin', NOW(), NOW()),
('dict_data', 154, 'dict_label', 'ja-JP', 'Top10', 'admin', NOW(), NOW());

-- 155	Top20
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 155, 'dict_label', 'zh-CN', 'Top20', 'admin', NOW(), NOW()),
('dict_data', 155, 'dict_label', 'zh-TW', 'Top20', 'admin', NOW(), NOW()),
('dict_data', 155, 'dict_label', 'en-US', 'Top20', 'admin', NOW(), NOW()),
('dict_data', 155, 'dict_label', 'ja-JP', 'Top20', 'admin', NOW(), NOW());

-- 156	DeFi币种
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 156, 'dict_label', 'zh-CN', 'DeFi币种', 'admin', NOW(), NOW()),
('dict_data', 156, 'dict_label', 'zh-TW', 'DeFi幣種', 'admin', NOW(), NOW()),
('dict_data', 156, 'dict_label', 'en-US', 'DeFi Coins', 'admin', NOW(), NOW()),
('dict_data', 156, 'dict_label', 'ja-JP', 'DeFiコイン', 'admin', NOW(), NOW());

-- 157	SHIB/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 157, 'dict_label', 'zh-CN', 'SHIB/USDT', 'admin', NOW(), NOW()),
('dict_data', 157, 'dict_label', 'zh-TW', 'SHIB/USDT', 'admin', NOW(), NOW()),
('dict_data', 157, 'dict_label', 'en-US', 'SHIB/USDT', 'admin', NOW(), NOW()),
('dict_data', 157, 'dict_label', 'ja-JP', 'SHIB/USDT', 'admin', NOW(), NOW());

-- 158	APT/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 158, 'dict_label', 'zh-CN', 'APT/USDT', 'admin', NOW(), NOW()),
('dict_data', 158, 'dict_label', 'zh-TW', 'APT/USDT', 'admin', NOW(), NOW()),
('dict_data', 158, 'dict_label', 'en-US', 'APT/USDT', 'admin', NOW(), NOW()),
('dict_data', 158, 'dict_label', 'ja-JP', 'APT/USDT', 'admin', NOW(), NOW());

-- 159	ARB/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 159, 'dict_label', 'zh-CN', 'ARB/USDT', 'admin', NOW(), NOW()),
('dict_data', 159, 'dict_label', 'zh-TW', 'ARB/USDT', 'admin', NOW(), NOW()),
('dict_data', 159, 'dict_label', 'en-US', 'ARB/USDT', 'admin', NOW(), NOW()),
('dict_data', 159, 'dict_label', 'ja-JP', 'ARB/USDT', 'admin', NOW(), NOW());

-- 160	OP/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 160, 'dict_label', 'zh-CN', 'OP/USDT', 'admin', NOW(), NOW()),
('dict_data', 160, 'dict_label', 'zh-TW', 'OP/USDT', 'admin', NOW(), NOW()),
('dict_data', 160, 'dict_label', 'en-US', 'OP/USDT', 'admin', NOW(), NOW()),
('dict_data', 160, 'dict_label', 'ja-JP', 'OP/USDT', 'admin', NOW(), NOW());

-- 161	PEPE/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 161, 'dict_label', 'zh-CN', 'PEPE/USDT', 'admin', NOW(), NOW()),
('dict_data', 161, 'dict_label', 'zh-TW', 'PEPE/USDT', 'admin', NOW(), NOW()),
('dict_data', 161, 'dict_label', 'en-US', 'PEPE/USDT', 'admin', NOW(), NOW()),
('dict_data', 161, 'dict_label', 'ja-JP', 'PEPE/USDT', 'admin', NOW(), NOW());

-- 162	ICP/USDT
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 162, 'dict_label', 'zh-CN', 'ICP/USDT', 'admin', NOW(), NOW()),
('dict_data', 162, 'dict_label', 'zh-TW', 'ICP/USDT', 'admin', NOW(), NOW()),
('dict_data', 162, 'dict_label', 'en-US', 'ICP/USDT', 'admin', NOW(), NOW()),
('dict_data', 162, 'dict_label', 'ja-JP', 'ICP/USDT', 'admin', NOW(), NOW());

-- 163	持有
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 163, 'dict_label', 'zh-CN', '持有', 'admin', NOW(), NOW()),
('dict_data', 163, 'dict_label', 'zh-TW', '持有', 'admin', NOW(), NOW()),
('dict_data', 163, 'dict_label', 'en-US', 'Hold', 'admin', NOW(), NOW()),
('dict_data', 163, 'dict_label', 'ja-JP', '保有', 'admin', NOW(), NOW());

-- 164	高风险
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 164, 'dict_label', 'zh-CN', '高风险', 'admin', NOW(), NOW()),
('dict_data', 164, 'dict_label', 'zh-TW', '高風險', 'admin', NOW(), NOW()),
('dict_data', 164, 'dict_label', 'en-US', 'High Risk', 'admin', NOW(), NOW()),
('dict_data', 164, 'dict_label', 'ja-JP', '高リスク', 'admin', NOW(), NOW());

-- 165	中风险
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 165, 'dict_label', 'zh-CN', '中风险', 'admin', NOW(), NOW()),
('dict_data', 165, 'dict_label', 'zh-TW', '中風險', 'admin', NOW(), NOW()),
('dict_data', 165, 'dict_label', 'en-US', 'Medium Risk', 'admin', NOW(), NOW()),
('dict_data', 165, 'dict_label', 'ja-JP', '中リスク', 'admin', NOW(), NOW());

-- 166	低风险
INSERT INTO `sys_i18n_translation` (`entity_type`, `entity_id`, `field_name`, `locale`, `translation`, `create_by`, `create_time`, `update_time`) VALUES
('dict_data', 166, 'dict_label', 'zh-CN', '低风险', 'admin', NOW(), NOW()),
('dict_data', 166, 'dict_label', 'zh-TW', '低風險', 'admin', NOW(), NOW()),
('dict_data', 166, 'dict_label', 'en-US', 'Low Risk', 'admin', NOW(), NOW()),
('dict_data', 166, 'dict_label', 'ja-JP', '低リスク', 'admin', NOW(), NOW());

