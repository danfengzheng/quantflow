<template>
  <div class="app-container">
    <el-card>
      <div slot="header">
        <span>{{ $t('module.trading.statistics.title') }}</span>
        <el-button style="float: right; padding: 3px 10px" type="primary" size="small" @click="loadData">
          {{ $t('button.refresh') }}
        </el-button>
      </div>

      <!-- 核心指标 -->
      <el-row :gutter="20" style="margin-bottom: 30px;">
        <el-col :span="6">
          <div class="stat-box">
            <div class="stat-title">{{ $t('field.totalTrades') }}</div>
            <div class="stat-value">{{ statistics.totalTrades || 0 }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-box">
            <div class="stat-title">{{ $t('field.winRate') }}</div>
            <div class="stat-value" :class="getWinRateClass()">
              {{ statistics.winRate || '0.00' }}%
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-box">
            <div class="stat-title">{{ $t('field.netProfit') }}</div>
            <div class="stat-value" :class="getPnlClass()">
              {{ statistics.netProfit || '0.00' }}
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="stat-box">
            <div class="stat-title">{{ $t('field.profitLossRatio') }}</div>
            <div class="stat-value">
              {{ statistics.profitLossRatio || '0.00' }}
            </div>
          </div>
        </el-col>
      </el-row>

      <!-- 详细统计 -->
      <el-row :gutter="20">
        <el-col :span="12">
          <el-card shadow="hover">
            <div slot="header">{{ $t('field.profitStats') }}</div>
            <el-descriptions :column="2" border>
              <el-descriptions-item :label="$t('field.profitTrades')">
                {{ statistics.profitTrades || 0 }}
              </el-descriptions-item>
              <el-descriptions-item :label="$t('field.totalProfit')">
                <span class="text-success">{{ statistics.totalProfit || '0.00' }}</span>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('field.avgProfit')">
                <span class="text-success">{{ statistics.avgProfit || '0.00' }}</span>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('field.maxProfit')">
                <span class="text-success">{{ statistics.maxProfit || '0.00' }}</span>
              </el-descriptions-item>
            </el-descriptions>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card shadow="hover">
            <div slot="header">{{ $t('field.lossStats') }}</div>
            <el-descriptions :column="2" border>
              <el-descriptions-item :label="$t('field.lossTrades')">
                {{ statistics.lossTrades || 0 }}
              </el-descriptions-item>
              <el-descriptions-item :label="$t('field.totalLoss')">
                <span class="text-danger">{{ statistics.totalLoss || '0.00' }}</span>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('field.avgLoss')">
                <span class="text-danger">{{ statistics.avgLoss || '0.00' }}</span>
              </el-descriptions-item>
              <el-descriptions-item :label="$t('field.maxLoss')">
                <span class="text-danger">{{ statistics.maxLoss || '0.00' }}</span>
              </el-descriptions-item>
            </el-descriptions>
          </el-card>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { getTradingStatistics } from "@/api/trading/statistics";

export default {
  name: "Statistics",
  data() {
    return {
      statistics: {}
    };
  },
  created() {
    this.loadData();
  },
  methods: {
    loadData() {
      getTradingStatistics().then(response => {
        this.statistics = response.data;
      });
    },
    getWinRateClass() {
      const winRate = parseFloat(this.statistics.winRate || 0);
      if (winRate >= 60) return 'text-success';
      if (winRate >= 40) return 'text-warning';
      return 'text-danger';
    },
    getPnlClass() {
      const netProfit = parseFloat(this.statistics.netProfit || 0);
      return netProfit >= 0 ? 'text-success' : 'text-danger';
    }
  }
};
</script>

<style scoped>
.stat-box {
  text-align: center;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
}

.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}

.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
}

.text-success {
  color: #67c23a;
}

.text-danger {
  color: #f56c6c;
}

.text-warning {
  color: #e6a23c;
}
</style>
