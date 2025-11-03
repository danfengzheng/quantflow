<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>持仓管理</span>
        <el-button style="float: right; padding: 3px 10px" type="primary" size="small" @click="getList">
          刷新
        </el-button>
      </div>

      <!-- 查询条件 -->
      <el-form :inline="true" :model="queryParams" size="small">
        <el-form-item label="交易对">
          <el-input v-model="queryParams.symbol" placeholder="请输入交易对" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 持仓列表 -->
      <el-table v-loading="loading" :data="positionList" border>
        <el-table-column label="交易对" align="center" prop="symbol" />
        <el-table-column label="持仓数量" align="center" prop="quantity" />
        <el-table-column label="可用数量" align="center" prop="availableQty" />
        <el-table-column label="冻结数量" align="center" prop="frozenQty" />
        <el-table-column label="平均成本价" align="center" prop="avgPrice" />
        <el-table-column label="当前价格" align="center" prop="currentPrice" />
        <el-table-column label="市值" align="center" prop="marketValue" />
        <el-table-column label="浮动盈亏" align="center" prop="unrealizedPnl">
          <template slot-scope="scope">
            <span :class="scope.row.unrealizedPnl >= 0 ? 'text-success' : 'text-danger'">
              {{ scope.row.unrealizedPnl }}
            </span>
          </template>
        </el-table-column>
        <el-table-column label="盈亏率" align="center" prop="unrealizedPnlRatio">
          <template slot-scope="scope">
            <span :class="scope.row.unrealizedPnlRatio >= 0 ? 'text-success' : 'text-danger'">
              {{ scope.row.unrealizedPnlRatio }}%
            </span>
          </template>
        </el-table-column>
        <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { listPosition } from "@/api/trading/position";

export default {
  name: "Position",
  data() {
    return {
      loading: false,
      positionList: [],
      queryParams: {
        symbol: null
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listPosition(this.queryParams).then(response => {
        this.positionList = response.rows;
        this.loading = false;
      });
    },
    handleQuery() {
      this.getList();
    },
    resetQuery() {
      this.queryParams = {
        symbol: null
      };
      this.getList();
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
