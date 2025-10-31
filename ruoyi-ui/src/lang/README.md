# 多语言使用说明

## 语言文件结构

多语言文件按照以下4个分类组织：

### 1. common（通用）
存放通用的文案，如：确认、取消、提交、搜索等

**使用示例：**
```vue
<el-button>{{ $t('common.confirm') }}</el-button>
<span>{{ $t('common.status') }}</span>
```

### 2. button（按钮）
存放所有按钮相关的文案

**使用示例：**
```vue
<el-button>{{ $t('button.add') }}</el-button>
<el-button>{{ $t('button.delete') }}</el-button>
<el-button>{{ $t('button.batchDelete') }}</el-button>
```

### 3. module（模块）
存放各个模块特有的文案，按模块划分：
- `module.login` - 登录模块
- `module.navbar` - 导航栏模块
- `module.dashboard` - 仪表盘模块
- `module.system` - 系统管理模块
  - `module.system.user` - 用户管理
  - `module.system.role` - 角色管理
  - `module.system.menu` - 菜单管理
  - `module.system.dept` - 部门管理
  - `module.system.post` - 岗位管理
  - `module.system.dict` - 字典管理
  - `module.system.config` - 参数设置
  - `module.system.notice` - 通知公告
- `module.monitor` - 系统监控模块
  - `module.monitor.online` - 在线用户
  - `module.monitor.job` - 定时任务
  - `module.monitor.loginLog` - 登录日志
  - `module.monitor.operLog` - 操作日志
  - `module.monitor.server` - 服务监控
  - `module.monitor.cache` - 缓存监控
- `module.tool` - 系统工具模块
  - `module.tool.build` - 表单构建
  - `module.tool.gen` - 代码生成
  - `module.tool.swagger` - 系统接口

**使用示例：**
```vue
<el-input :placeholder="$t('module.login.username')"></el-input>
<h3>{{ $t('module.system.user.title') }}</h3>
<el-form-item :label="$t('module.system.user.username')"></el-form-item>
```

### 4. message（提示信息）
存放各类提示、警告、错误、确认等消息：
- `message.success.*` - 成功提示
- `message.error.*` - 错误提示
- `message.warning.*` - 警告提示
- `message.info.*` - 信息提示
- `message.confirm.*` - 确认提示
- `message.validate.*` - 验证提示

**使用示例：**
```javascript
// 成功提示
this.$message.success(this.$t('message.success.add'))
this.$message.success(this.$t('message.success.edit'))

// 错误提示
this.$message.error(this.$t('message.error.delete'))

// 警告提示
this.$message.warning(this.$t('message.warning.delete'))

// 确认对话框
this.$confirm(
  this.$t('message.confirm.delete'),
  this.$t('common.tips'),
  {
    confirmButtonText: this.$t('button.confirm'),
    cancelButtonText: this.$t('button.cancel')
  }
)
```

## 常用键值速查

### 按钮类
- `button.add` - 新增
- `button.edit` - 修改
- `button.delete` - 删除
- `button.export` - 导出
- `button.import` - 导入
- `button.search` - 搜索
- `button.reset` - 重置
- `button.save` - 保存
- `button.submit` - 提交
- `button.confirm` - 确定
- `button.cancel` - 取消
- `button.batchDelete` - 批量删除

### 通用类
- `common.status` - 状态
- `common.operate` - 操作
- `common.enable` - 启用
- `common.disable` - 禁用
- `common.noData` - 暂无数据
- `common.loading` - 加载中...
- `common.total` - 共
- `common.items` - 条

### 提示类
- `message.success.add` - 新增成功
- `message.success.edit` - 修改成功
- `message.success.delete` - 删除成功
- `message.error.add` - 新增失败
- `message.warning.delete` - 此操作将永久删除该数据, 是否继续?
- `message.confirm.delete` - 确定要删除这条数据吗？

## 注意事项

1. **按钮统一使用 `button.*`**：所有按钮文案都应该从 `button` 分类中获取
2. **通用文案使用 `common.*`**：通用的标签、状态等使用 `common` 分类
3. **模块特有使用 `module.*`**：各模块特有的文案放在对应的 `module.*` 下
4. **提示信息使用 `message.*`**：所有提示、警告、错误信息使用 `message` 分类
5. **新增语言键值**：需要在 `zh-CN.js` 和 `en-US.js` 中同时添加

## 添加新语言

如需添加其他语言（如日语、韩语等），需要：
1. 创建对应的语言文件，如 `ja-JP.js`
2. 在 `src/lang/index.js` 中引入并注册
3. 保持与 `zh-CN.js` 和 `en-US.js` 相同的结构

