# 多语言使用说明

## 语言文件结构

多语言文件按照以下**6个分类**组织：

### 1. common（通用）
存放通用的文案，如：确认、取消、提交、搜索、状态、时间等

**使用示例：**
```vue
<el-button>{{ $t('common.confirm') }}</el-button>
<span>{{ $t('common.status') }}</span>
<el-date-picker :placeholder="$t('common.startTime')" />
```

**常用键值：**
- `common.confirm` - 确定
- `common.cancel` - 取消
- `common.status` - 状态
- `common.loading` - 加载中...
- `common.noData` - 暂无数据
- `common.startTime` - 开始时间
- `common.endTime` - 结束时间
- `common.startDate` - 开始日期
- `common.endDate` - 结束日期
- `common.hint` - 提示

### 2. button（按钮）
存放**所有按钮**相关的文案，统一从此分类获取按钮文本

**使用示例：**
```vue
<el-button>{{ $t('button.add') }}</el-button>
<el-button>{{ $t('button.delete') }}</el-button>
<el-button>{{ $t('button.batchDelete') }}</el-button>
<el-button>{{ $t('button.showSearch') }}</el-button>
<el-button>{{ $t('button.dataScope') }}</el-button>
```

**常用键值：**
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
- `button.showSearch` - 显示搜索
- `button.hiddenSearch` - 隐藏搜索
- `button.showHiddenField` - 显隐列
- `button.dataScope` - 数据权限
- `button.assignUser` - 分配用户
- `button.refreshCache` - 刷新缓存

### 3. field（字段）
存放**表单字段标签和表格列头**的通用文案，用于 label 和表格列标题

**使用示例：**
```vue
<!-- 表单标签 -->
<el-form-item :label="$t('field.username')" prop="username">
  <el-input />
</el-form-item>

<!-- 表格列头 -->
<el-table-column :label="$t('field.status')" prop="status" />

<!-- 搜索表单 -->
<el-form-item :label="$t('field.createTime')">
  <el-date-picker />
</el-form-item>
```

**常用键值：**
- `field.id` - 编号
- `field.username` - 用户名称
- `field.nickname` - 用户昵称
- `field.email` - 邮箱
- `field.phone` - 手机号码
- `field.sex` - 用户性别
- `field.status` - 状态
- `field.dept` - 部门
- `field.post` - 岗位
- `field.role` - 角色
- `field.password` - 密码
- `field.confirmPassword` - 确认密码
- `field.oldPassword` - 旧密码
- `field.newPassword` - 新密码
- `field.remark` - 备注
- `field.createTime` - 创建时间

### 4. placeholder（占位符）
存放**输入框、选择框等组件的占位符文案**

**使用示例：**
```vue
<!-- 输入框占位符 -->
<el-input :placeholder="$t('placeholder.username')" />

<!-- 选择框占位符 -->
<el-select :placeholder="$t('placeholder.selectDept')">
  <el-option />
</el-select>

<!-- 文本域占位符 -->
<el-input :placeholder="$t('placeholder.remark')" type="textarea" />
```

**常用键值：**
- `placeholder.username` - 请输入用户名称
- `placeholder.nickname` - 请输入用户昵称
- `placeholder.email` - 请输入邮箱
- `placeholder.phone` - 请输入手机号码
- `placeholder.password` - 请输入用户密码
- `placeholder.selectDept` - 请选择部门
- `placeholder.selectRole` - 请选择角色
- `placeholder.selectSex` - 请选择性别
- `placeholder.remark` - 请输入备注

### 5. module（模块）
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
<!-- 模块标题 -->
<h3>{{ $t('module.system.user.title') }}</h3>

<!-- 模块特有的表单字段 -->
<el-form-item :label="$t('module.system.user.username')"></el-form-item>

<!-- 模块特有的占位符（如果模块有特殊需求，优先使用 module，否则使用通用的 placeholder） -->
<el-input :placeholder="$t('module.login.username')"></el-input>
```

### 6. message（提示信息）
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

// 验证规则
rules: {
  username: [
    { required: true, message: this.$t('message.validate.required'), trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: this.$t('message.validate.email'), trigger: 'blur' }
  ]
}
```

**常用键值：**
- `message.success.add` - 新增成功
- `message.success.edit` - 修改成功
- `message.success.delete` - 删除成功
- `message.error.add` - 新增失败
- `message.warning.delete` - 此操作将永久删除该数据, 是否继续?
- `message.confirm.delete` - 确定要删除这条数据吗？
- `message.validate.required` - 不能为空
- `message.validate.email` - 邮箱格式不正确
- `message.validate.phone` - 手机号格式不正确

## 使用规范

### 1. 按钮文案
**必须使用 `button.*`**，所有按钮文本统一从此分类获取
```vue
✅ <el-button>{{ $t('button.add') }}</el-button>
❌ <el-button>新增</el-button>
```

### 2. 表单标签和表格列头
**优先使用 `field.*`**，通用的字段标签使用此分类
```vue
✅ <el-form-item :label="$t('field.username')"></el-form-item>
✅ <el-table-column :label="$t('field.status')" />
❌ <el-form-item label="用户名称"></el-form-item>
```

