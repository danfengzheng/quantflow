<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="实体类型" prop="entityType">
        <el-select v-model="queryParams.entityType" placeholder="请选择实体类型" clearable style="width: 200px">
          <el-option label="菜单" value="menu" />
          <el-option label="字典数据" value="dict_data" />
          <el-option label="字典类型" value="dict_type" />
          <el-option label="角色" value="role" />
          <el-option label="部门" value="dept" />
        </el-select>
      </el-form-item>
      <el-form-item label="实体ID" prop="entityId">
        <el-input
          v-model="queryParams.entityId"
          placeholder="请输入实体ID"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="字段名" prop="fieldName">
        <el-input
          v-model="queryParams.fieldName"
          placeholder="请输入字段名"
          clearable
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="语言" prop="locale">
        <el-select v-model="queryParams.locale" placeholder="请选择语言" clearable style="width: 150px">
          <el-option label="简体中文" value="zh-CN" />
          <el-option label="繁体中文" value="zh-TW" />
          <el-option label="英文" value="en-US" />
          <el-option label="日文" value="ja-JP" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:i18n:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:i18n:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:i18n:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefreshCache"
          v-hasPermi="['system:i18n:remove']"
        >刷新缓存</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="i18nList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="80" />
      <el-table-column label="实体类型" align="center" prop="entityType" width="120" />
      <el-table-column label="实体ID" align="center" prop="entityId" width="100" />
      <el-table-column label="字段名" align="center" prop="fieldName" width="150" />
      <el-table-column label="语言" align="center" prop="locale" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.locale === 'zh-CN'" type="success">简体中文</el-tag>
          <el-tag v-else-if="scope.row.locale === 'zh-TW'" type="info">繁体中文</el-tag>
          <el-tag v-else-if="scope.row.locale === 'en-US'" type="warning">英文</el-tag>
          <el-tag v-else-if="scope.row.locale === 'ja-JP'" type="danger">日文</el-tag>
          <span v-else>{{ scope.row.locale }}</span>
        </template>
      </el-table-column>
      <el-table-column label="翻译内容" align="center" prop="translation" :show-overflow-tooltip="true" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}:{s}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:i18n:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:i18n:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改多语言翻译对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="实体类型" prop="entityType">
          <el-select v-model="form.entityType" placeholder="请选择实体类型" style="width: 100%">
            <el-option label="菜单" value="menu" />
            <el-option label="字典数据" value="dict_data" />
            <el-option label="字典类型" value="dict_type" />
            <el-option label="角色" value="role" />
            <el-option label="部门" value="dept" />
          </el-select>
        </el-form-item>
        <el-form-item label="实体ID" prop="entityId">
          <el-input-number v-model="form.entityId" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="字段名" prop="fieldName">
          <el-input v-model="form.fieldName" placeholder="请输入字段名" />
        </el-form-item>
        <el-form-item label="语言" prop="locale">
          <el-select v-model="form.locale" placeholder="请选择语言" style="width: 100%">
            <el-option label="简体中文" value="zh-CN" />
            <el-option label="繁体中文" value="zh-TW" />
            <el-option label="英文" value="en-US" />
            <el-option label="日文" value="ja-JP" />
          </el-select>
        </el-form-item>
        <el-form-item label="翻译内容" prop="translation">
          <el-input v-model="form.translation" type="textarea" :rows="3" placeholder="请输入翻译内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listI18n, getI18n, delI18n, addI18n, updateI18n, refreshCache } from '@/api/system/i18n'

export default {
  name: 'I18n',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 多语言翻译表格数据
      i18nList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        entityType: null,
        entityId: null,
        fieldName: null,
        locale: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        entityType: [
          { required: true, message: '实体类型不能为空', trigger: 'change' }
        ],
        entityId: [
          { required: true, message: '实体ID不能为空', trigger: 'blur' }
        ],
        fieldName: [
          { required: true, message: '字段名不能为空', trigger: 'blur' }
        ],
        locale: [
          { required: true, message: '语言不能为空', trigger: 'change' }
        ],
        translation: [
          { required: true, message: '翻译内容不能为空', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询多语言翻译列表 */
    getList() {
      this.loading = true
      listI18n(this.queryParams).then(response => {
        this.i18nList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        entityType: null,
        entityId: null,
        fieldName: null,
        locale: null,
        translation: null
      }
      this.resetForm('form')
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '添加多语言翻译'
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      getI18n(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改多语言翻译'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateI18n(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addI18n(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除多语言翻译编号为"' + ids + '"的数据项？').then(function() {
        return delI18n(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    /** 刷新缓存按钮操作 */
    handleRefreshCache() {
      this.$modal.confirm('是否确认刷新所有多语言翻译的Redis缓存？').then(function() {
        return refreshCache()
      }).then(() => {
        this.$modal.msgSuccess('缓存刷新成功')
      }).catch(() => {})
    }
  }
}
</script>

