<template>
  <div class="app-container">
    <el-card v-loading="loading">
      <div slot="header">
        <span>回测结果</span>
        <el-button style="float: right; padding: 3px 10px" type="text" @click="goBack">
          返回列表
        </el-button>
      </div>

      <!-- 基本信息 -->
      <el-descriptions :column="3" border style="margin-bottom: 20px;">
        <el-descriptions-item label="策略名称">{{ basicInfo.strategyName }}</el-descriptions-item>
        <el-descriptions-item label="交易对">{{ basicInfo.symbol }}</el-descriptions-item>
        <el-descriptions-item label="K线周期">{{ basicInfo.interval }}</el-descriptions-item>
        <el-descriptions-item label="开始日期">{{ basicInfo.startDate }}</el-descriptions-item>
        <el-descriptions-item label="结束日期">{{ basicInfo.endDate }}</el-descriptions-item>
        <el-descriptions-item label="交易天数">{{ basicInfo.tradingDays }}</el-descriptions-item>
        <el-descriptions-item label="初始资金">{{ basicInfo.initialCapital }}</el-descriptions-item>
        <el-descriptions-item label="最终资金">{{ returnMetrics.finalCapital }}</el-descriptions-item>
        <el-descriptions-item label="累计收益">
          <span :class="returnMetrics.cumulativeProfit >= 0 ? 'text-success' : 'text-danger'">
            {{ returnMetrics.cumulativeProfit }}
          </span>
        </el-descriptions-item>
      </el-descriptions>

      <!-- 核心指标 -->
      <el-row :gutter="20" style="margin-bottom: 20px;">
        <el-col :span="6">
          <div class="metric-box">
            <div class="metric-label">总收益率</div>
            <div class="metric-value" :class="returnMetrics.totalReturn >= 0 ? 'text-success' : 'text-danger'">
              {{ returnMetrics.totalReturn }}%
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="metric-box">
            <div class="metric-label">年化收益率</div>
            <div class="metric-value" :class="returnMetrics.annualReturn >= 0 ? 'text-success' : 'text-danger'">
              {{ returnMetrics.annualReturn }}%
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="metric-box">
            <div class="metric-label">最大回撤</div>
            <div class="metric-value text-danger">
              {{ riskMetrics.maxDrawdown }}%
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="metric-box">
            <div class="metric-label">夏普比率</div>
            <div class="metric-value">
              {{ riskMetrics.sharpeRatio }}
            </div>
          </div>
        </el-col>
      </el-row>

      <!-- 交易统计 -->
      <el-card style="margin-bottom: 20px;">
        <div slot="header">交易统计</div>
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="stat-item">
              <div class="stat-label">总交易次数</div>
              <div class="stat-value">{{ tradeStats.totalTrades }}</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-item">
              <div class="stat-label">胜率</div>
              <div class="stat-value" :class="getWinRateClass()">{{ tradeStats.winRate }}%</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-item">
              <div class="stat-label">盈亏比</div>
              <div class="stat-value">{{ tradeStats.profitLossRatio }}</div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="stat-item">
              <div class="stat-label">平均盈利</div>
              <div class="stat-value text-success">{{ tradeStats.avgProfit }}</div>
            </div>
          </el-col>
        </el-row>
      </el-card>

      <!-- 资金曲线图 -->
      <el-card style="margin-bottom: 20px;">
        <div slot="header">资金曲线</div>
        <div id="equity-chart" style="width: 100%; height: 400px;"></div>
      </el-card>

      <!-- 交易记录 -->
      <el-card>
        <div slot="header">交易记录</div>
        <el-table :data="tradeRecords" size="small">
          <el-table-column prop="entryDate" label="买入日期" width="120" />
          <el-table-column prop="exitDate" label="卖出日期" width="120" />
          <el-table-column prop="entryPrice" label="买入价" />
          <el-table-column prop="exitPrice" label="卖出价" />
          <el-table-column prop="quantity" label="数量" />
          <el-table-column prop="profit" label="盈亏">
            <template slot-scope="scope">
              <span :class="scope.row.profit >= 0 ? 'text-success' : 'text-danger'">
                {{ scope.row.profit }}
              </span>
            </template>
          </el-table-column>
          <el-table-column prop="profitRatio" label="盈亏率">
            <template slot-scope="scope">
              <span :class="scope.row.profitRatio >= 0 ? 'text-success' : 'text-danger'">
                {{ scope.row.profitRatio }}%
              </span>
            </template>
          </el-table-column>
        </el-table>
      </el-card>
    </el-card>
  </div>
</template>

<script>
import { getBacktestResult } from "@/api/trading/backtest";
import * as echarts from 'echarts';

export default {
  name: "BacktestResult",
  data() {
    return {
      loading: false,
      basicInfo: {},
      returnMetrics: {},
      riskMetrics: {},
      tradeStats: {},
      equityCurve: [],
      tradeRecords: [],
      chart: null
    };
  },
  created() {
    const backtestId = this.$route.query.id;
    if (backtestId) {
      this.loadResult(backtestId);
    }
  },
  beforeDestroy() {
    if (this.chart) {
      this.chart.dispose();
    }
  },
  methods: {
    loadResult(id) {
      this.loading = true;
      getBacktestResult(id).then(response => {
        const result = response.data;
        this.basicInfo = result.basicInfo || {};
        this.returnMetrics = result.returnMetrics || {};
        this.riskMetrics = result.riskMetrics || {};
        this.tradeStats = result.tradeStats || {};
        this.equityCurve = result.equityCurve || [];
        this.tradeRecords = result.tradeRecords || [];

        this.loading = false;

        this.$nextTick(() => {
          this.renderChart();
        });
      });
    },
    renderChart() {
      if (!this.chart) {
        this.chart = echarts.init(document.getElementById('equity-chart'));
      }

      const dates = this.equityCurve.map(item => item.date);
      const equity = this.equityCurve.map(item => item.equity);

      const option = {
        title: {
          text: '资金曲线',
          left: 'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        xAxis: {
          type: 'category',
          data: dates,
          boundaryGap: false
        },
        yAxis: {
          type: 'value',
          name: '资金'
        },
        series: [
          {
            name: '资金',
            type: 'line',
            data: equity,
            smooth: true,
            areaStyle: {
              color: {
                type: 'linear',
                x: 0,
                y: 0,
                x2: 0,
                y2: 1,
                colorStops: [
                  { offset: 0, color: 'rgba(24, 144, 255, 0.3)' },
                  { offset: 1, color: 'rgba(24, 144, 255, 0.05)' }
                ]
              }
            },
            lineStyle: {
              color: '#1890ff',
              width: 2
            }
          }
        ]
      };

      this.chart.setOption(option);
    },
    getWinRateClass() {
      const winRate = parseFloat(this.tradeStats.winRate || 0);
      if (winRate >= 60) return 'text-success';
      if (winRate >= 40) return 'text-warning';
      return 'text-danger';
    },
    goBack() {
      this.$router.push('/trading/backtest');
    }
  }
};
</script>

<style scoped>
.metric-box {
  text-align: center;
  padding: 20px;
  background: #f5f7fa;
  border-radius: 4px;
}

.metric-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}

.metric-value {
  font-size: 28px;
  font-weight: bold;
}

.stat-item {
  text-align: center;
  padding: 15px 0;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 8px;
}

.stat-value {
  font-size: 20px;
  font-weight: bold;
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
