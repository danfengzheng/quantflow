<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item label="策略名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入策略名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="策略类型" prop="type">
        <el-select v-model="queryParams.type" placeholder="请选择策略类型" clearable>
          <el-option
            v-for="dict in dict.type.qf_strategy_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable>
          <el-option label="停用" :value="0" />
          <el-option label="启用" :value="1" />
          <el-option label="运行中" :value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作按钮 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="strategyList">
      <el-table-column label="策略ID" align="center" prop="id" width="80" />
      <el-table-column label="策略名称" align="center" prop="name" />
      <el-table-column label="策略代码" align="center" prop="code" />
      <el-table-column label="策略类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qf_strategy_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="交易对" align="center" prop="symbol" />
      <el-table-column label="K线周期" align="center" prop="interval">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qf_interval" :value="scope.row.interval"/>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 0" type="info">停用</el-tag>
          <el-tag v-else-if="scope.row.status === 1" type="success">启用</el-tag>
          <el-tag v-else-if="scope.row.status === 2" type="warning">运行中</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="250" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status !== 2"
            size="mini"
            type="success"
            icon="el-icon-video-play"
            @click="handleStart(scope.row)"
          >启动</el-button>
          <el-button
            v-if="scope.row.status === 2"
            size="mini"
            type="warning"
            icon="el-icon-video-pause"
            @click="handleStop(scope.row)"
          >停止</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改策略对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="策略名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入策略名称" />
        </el-form-item>
        <el-form-item label="策略代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入策略代码" />
        </el-form-item>
        <el-form-item label="策略类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择策略类型">
            <el-option
              v-for="dict in dict.type.qf_strategy_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="策略描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="交易账户ID" prop="accountId">
          <el-input v-model="form.accountId" placeholder="请输入交易账户ID" />
        </el-form-item>
        <el-form-item label="交易对" prop="symbol">
          <el-input v-model="form.symbol" placeholder="请输入交易对" />
        </el-form-item>
        <el-form-item label="K线周期" prop="interval">
          <el-select v-model="form.interval" placeholder="请选择Kline周期">
            <el-option
              v-for="dict in dict.type.qf_interval"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="策略参数" prop="params">
          <el-input v-model="form.params" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.strategy_status"
              :key="dict.value"
              :label="parseInt(dict.value)"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="创建用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入创建用户ID" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
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
import { listStrategy, getStrategy, delStrategy, addStrategy, updateStrategy } from "@/api/trading/strategy"
import { startStrategy, stopStrategy, executeStrategy } from "@/api/trading/strategy";

export default {
  name: "Strategy",
  dicts: ['strategy_status','qf_strategy_type', 'qf_interval'],
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
      // 策略表格数据
      strategyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        code: null,
        type: null,
        accountId: null,
        symbol: null,
        interval: null,
        status: null,
        userId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "策略名称不能为空", trigger: "blur" }
        ],
        accountId: [
          { required: true, message: "交易账户ID不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询策略列表 */
    getList() {
      this.loading = true;
      listStrategy(this.queryParams).then(response => {
        this.strategyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 启动策略 */
    handleStart(row) {
      this.$modal.confirm('确认启动策略"' + row.name + '"？').then(() => {
        return startStrategy(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("启动成功");
      });
    },

    /** 停止策略 */
    handleStop(row) {
      this.$modal.confirm('确认停止策略"' + row.name + '"？').then(() => {
        return stopStrategy(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("停止成功");
      });
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
        name: null,
        code: null,
        type: null,
        description: null,
        accountId: null,
        symbol: null,
        interval: null,
        params: null,
        status: null,
        userId: null,
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
      this.title = "添加策略"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStrategy(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改策略"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStrategy(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStrategy(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除策略编号为"' + ids + '"的数据项？').then(function() {
        return delStrategy(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('trading/strategy/export', {
        ...this.queryParams
      }, `strategy_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
