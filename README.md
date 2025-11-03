# QuantFlow 量化交易系统

一个功能完整的企业级量化交易系统，支持多交易所、多策略、实时监控。

## 🚀 功能特性

- 📊 **多交易所支持**：币安、OKX 等（可扩展）
- 🤖 **智能策略引擎**：内置均线交叉策略，支持自定义策略
- 📈 **实时行情监控**：WebSocket 实时推送
- 💰 **自动交易执行**：信号自动转订单，智能执行
- 🛡️ **多层风控系统**：下单检查、止损止盈、风控告警
- 📉 **回测系统**：历史数据回测，完整性能分析
- 📱 **可视化界面**：交易仪表盘、K线图表、统计分析

## 🏗️ 技术架构

### 后端
- Spring Boot 3.x
- MyBatis-Plus
- Redis
- WebSocket
- Quartz
- OkHttp

### 前端
- Vue 3
- Element Plus
- ECharts
- WebSocket

### 数据库
- MySQL 8.0（业务数据）
- InfluxDB（时序数据，可选）

## 📦 快速开始

### 环境要求

- JDK 17+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+

### 安装步骤

1. **克隆项目**
```bash
git clone https://github.com/your-repo/quantflow.git
cd quantflow
```

2. **导入数据库**
```bash
mysql -u root -p < sql/quantflow.sql
mysql -u root -p < sql/quantflow_trading.sql
mysql -u root -p < sql/quartz.sql
```

3. **修改配置**
```yaml
# quantflow-admin/src/main/resources/application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/quantflow
    username: root
    password: your_password
  redis:
    host: localhost
    port: 6379
```

4. **启动后端**
```bash
mvn clean install
java -jar quantflow-admin/target/quantflow-admin.jar
```

5. **启动前端**
```bash
cd quantflow-ui
npm install
npm run dev
```

6. **访问系统**
```
地址: http://localhost
账号: admin
密码: admin123
```

## 📖 使用指南

### 1. 配置交易账户

系统管理 → 账户管理 → 新增账户
- 填写账户名称
- 选择交易所
- 配置 API Key
- 测试连接

### 2. 创建交易策略

量化交易 → 策略管理 → 新增策略
- 选择策略类型
- 配置交易对和周期
- 设置策略参数
- 启动策略

### 3. 监控运行状态

量化交易 → 交易仪表盘
- 查看资产总览
- 监控持仓情况
- 查看最近订单和信号

### 4. 回测策略

量化交易 → 回测管理 → 新建回测
- 选择策略
- 设置初始资金
- 执行回测
- 查看结果报告

## ⚠️ 风险提示

- 量化交易存在风险，请谨慎使用
- 建议先使用测试网进行测试
- 设置合理的风控参数
- 不要投入超过承受能力的资金

## 📄 许可证

Apache License 2.0

## 🤝 贡献

欢迎提交 Issue 和 Pull Request

## 📧 联系方式

- Email: your-email@example.com
- GitHub: https://github.com/your-repo/quantflow