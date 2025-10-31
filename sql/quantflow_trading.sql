-- QuantFlow 量化交易系统数据表
-- 版本: 1.0.0
-- 日期: 2025-10-31

USE quantflow;

-- ----------------------------
-- 1. 交易账户表
-- ----------------------------
DROP TABLE IF EXISTS `qf_account`;
CREATE TABLE `qf_account` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '账户ID',
                              `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                              `account_name` varchar(100) NOT NULL COMMENT '账户名称',
                              `exchange` varchar(50) NOT NULL COMMENT '交易所',
                              `api_key` varchar(500) DEFAULT NULL COMMENT 'API Key（加密）',
                              `api_secret` varchar(500) DEFAULT NULL COMMENT 'API Secret（加密）',
                              `passphrase` varchar(500) DEFAULT NULL COMMENT 'Passphrase（加密，某些交易所需要）',
                              `is_testnet` tinyint(1) DEFAULT '0' COMMENT '是否测试网：0否 1是',
                              `status` tinyint(1) DEFAULT '1' COMMENT '状态：0禁用 1启用',
                              `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                              `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                              `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                              PRIMARY KEY (`id`),
                              KEY `idx_user_id` (`user_id`),
                              KEY `idx_exchange` (`exchange`),
                              KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='交易账户表';

-- ----------------------------
-- 2. 持仓表
-- ----------------------------
DROP TABLE IF EXISTS `qf_position`;
CREATE TABLE `qf_position` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '持仓ID',
                               `account_id` bigint(20) NOT NULL COMMENT '账户ID',
                               `symbol` varchar(20) NOT NULL COMMENT '交易对',
                               `quantity` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '持仓数量',
                               `available_qty` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '可用数量',
                               `frozen_qty` decimal(20,8) DEFAULT '0.00000000' COMMENT '冻结数量',
                               `avg_price` decimal(20,8) NOT NULL DEFAULT '0.00000000' COMMENT '平均成本价',
                               `current_price` decimal(20,8) DEFAULT '0.00000000' COMMENT '当前价格',
                               `market_value` decimal(20,8) DEFAULT '0.00000000' COMMENT '市值',
                               `unrealized_pnl` decimal(20,8) DEFAULT '0.00000000' COMMENT '浮动盈亏',
                               `unrealized_pnl_ratio` decimal(10,4) DEFAULT '0.0000' COMMENT '浮动盈亏率(%)',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_account_symbol` (`account_id`,`symbol`),
                               KEY `idx_account_id` (`account_id`),
                               KEY `idx_symbol` (`symbol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='持仓表';

-- ----------------------------
-- 3. 资产快照表
-- ----------------------------
DROP TABLE IF EXISTS `qf_asset_snapshot`;
CREATE TABLE `qf_asset_snapshot` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '快照ID',
                                     `account_id` bigint(20) NOT NULL COMMENT '账户ID',
                                     `total_balance` decimal(20,8) DEFAULT '0.00000000' COMMENT '总资产',
                                     `available_balance` decimal(20,8) DEFAULT '0.00000000' COMMENT '可用余额',
                                     `frozen_balance` decimal(20,8) DEFAULT '0.00000000' COMMENT '冻结金额',
                                     `position_value` decimal(20,8) DEFAULT '0.00000000' COMMENT '持仓市值',
                                     `total_pnl` decimal(20,8) DEFAULT '0.00000000' COMMENT '总盈亏',
                                     `daily_pnl` decimal(20,8) DEFAULT '0.00000000' COMMENT '当日盈亏',
                                     `snapshot_time` datetime NOT NULL COMMENT '快照时间',
                                     `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     PRIMARY KEY (`id`),
                                     KEY `idx_account_id` (`account_id`),
                                     KEY `idx_snapshot_time` (`snapshot_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资产快照表';

