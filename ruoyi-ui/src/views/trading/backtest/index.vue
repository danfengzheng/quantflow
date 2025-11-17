<template>
  <div class="app-container">
    <!-- 查询表单 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
      <el-form-item :label="$t('field.strategyName')" prop="strategyId">
        <el-select v-model="queryParams.strategyId" placeholder="请选择策略" clearable>
          <el-option
            v-for="strategy in strategyList"
            :key="strategy.id"
            :label="strategy.name"
            :value="strategy.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('field.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('placeholder.selectStatus')" clearable>
          <el-option label="待执行" value="PENDING" />
          <el-option :label="$t('field.running')" value="RUNNING" />
          <el-option label="已完成" value="COMPLETED" />
          <el-option label="失败" value="FAILED" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset') }}</el-button>
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
        >新建回测</el-button>
      </el-col>
    </el-row>
    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="backtestList">
      <el-table-column :label="$t('field.id')" align="center" prop="id" width="80" />
      <el-table-column label="任务名称" align="center" prop="name" min-width="150" show-overflow-tooltip />
      <el-table-column :label="$t('field.symbol')" align="center" prop="symbol" width="100" />
      <el-table-column label="周期" align="center" prop="interval" width="80" />
      <el-table-column label="回测期间" align="center" width="200">
        <template slot-scope="scope">
      <span v-if="scope.row.startDate && scope.row.endDate">
        {{ parseTime(scope.row.startDate, '{y}-{m}-{d}') }} ~ {{ parseTime(scope.row.endDate, '{y}-{m}-{d}') }}
      </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="初始资金" align="center" prop="initialCapital" width="100" />
      <el-table-column :label="$t('field.totalReturn')" align="center" prop="totalReturn" width="100">
        <template slot-scope="scope">
      <span v-if="scope.row.totalReturn" :class="scope.row.totalReturn >= 0 ? 'text-success' : 'text-danger'">
        {{ scope.row.totalReturn }}%
      </span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.winRate')" align="center" prop="winRate" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.winRate">{{ scope.row.winRate }}%</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.tradeCount')" align="center" prop="tradeCount" width="90">
        <template slot-scope="scope">
          <span>{{ scope.row.tradeCount || '-' }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.status')" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.status === 'PENDING'" type="info" size="small">待执行</el-tag>
          <el-tag v-else-if="scope.row.status === 'RUNNING'" type="warning" size="small">
            <i class="el-icon-loading"></i> {{ $t('field.running') }}
          </el-tag>
          <el-tag v-else-if="scope.row.status === 'COMPLETED'" type="success" size="small">已完成</el-tag>
          <el-tag v-else-if="scope.row.status === 'FAILED'" type="danger" size="small">失败</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.createTime')" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.operate')" align="center" width="220" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.status === 'PENDING'"
            size="mini"
            type="success"
            icon="el-icon-video-play"
            @click="handleExecute(scope.row)"
          >执行</el-button>
          <el-button
            v-if="scope.row.status === 'COMPLETED'"
            size="mini"
            type="primary"
            icon="el-icon-view"
            @click="handleViewResult(scope.row)"
          >{{ $t('field.viewResult') }}</el-button>
          <el-button
            v-if="scope.row.status === 'FAILED'"
            size="mini"
            type="warning"
            icon="el-icon-warning"
            @click="showErrorMsg(scope.row)"
          >错误</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            style="color: #F56C6C;"
          >{{ $t('button.delete') }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加回测对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="form.name" :placeholder="$t('placeholder.name')" />
        </el-form-item>

        <el-form-item label="策略" prop="strategyId">
          <el-select
            v-model="form.strategyId"
            placeholder="请选择策略"
            style="width: 100%"
            @change="handleStrategyChange"
          >
            <el-option
              v-for="strategy in strategyList"
              :key="strategy.id"
              :label="strategy.name"
              :value="strategy.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item :label="$t('field.symbol')" prop="symbol">
          <el-input v-model="form.symbol" placeholder="从策略自动获取" :disabled="true" />
        </el-form-item>

        <el-form-item :label="$t('field.interval')" prop="interval">
          <el-input v-model="form.interval" placeholder="从策略自动获取" :disabled="true" />
        </el-form-item>

        <el-form-item label="回测时间范围" required>
          <el-col :span="11">
            <el-form-item prop="startDate">
              <el-date-picker
                v-model="form.startDate"
                type="date"
                placeholder="开始日期"
                value-format="yyyy-MM-dd"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="2" style="text-align: center">-</el-col>
          <el-col :span="11">
            <el-form-item prop="endDate">
              <el-date-picker
                v-model="form.endDate"
                type="date"
                placeholder="结束日期"
                value-format="yyyy-MM-dd"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-form-item>

        <el-form-item label="初始资金" prop="initialCapital">
          <el-input-number
            v-model="form.initialCapital"
            :min="100"
            :max="1000000"
            :step="1000"
            style="width: 100%"
          />
        </el-form-item>

        <el-form-item label="手续费率" prop="commissionRate">
          <el-input-number
            v-model="form.commissionRate"
            :min="0"
            :max="0.01"
            :step="0.0001"
            :precision="4"
            style="width: 100%"
          />
          <span style="color: #909399; font-size: 12px; margin-left: 10px;">
            (0.001 = 0.1%)
          </span>
        </el-form-item>

        <el-form-item label="滑点率" prop="slippageRate">
          <el-input-number
            v-model="form.slippageRate"
            :min="0"
            :max="0.01"
            :step="0.0001"
            :precision="4"
            style="width: 100%"
          />
          <span style="color: #909399; font-size: 12px; margin-left: 10px;">
            (可选，默认为0)
          </span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listBacktest, addBacktest, delBacktest, executeBacktest } from "@/api/trading/backtest";