### 3. 输入框占位符
**必须使用 `placeholder.*`**，所有输入框、选择框的占位符统一从此分类获取
```vue
✅ <el-input :placeholder="$t('placeholder.username')" />
✅ <el-select :placeholder="$t('placeholder.selectDept')">
❌ <el-input placeholder="请输入用户名称" />
```

### 4. 模块特有文案
**使用 `module.*`**，各模块特有的文案放在对应的 `module.*` 下
```vue
✅ <h3>{{ $t('module.system.user.title') }}</h3>
❌ <h3>用户管理</h3>
```

### 5. 提示信息
**使用 `message.*`**，所有提示、警告、错误信息使用此分类
```javascript
✅ this.$message.success(this.$t('message.success.add'))
❌ this.$message.success('新增成功')
```

### 6. 通用文案
**使用 `common.*`**，通用的标签、状态、时间等使用此分类
```vue
✅ <span>{{ $t('common.status') }}</span>
✅ <span>{{ $t('common.loading') }}</span>
```

## 完整示例

```vue
<template>
  <div>
    <!-- 模块标题 -->
    <h3>{{ $t('module.system.user.title') }}</h3>
    
    <!-- 搜索表单 -->
    <el-form>
      <el-form-item :label="$t('field.username')">
        <el-input :placeholder="$t('placeholder.username')" />
      </el-form-item>
      <el-form-item :label="$t('field.createTime')">
        <el-date-picker 
          :placeholder="$t('common.startTime')"
        />
      </el-form-item>
    </el-form>
    
    <!-- 操作按钮 -->
    <el-button @click="handleAdd">{{ $t('button.add') }}</el-button>
    <el-button @click="handleExport">{{ $t('button.export') }}</el-button>
    <el-button @click="handleShowSearch">{{ $t('button.showSearch') }}</el-button>
    
    <!-- 表格 -->
    <el-table>
      <el-table-column :label="$t('field.username')" prop="username" />
      <el-table-column :label="$t('field.status')" prop="status" />
      <el-table-column :label="$t('common.operate')">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.row)">{{ $t('button.edit') }}</el-button>
          <el-button @click="handleDelete(scope.row)">{{ $t('button.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
export default {
  methods: {
    handleAdd() {
      // 提示信息
      this.$message.success(this.$t('message.success.add'))
    },
    handleDelete(row) {
      // 确认对话框
      this.$confirm(
        this.$t('message.confirm.delete'),
        this.$t('common.tips'),
        {
          confirmButtonText: this.$t('button.confirm'),
          cancelButtonText: this.$t('button.cancel')
        }
      ).then(() => {
        // 删除逻辑
        this.$message.success(this.$t('message.success.delete'))
      })
    }
  }
}
</script>
```

## 在 modal.js 中使用多语言

由于 `modal.js` 是普通 JS 文件，需要导入 i18n 实例：

```javascript
import i18n from '@/lang'

export default {
  confirm(content) {
    return MessageBox.confirm(
      content,
      i18n.t('common.tips'),
      {
        confirmButtonText: i18n.t('button.confirm'),
        cancelButtonText: i18n.t('button.cancel'),
        type: 'warning'
      }
    )
  },
  
  alert(content) {
    return MessageBox.alert(
      content,
      i18n.t('common.tips')
    )
  },
  
  loading(content) {
    return Loading.service({
      text: content || i18n.t('common.loading')
    })
  }
}
```

## 注意事项

1. **按钮统一使用 `button.*`**：所有按钮文案都应该从 `button` 分类中获取
2. **字段标签统一使用 `field.*`**：表单标签和表格列头优先使用 `field` 分类
3. **占位符统一使用 `placeholder.*`**：所有输入框、选择框的占位符统一使用 `placeholder` 分类
4. **通用文案使用 `common.*`**：通用的标签、状态、时间等使用 `common` 分类
5. **模块特有使用 `module.*`**：各模块特有的文案放在对应的 `module.*` 下
6. **提示信息使用 `message.*`**：所有提示、警告、错误信息使用 `message` 分类
7. **新增语言键值**：需要在 `zh-CN.js` 和 `en-US.js` 中同时添加
8. **保持结构一致**：确保中英文语言文件的结构完全一致

## 添加新语言

如需添加其他语言（如日语、韩语等），需要：
1. 创建对应的语言文件，如 `ja-JP.js`
2. 在 `src/lang/index.js` 中引入并注册
3. 保持与 `zh-CN.js` 和 `en-US.js` 相同的结构，包含所有6个分类：common、button、field、placeholder、module、message

## 分类使用优先级

1. **按钮文本** → `button.*`
2. **表单字段标签/表格列头** → `field.*`（优先）或 `module.*`（模块特有）
3. **输入框占位符** → `placeholder.*`（优先）或 `module.*`（模块特有）
4. **模块标题和特有文案** → `module.*`
5. **通用文案** → `common.*`
6. **提示信息** → `message.*`
