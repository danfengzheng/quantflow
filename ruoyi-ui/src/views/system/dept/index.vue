<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('field.deptName')" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          :placeholder="$t('placeholder.deptName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('field.status')" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search')}}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset')}}</el-button>
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
          v-hasPermi="['system:dept:add']"
        >{{ $t('button.add')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >{{ $t('button.expand')}}/{{ $t('button.collapse')}}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="deptList"
      row-key="deptId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="deptName" :label="$t('field.deptName')" width="260"></el-table-column>
      <el-table-column prop="orderNum" :label="$t('field.sort')" width="200"></el-table-column>
      <el-table-column prop="status" :label="$t('field.status')" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.createTime')" align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dept:edit']"
          >{{ $t('button.edit')}}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:dept:add']"
          >{{ $t('button.add')}}</el-button>
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dept:remove']"
          >{{ $t('button.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改部门对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item :label="$t('field.parentId')" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer" :placeholder="$t('placeholder.selectParentDept')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('field.deptName')" prop="deptName">
              <el-input v-model="form.deptName" :placeholder="$t('placeholder.deptName')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('field.orderNum')" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('field.leader')" prop="leader">
              <el-input v-model="form.leader" :placeholder="$t('placeholder.enterLeader')" maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('field.phone')" prop="phone">
              <el-input v-model="form.phone" :placeholder="$t('placeholder.enterPhone')" maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('field.email')" prop="email">
              <el-input v-model="form.email" :placeholder="$t('placeholder.email')" maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('module.system.dept.status')">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 多语言设置（折叠面板） -->
        <el-row>
          <el-col :span="24">
            <el-collapse v-model="activeI18nCollapse" style="margin-bottom: 10px;">
              <el-collapse-item title="多语言设置" name="i18n">
                <el-form :model="i18nForm" label-width="120px" size="small">
                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="简体中文 (zh-CN)">
                        <el-input v-model="i18nForm.dept_name['zh-CN']" placeholder="请输入简体中文部门名称" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="繁体中文 (zh-TW)">
                        <el-input v-model="i18nForm.dept_name['zh-TW']" placeholder="請輸入繁體中文部門名稱" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row :gutter="20">
                    <el-col :span="12">
                      <el-form-item label="English (en-US)">
                        <el-input v-model="i18nForm.dept_name['en-US']" placeholder="Enter English dept name" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="12">
                      <el-form-item label="日本語 (ja-JP)">
                        <el-input v-model="i18nForm.dept_name['ja-JP']" placeholder="部門名を入力してください" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
                <div style="margin-top: 10px; text-align: right;">
                  <el-button type="primary" size="small" @click="saveI18nTranslationsInline" :disabled="!form.deptId">
                    {{ $t('button.save') || '保存多语言' }}
                  </el-button>
                  <el-button size="small" @click="loadI18nTranslations(form.deptId)" :disabled="!form.deptId">
                    {{ $t('button.refresh') || '刷新' }}
                  </el-button>
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit')}}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel')}}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDept, getDept, delDept, addDept, updateDept, listDeptExcludeChild } from "@/api/system/dept"
import { getFieldTranslations, saveFieldTranslations } from "@/api/system/i18n"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"

