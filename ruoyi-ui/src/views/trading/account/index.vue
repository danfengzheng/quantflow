<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.accountName')" prop="accountName">
        <el-input
          v-model="queryParams.accountName"
          :placeholder="$t('placeholder.accountName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.exchange')" prop="exchange">
        <el-select v-model="queryParams.exchange" :placeholder="$t('placeholder.selectExchange')" clearable>
          <el-option
            v-for="dict in dict.type.qf_exchange_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否测试" prop="isTestnet">
        <el-select v-model="queryParams.isTestnet" placeholder="请选择是否测试" clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('field.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('placeholder.selectStatus')" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
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
          v-hasPermi="['trading:account:add']"
        >{{ $t('button.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['trading:account:edit']"
        >{{ $t('button.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['trading:account:remove']"
        >{{ $t('button.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['trading:account:export']"
        >{{ $t('button.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="accountList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="账户ID" align="center" prop="id" />
      <el-table-column label="账户名称" align="center" prop="accountName" />
      <el-table-column label="交易所" align="center" prop="exchange">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qf_exchange_type" :value="scope.row.exchange"/>
        </template>
      </el-table-column>
      <el-table-column label="是否测试" align="center" prop="isTestnet">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.isTestnet"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['trading:account:edit']"
          >{{ $t('button.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['trading:account:remove']"
          >{{ $t('button.delete') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-test"
            @click="handleTestconnect(scope.row)"
            v-hasPermi="['trading:account:test']"
          >{{ $t('button.test') }}</el-button>
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

    <!-- 添加或修改账户对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="账户名称" prop="accountName">
          <el-input v-model="form.accountName" placeholder="请输入账户名称" />
        </el-form-item>
        <el-form-item label="交易所" prop="exchange">
          <el-select v-model="form.exchange" placeholder="请选择交易所">
            <el-option
              v-for="dict in dict.type.qf_exchange_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Key" prop="apiKey">
          <el-input v-model="form.apiKey" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="Secret" prop="apiSecret">
          <el-input v-model="form.apiSecret" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="Passphrase" prop="passphrase">
          <el-input v-model="form.passphrase" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="是否测试" prop="isTestnet">
          <el-input v-model="form.isTestnet" type="textarea" placeholder="请输入内容" />
          <el-radio-group v-model="form.isTestnet">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAccount, getAccount, delAccount, addAccount, updateAccount, testConnection } from "@/api/trading/account"

export default {
  name: "Account",
  dicts: ['sys_yes_no', 'qf_exchange_type', 'sys_normal_disable'],
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
      // 账户表格数据
      accountList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        accountName: null,
        exchange: null,
        isTestnet: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        accountName: [
          { required: true, message: "账户名称不能为空", trigger: "blur" }
        ],
        exchange: [
          { required: true, message: "交易所不能为空", trigger: "change" }
        ],
        apiKey: [
          { required: true, message: "API Key不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询账户列表 */
    getList() {
      this.loading = true
      listAccount(this.queryParams).then(response => {
        this.accountList = response.rows
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
        userId: null,
        accountName: null,
        exchange: null,
        apiKey: null,
        apiSecret: null,
        passphrase: null,
        isTestnet: null,
        status: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加账户"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getAccount(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改账户"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAccount(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addAccount(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
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
      this.$modal.confirm('是否确认删除账户编号为"' + ids + '"的数据项？').then(function() {
        return delAccount(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 删除按钮操作 */
    handleTestconnect(row) {
      const ids = row.id || this.ids
      testConnection(ids).then(response => {
        this.$modal.msgSuccess("测试连接成功")
      }).catch(() => {
        this.$modal.msgError("测试连接失败")
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('trading/account/export', {
        ...this.queryParams
      }, `account_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