import { listStrategy, getStrategy } from "@/api/trading/strategy";

export default {
  name: "Backtest",
  data() {
    return {
      loading: false,
      backtestList: [],
      strategyList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        strategyId: null,
        status: null
      },
      form: {},
      rules: {
        name: [
          { required: true, message: "任务名称不能为空", trigger: "blur" }
        ],
        strategyId: [
          { required: true, message: "请选择策略", trigger: "change" }
        ],
        startDate: [
          { required: true, message: "请选择开始日期", trigger: "change" }
        ],
        endDate: [
          { required: true, message: "请选择结束日期", trigger: "change" }
        ],
        initialCapital: [
          { required: true, message: "初始资金不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStrategyList();
  },
  methods: {
    getList() {
      this.loading = true;
      listBacktest(this.queryParams).then(response => {
        this.backtestList = response.rows;
        this.loading = false;
      });
    },
    getStrategyList() {
      listStrategy({}).then(response => {
        this.strategyList = response.rows;
      });
    },
    handleStrategyChange(strategyId) {
      // 根据选择的策略自动填充交易对和周期
      const strategy = this.strategyList.find(s => s.id === strategyId);
      if (strategy) {
        this.form.symbol = strategy.symbol;
        this.form.interval = strategy.interval;

        // 自动生成任务名称
        if (!this.form.name || this.form.name.includes('回测')) {
          const now = new Date();
          this.form.name = `${strategy.name}_回测_${now.getMonth()+1}${now.getDate()}`;
        }
      }
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新建回测";
    },
    handleExecute(row) {
      this.$modal.confirm('确认执行回测任务"' + row.name + '"？执行时间可能较长，请耐心等待。').then(() => {
        return executeBacktest(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("回测任务已启动，请稍后查看结果");
      });
    },
    handleViewResult(row) {
      this.$router.push({
        path: '/trading/backtest/result',
        query: { id: row.id }
      });
    },
    handleDelete(row) {
      this.$modal.confirm('是否确认删除回测任务"' + row.name + '"？').then(() => {
        return delBacktest(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          // 验证日期范围
          if (new Date(this.form.startDate) >= new Date(this.form.endDate)) {
            this.$modal.msgError("结束日期必须大于开始日期");
            return;
          }

          addBacktest(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        name: null,
        strategyId: null,
        symbol: null,
        interval: null,
        startDate: null,
        endDate: null,
        initialCapital: 10000,
        commissionRate: 0.001,
        slippageRate: 0
      };
      this.resetForm("form");
    },
    showErrorMsg(row) {
      this.$alert(row.errorMsg || '未知错误', '回测失败原因', {
        confirmButtonText: '确定',
        type: 'error'
      });
    }
  }
};
</script>

<style scoped>
.text-success {
  color: #67c23a;
}
.text-danger {
  color: #f56c6c;
}
</style>
