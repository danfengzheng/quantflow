# 多语言架构设计文档

## 📋 架构概述

系统采用**接口 + 枚举**的设计模式，实现统一的多语言消息管理，支持模块化扩展。

## 🏗️ 核心组件

### 1. I18nMessageKey 接口
**位置**: `quantflow-common/src/main/java/com/quantflow/common/constant/I18nMessageKey.java`

**作用**: 
- 定义多语言消息键的标准接口
- 所有消息键枚举必须实现此接口
- 提供统一的 `getKey()` 和 `getMsg()` 方法

**接口定义**:
```java
public interface I18nMessageKey {
    String getKey();  // 获取数字编码的消息键
    String getMsg();  // 获取中文说明（仅用于调试）
}
```

### 2. MessageKeys 枚举（系统核心模块）
**位置**: `quantflow-common/src/main/java/com/quantflow/common/constant/MessageKeys.java`

**编码范围**: 1000-13999
- 1000-1999: 错误消息
- 2000-2999: 验证消息
- 3000-3999: 文件上传消息
- 4000-4999: 权限消息
- 5000-5999: 用户管理
- 6000-6999: 角色管理
- 7000-7999: 岗位管理
- 8000-8999: 字典管理
- 9000-9999: 部门管理
- 10000-10999: 系统配置
- 11000-11999: 代码生成
- 12000-12999: 限流
- 13000-13999: 安全工具

### 3. ServiceException 异常类
**位置**: `quantflow-common/src/main/java/com/quantflow/common/exception/ServiceException.java`

**支持**:
- 接受 `I18nMessageKey` 接口类型，支持所有实现该接口的枚举
- 自动从国际化资源文件获取对应语言的消息

## 🔧 扩展指南

### 为新增模块创建消息键枚举

#### 步骤1: 创建枚举类
```java
package com.quantflow.trading.constant;

import com.quantflow.common.constant.I18nMessageKey;

/**
 * 交易模块国际化消息键枚举
 * 编码范围: 20000-29999
 */
public enum TradingMessageKeys implements I18nMessageKey {
    
    /** 订单创建失败 */
    ORDER_CREATE_FAILED("20001", "订单创建失败"),
    
    /** 账户余额不足 */
    INSUFFICIENT_BALANCE("20002", "账户余额不足");
    
    private final String key;
    private final String msg;
    
    TradingMessageKeys(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
    
    @Override
    public String getKey() {
        return key;
    }
    
    @Override
    public String getMsg() {
        return msg;
    }
}
```

#### 步骤2: 在国际化文件中添加消息
在 `quantflow-admin/src/main/resources/i18n/` 目录下的所有语言文件中添加：

**messages_zh_CN.properties**:
```
20001=订单创建失败
20002=账户余额不足
```

**messages_zh_TW.properties**:
```
20001=訂單創建失敗
20002=賬戶餘額不足
```

**messages_en_US.properties**:
```
20001=Order creation failed
20002=Insufficient balance
```

**messages_ja_JP.properties**:
```
20001=注文の作成に失敗しました
20002=残高不足
```

#### 步骤3: 在代码中使用
```java
// 使用模块自己的消息键
throw new ServiceException(TradingMessageKeys.ORDER_CREATE_FAILED);

// 带参数
throw new ServiceException(TradingMessageKeys.INSUFFICIENT_BALANCE, amount);
```

## 📝 编码规范

### 编码范围分配
- **系统核心模块**: 1000-13999
- **交易模块**: 20000-29999
- **其他业务模块**: 30000-39999, 40000-49999, ...

### 命名规范
1. 枚举名称使用大写下划线：`ORDER_CREATE_FAILED`
2. 消息键使用数字编码：`"20001"`
3. 中文说明清晰描述消息含义

### 使用规范
1. **必须**使用枚举，禁止硬编码字符串
2. **必须**实现 `I18nMessageKey` 接口
3. **必须**在所有4种语言的资源文件中添加对应消息
4. **建议**按模块划分编码范围，避免冲突

## ✅ 优势

1. **类型安全**: 编译时检查，避免拼写错误
2. **IDE支持**: 自动补全，提高开发效率
3. **统一管理**: 所有消息键集中管理，便于维护
4. **易于扩展**: 新模块只需实现接口即可
5. **调试友好**: `getMsg()` 方法提供中文说明，便于调试
6. **多语言支持**: 自动根据请求头选择语言

## 📚 示例

### 系统核心模块
```java
throw new ServiceException(MessageKeys.USER_NOT_EXISTS);
throw new ServiceException(MessageKeys.USER_IMPORT_FAILED, failureNum, errorMsg);
```

### 交易模块（扩展示例）
```java
throw new ServiceException(TradingMessageKeys.ORDER_CREATE_FAILED);
throw new ServiceException(TradingMessageKeys.INSUFFICIENT_BALANCE, amount);
```

## 🔍 调试技巧

在调试时，可以通过 `getMsg()` 方法查看中文说明：
```java
MessageKeys key = MessageKeys.USER_NOT_EXISTS;
System.out.println("消息键: " + key.getKey());  // 输出: 1004
System.out.println("中文说明: " + key.getMsg()); // 输出: 用户不存在/密码错误
```

