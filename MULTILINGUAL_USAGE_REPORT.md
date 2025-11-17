# 多语言使用情况报告

## 📊 当前状态总结

### ✅ 已正确使用多语言的部分

#### 后端代码
1. **BaseException 及其子类** - ✅ 已使用国际化
   - `UserException` 及其子类（`UserPasswordNotMatchException` 等）
   - 通过 `MessageUtils.message(code, args)` 获取国际化消息
   - 示例：`super("user.password.not.match", null)`

2. **Service 层** - ✅ 已使用国际化
   - `SysLoginService` - 使用 `MessageUtils.message()` 获取消息
   - `SysRegisterService` - 使用 `MessageUtils.message()` 获取消息
   - `UserDetailsServiceImpl` - 使用 `MessageUtils.message()` 获取消息
   - `LogoutSuccessHandlerImpl` - 使用 `MessageUtils.message()` 获取消息

#### 前端代码
1. **国际化配置文件** - ✅ 已存在
   - `ruoyi-ui/src/lang/zh-CN.js` - 简体中文
   - `ruoyi-ui/src/lang/en-US.js` - 英文
   - 缺少：繁体中文、日语

2. **部分组件** - ✅ 已使用国际化
   - `ruoyi-ui/src/plugins/modal.js` - 使用了 `i18n.t()`

### ❌ 未使用多语言的部分（需要修复）

#### 后端代码
1. **ServiceException** - ❌ 未使用国际化
   - 直接使用硬编码的 message 字符串
   - 位置：`quantflow-common/src/main/java/com/quantflow/common/exception/ServiceException.java`
   - 问题：`new ServiceException("错误消息")` 直接传入中文字符串

2. **GlobalException** - ❌ 未使用国际化
   - 直接使用硬编码的 message 字符串

#### 前端代码
1. **大量硬编码中文字符串** - ❌ 需要替换为国际化
   
   需要修复的文件：
   - `ruoyi-ui/src/views/trading/backtest/index.vue`
     - "删除成功" → `this.$t('message.success.delete')`
     - "新增成功" → `this.$t('message.success.add')`
     - "结束日期必须大于开始日期" → 需要添加到国际化文件
     - "未知错误" → `this.$t('message.error.unknown')`
     - "回测失败原因" → 需要添加到国际化文件
     - "确定" → `this.$t('button.confirm')`
   
   - `ruoyi-ui/src/views/trading/order/index.vue`
     - "新增成功" → `this.$t('message.success.add')`
     - "删除成功" → `this.$t('message.success.delete')`
   
   - `ruoyi-ui/src/views/trading/strategy/index.vue`
     - "新增成功" → `this.$t('message.success.add')`
     - "删除成功" → `this.$t('message.success.delete')`
   
   - `ruoyi-ui/src/views/trading/account/index.vue`
     - "新增成功" → `this.$t('message.success.add')`
     - "删除成功" → `this.$t('message.success.delete')`
   
   - `ruoyi-ui/src/views/system/user/index.vue`
     - "新增成功" → `this.$t('message.success.add')`
     - "删除成功" → `this.$t('message.success.delete')`
   
   - `ruoyi-ui/src/views/system/dict/data.vue`
     - "新增成功" → `this.$t('message.success.add')`
     - "删除成功" → `this.$t('message.success.delete')`
   
   - `ruoyi-ui/src/views/tool/gen/index.vue`
     - "删除成功" → `this.$t('message.success.delete')`
   
   - `ruoyi-ui/src/views/monitor/job/log.vue`
     - "删除成功" → `this.$t('message.success.delete')`

2. **缺少前端国际化文件**
   - 需要创建：`ruoyi-ui/src/lang/zh-TW.js` (繁体中文)
   - 需要创建：`ruoyi-ui/src/lang/ja-JP.js` (日语)

## 🔧 修复建议

### 优先级1：后端异常类修复

#### 修复 ServiceException
```java
// 当前代码（错误）
throw new ServiceException("用户不存在");

// 修复后（正确）
throw new ServiceException(MessageUtils.message("user.not.exists"));
```

### 优先级2：前端硬编码字符串修复

#### 示例修复
```javascript
// 当前代码（错误）
this.$modal.msgSuccess("删除成功");

// 修复后（正确）
this.$modal.msgSuccess(this.$t('message.success.delete'));
```

### 优先级3：补充前端国际化文件

需要创建：
- `ruoyi-ui/src/lang/zh-TW.js` - 繁体中文翻译
- `ruoyi-ui/src/lang/ja-JP.js` - 日语翻译

并在 `ruoyi-ui/src/lang/index.js` 中注册这些语言。

## 📝 需要添加到国际化文件的键值

### 后端 messages_*.properties
所有文件已包含基本消息，但可能需要根据实际使用情况补充。

### 前端 lang/*.js
需要添加的键值（如果缺失）：
- `message.error.unknown` - 未知错误
- `message.validate.dateRange` - 日期范围验证消息
- `module.backtest.errorReason` - 回测失败原因

## 🎯 总结

**后端多语言使用情况：** 70% ✅
- BaseException 系列已正确使用
- Service 层已正确使用
- ServiceException 需要修复

**前端多语言使用情况：** 30% ✅
- 国际化配置文件存在但不完整
- 大量硬编码中文字符串需要替换
- 缺少繁体中文和日语的前端翻译文件

**建议修复顺序：**
1. 修复后端 ServiceException 使用国际化
2. 创建前端繁体中文和日语翻译文件
3. 逐步替换前端硬编码字符串