-- ----------------------------
-- 4. 策略配置表
-- ----------------------------
DROP TABLE IF EXISTS `qf_strategy`;
CREATE TABLE `qf_strategy` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '策略ID',
                               `name` varchar(100) NOT NULL COMMENT '策略名称',
                               `code` varchar(50) DEFAULT NULL COMMENT '策略代码',
                               `type` varchar(50) DEFAULT NULL COMMENT '策略类型',
                               `description` text COMMENT '策略描述',
                               `account_id` bigint(20) NOT NULL COMMENT '交易账户ID',
                               `symbol` varchar(20) DEFAULT NULL COMMENT '交易对',
                               `interval` varchar(10) DEFAULT NULL COMMENT 'K线周期',
                               `params` json DEFAULT NULL COMMENT '策略参数（JSON格式）',
                               `status` tinyint(1) DEFAULT '0' COMMENT '状态：0停用 1启用 2运行中',
                               `user_id` bigint(20) DEFAULT NULL COMMENT '创建用户ID',
                               `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                               `remark` varchar(500) DEFAULT NULL COMMENT '备注',
                               PRIMARY KEY (`id`),
                               UNIQUE KEY `uk_code` (`code`),
                               KEY `idx_user_id` (`user_id`),
                               KEY `idx_account_id` (`account_id`),
                               KEY `idx_symbol` (`symbol`),
                               KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略配置表';

-- ----------------------------
-- 5. 策略信号表
-- ----------------------------
DROP TABLE IF EXISTS `qf_signal`;
CREATE TABLE `qf_signal` (
                             `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '信号ID',
                             `strategy_id` bigint(20) NOT NULL COMMENT '策略ID',
                             `symbol` varchar(20) NOT NULL COMMENT '交易对',
                             `signal_type` varchar(10) NOT NULL COMMENT '信号类型：BUY/SELL',
                             `price` decimal(20,8) DEFAULT NULL COMMENT '信号价格',
                             `quantity` decimal(20,8) DEFAULT NULL COMMENT '建议数量',
                             `reason` text COMMENT '信号原因',
                             `confidence` decimal(5,2) DEFAULT NULL COMMENT '置信度',
                             `status` tinyint(1) DEFAULT '0' COMMENT '状态：0待处理 1已处理 2已忽略',
                             `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             PRIMARY KEY (`id`),
                             KEY `idx_strategy_id` (`strategy_id`),
                             KEY `idx_symbol` (`symbol`),
                             KEY `idx_status` (`status`),
                             KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='策略信号表';

-- ----------------------------
-- 6. 订单表
-- ----------------------------
DROP TABLE IF EXISTS `qf_order`;
CREATE TABLE `qf_order` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单ID',
                            `order_no` varchar(50) NOT NULL COMMENT '订单号',
                            `strategy_id` bigint(20) DEFAULT NULL COMMENT '策略ID',
                            `account_id` bigint(20) NOT NULL COMMENT '账户ID',
                            `symbol` varchar(20) NOT NULL COMMENT '交易对',
                            `side` varchar(10) NOT NULL COMMENT '方向：BUY/SELL',
                            `type` varchar(20) NOT NULL COMMENT '类型：MARKET/LIMIT',
                            `price` decimal(20,8) DEFAULT NULL COMMENT '价格',
                            `quantity` decimal(20,8) NOT NULL COMMENT '数量',
                            `filled_qty` decimal(20,8) DEFAULT '0.00000000' COMMENT '成交数量',
                            `avg_price` decimal(20,8) DEFAULT NULL COMMENT '成交均价',
                            `amount` decimal(20,8) DEFAULT NULL COMMENT '金额',
                            `commission` decimal(20,8) DEFAULT NULL COMMENT '手续费',
                            `status` varchar(20) NOT NULL COMMENT '状态',
                            `exchange_order_id` varchar(100) DEFAULT NULL COMMENT '交易所订单ID',
                            `error_msg` text COMMENT '错误信息',
                            `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                            `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                            PRIMARY KEY (`id`),
                            UNIQUE KEY `uk_order_no` (`order_no`),
                            KEY `idx_strategy_id` (`strategy_id`),
                            KEY `idx_account_id` (`account_id`),
                            KEY `idx_symbol` (`symbol`),
                            KEY `idx_status` (`status`),
                            KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- ----------------------------
-- 7. 成交记录表
-- ----------------------------
DROP TABLE IF EXISTS `qf_trade`;
CREATE TABLE `qf_trade` (
                            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '成交ID',
                            `order_id` bigint(20) NOT NULL COMMENT '订单ID',
                            `trade_id` varchar(100) DEFAULT NULL COMMENT '成交ID',
                            `symbol` varchar(20) NOT NULL COMMENT '交易对',
                            `side` varchar(10) NOT NULL COMMENT '方向',
                            `price` decimal(20,8) NOT NULL COMMENT '成交价格',
                            `quantity` decimal(20,8) NOT NULL COMMENT '成交数量',
                            `amount` decimal(20,8) NOT NULL COMMENT '成交金额',
                            `commission` decimal(20,8) DEFAULT NULL COMMENT '手续费',
                            `trade_time` datetime NOT NULL COMMENT '成交时间',
                            `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            PRIMARY KEY (`id`),
                            KEY `idx_order_id` (`order_id`),
                            KEY `idx_symbol` (`symbol`),
                            KEY `idx_trade_time` (`trade_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='成交记录表';

-- ----------------------------
-- 8. 风控配置表
-- ----------------------------
DROP TABLE IF EXISTS `qf_risk_config`;
CREATE TABLE `qf_risk_config` (
                                  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '配置ID',
                                  `name` varchar(100) NOT NULL COMMENT '配置名称',
                                  `level` varchar(20) NOT NULL COMMENT '级别：STRATEGY/ACCOUNT/SYSTEM',
                                  `target_id` bigint(20) DEFAULT NULL COMMENT '目标ID（策略ID或账户ID）',
                                  `max_order_amount` decimal(20,8) DEFAULT NULL COMMENT '单笔最大金额',
                                  `max_position_ratio` decimal(5,2) DEFAULT NULL COMMENT '最大持仓比例（%）',
                                  `max_daily_loss` decimal(20,8) DEFAULT NULL COMMENT '单日最大亏损',
                                  `max_daily_trades` int(11) DEFAULT NULL COMMENT '单日最大交易次数',
                                  `stop_loss_ratio` decimal(5,2) DEFAULT NULL COMMENT '止损比例（%）',
                                  `take_profit_ratio` decimal(5,2) DEFAULT NULL COMMENT '止盈比例（%）',
                                  `status` tinyint(1) DEFAULT '1' COMMENT '状态：0禁用 1启用',
                                  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                                  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
                                  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                  PRIMARY KEY (`id`),
                                  KEY `idx_level_target` (`level`,`target_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='风控配置表';

-- ----------------------------
-- 9. 风控日志表
-- ----------------------------
DROP TABLE IF EXISTS `qf_risk_log`;
CREATE TABLE `qf_risk_log` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
                               `type` varchar(50) NOT NULL COMMENT '风控类型',
                               `level` varchar(20) NOT NULL COMMENT '风控级别：WARNING/ERROR/CRITICAL',
                               `target_type` varchar(20) DEFAULT NULL COMMENT '目标类型：STRATEGY/ACCOUNT/ORDER',
                               `target_id` bigint(20) DEFAULT NULL COMMENT '目标ID',
                               `message` text NOT NULL COMMENT '风控信息',
                               `action` varchar(50) DEFAULT NULL COMMENT '处理动作：BLOCK/ALERT/SUSPEND',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_type` (`type`),
                               KEY `idx_level` (`level`),
                               KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='风控日志表';

-- ----------------------------
-- 10. 回测任务表
-- ----------------------------
DROP TABLE IF EXISTS `qf_backtest`;
CREATE TABLE `qf_backtest` (
                               `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务ID',
                               `name` varchar(100) NOT NULL COMMENT '任务名称',
                               `strategy_id` bigint(20) NOT NULL COMMENT '策略ID',
                               `symbol` varchar(20) NOT NULL COMMENT '交易对',
                               `interval` varchar(10) NOT NULL COMMENT 'K线周期',
                               `start_date` date NOT NULL COMMENT '回测开始日期',
                               `end_date` date NOT NULL COMMENT '回测结束日期',
                               `initial_capital` decimal(20,8) NOT NULL COMMENT '初始资金',
                               `commission_rate` decimal(5,4) DEFAULT NULL COMMENT '手续费率',
                               `slippage_rate` decimal(5,4) DEFAULT NULL COMMENT '滑点率',
                               `status` varchar(20) NOT NULL COMMENT '状态：PENDING/RUNNING/COMPLETED/FAILED',
                               `result` json DEFAULT NULL COMMENT '回测结果（JSON）',
                               `total_return` decimal(10,4) DEFAULT NULL COMMENT '总收益率',
                               `annual_return` decimal(10,4) DEFAULT NULL COMMENT '年化收益率',
                               `max_drawdown` decimal(10,4) DEFAULT NULL COMMENT '最大回撤',
                               `sharpe_ratio` decimal(10,4) DEFAULT NULL COMMENT '夏普比率',
                               `win_rate` decimal(5,2) DEFAULT NULL COMMENT '胜率',
                               `trade_count` int(11) DEFAULT NULL COMMENT '交易次数',
                               `error_msg` text COMMENT '错误信息',
                               `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                               `complete_time` datetime DEFAULT NULL COMMENT '完成时间',
                               PRIMARY KEY (`id`),
                               KEY `idx_strategy_id` (`strategy_id`),
                               KEY `idx_status` (`status`),
                               KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='回测任务表';

-- ----------------------------
-- 初始化测试数据（可选）
-- ----------------------------

-- 插入测试账户
INSERT INTO `qf_account` (`user_id`, `account_name`, `exchange`, `is_testnet`, `status`, `create_by`, `remark`)
VALUES (1, '币安测试账户', 'binance', 1, 1, 'admin', '用于测试的币安账户');

-- 插入系统级风控配置
INSERT INTO `qf_risk_config` (`name`, `level`, `max_order_amount`, `max_position_ratio`, `max_daily_loss`, `max_daily_trades`, `stop_loss_ratio`, `take_profit_ratio`, `create_by`)
VALUES ('系统默认风控', 'SYSTEM', 10000.00000000, 80.00, 5000.00000000, 100, 5.00, 10.00, 'admin');