export default {
  name: "Dept",
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: true,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 多语言折叠面板（主表单中）
      activeI18nCollapse: [],
      i18nForm: {
        dept_name: {
          'zh-CN': '',
          'zh-TW': '',
          'en-US': '',
          'ja-JP': ''
        }
      },
      // 表单校验
      rules: {
        parentId: [
          { required: true, message: this.$t('message.validate.required'), trigger: "blur" }
        ],
        deptName: [
          { required: true, message: this.$t('message.validate.required'), trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: this.$t('message.validate.required'), trigger: "blur" }
        ],
        email: [
          {
            type: "email",
            message: this.$t('message.validate.email'),
            trigger: ["blur", "change"]
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: this.$t('message.validate.phone'),
            trigger: "blur"
          }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      this.loading = true
      listDept(this.queryParams).then(response => {
        this.deptList = this.handleTree(response.data, "deptId")
        this.loading = false
      })
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      }
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: undefined,
        parentId: undefined,
        deptName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        status: "0"
      }
      // 重置多语言表单
      this.activeI18nCollapse = []
      this.i18nForm = {
        dept_name: {
          'zh-CN': '',
          'zh-TW': '',
          'en-US': '',
          'ja-JP': ''
        }
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      if (row != undefined) {
        this.form.parentId = row.deptId
      }
      this.open = true
      this.title = this.$t('module.system.dept.title') + ' - ' + this.$t('button.add')
      listDept().then(response => {
        this.deptOptions = this.handleTree(response.data, "deptId")
      })
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      getDept(row.deptId).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('module.system.dept.title') + ' - ' + this.$t('button.edit')
        // 加载多语言翻译
        this.loadI18nTranslations(row.deptId)
        // 自动展开多语言折叠面板（如果已有翻译数据）
        this.$nextTick(() => {
          setTimeout(() => {
            const hasTranslations = Object.values(this.i18nForm.dept_name).some(v => v)
            if (hasTranslations && !this.activeI18nCollapse.includes('i18n')) {
              this.activeI18nCollapse.push('i18n')
            }
          }, 300)
        })
        listDeptExcludeChild(row.deptId).then(response => {
          this.deptOptions = this.handleTree(response.data, "deptId")
          if (this.deptOptions.length == 0) {
            const noResultsOptions = { deptId: this.form.parentId, deptName: this.form.parentName, children: [] }
            this.deptOptions.push(noResultsOptions)
          }
        })
      })
    },
    /** 加载多语言翻译 */
    loadI18nTranslations(deptId) {
      if (!deptId) return
      
      // 加载部门名称的多语言翻译
      getFieldTranslations('dept', deptId, 'dept_name').then(response => {
        if (response.data) {
          Object.assign(this.i18nForm.dept_name, response.data)
        }
      }).catch(() => {
        // 如果加载失败，使用默认值
      })
    },
    /** 保存多语言翻译（主表单中） */
    saveI18nTranslationsInline() {
      if (!this.form.deptId) {
        this.$modal.msgError('请先保存部门基本信息')
        return
      }
      
      // 保存部门名称的多语言翻译
      saveFieldTranslations('dept', this.form.deptId, 'dept_name', this.i18nForm.dept_name).then(() => {
        this.$modal.msgSuccess(this.$t('message.success.edit') || '保存成功')
        // 刷新部门列表
        this.getList()
      }).catch(() => {
        this.$modal.msgError('保存多语言翻译失败')
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.deptId != undefined) {
            updateDept(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.success.edit'))
              // 如果是编辑，自动保存多语言翻译（如果折叠面板已展开且有数据）
              if (this.form.deptId && this.activeI18nCollapse.includes('i18n')) {
                this.saveI18nTranslationsInline()
              }
              this.open = false
              this.getList()
              // 重新加载多语言翻译
              if (this.form.deptId) {
                this.loadI18nTranslations(this.form.deptId)
              }
            })
          } else {
            addDept(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.success.add'))
              // 新增后获取部门ID并保存多语言翻译
              if (response.data && response.data.deptId) {
                this.form.deptId = response.data.deptId
                // 如果折叠面板已展开，保存多语言翻译
                if (this.activeI18nCollapse.includes('i18n')) {
                  // 确保默认语言有值
                  if (!this.i18nForm.dept_name['zh-CN']) {
                    this.i18nForm.dept_name['zh-CN'] = this.form.deptName || ''
                  }
                  this.saveI18nTranslationsInline()
                } else {
                  // 否则只保存默认语言（zh-CN）的翻译
                  const defaultTranslations = {
                    'zh-CN': this.form.deptName || ''
                  }
                  saveFieldTranslations('dept', this.form.deptId, 'dept_name', defaultTranslations).catch(() => {})
                }
              }
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除名称为"' + row.deptName + '"的数据项？').then(function() {
        return delDept(row.deptId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('message.success.delete'))
      }).catch(() => {})
    }
  }
}
</script>
