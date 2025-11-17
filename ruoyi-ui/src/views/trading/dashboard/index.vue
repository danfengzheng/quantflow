<template>
  <div class="app-container">
    <!-- 数据卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #409EFF;">
              <i class="el-icon-wallet"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">{{ $t('field.totalAsset') }}</div>
              <div class="stat-value">{{ accountOverview.totalAsset || '0.00' }}</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #67C23A;">
              <i class="el-icon-money"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">{{ $t('field.positionValue') }}</div>
              <div class="stat-value">{{ accountOverview.positionValue || '0.00' }}</div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" :style="{ background: accountOverview.totalPnl >= 0 ? '#67C23A' : '#F56C6C' }">
              <i class="el-icon-data-line"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">{{ $t('field.totalPnl') }}</div>
              <div class="stat-value" :class="accountOverview.totalPnl >= 0 ? 'text-success' : 'text-danger'">
                {{ accountOverview.totalPnl || '0.00' }}
              </div>
              <div class="stat-sub" :class="accountOverview.totalPnlRatio >= 0 ? 'text-success' : 'text-danger'">
                {{ accountOverview.totalPnlRatio || '0.00' }}%
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card shadow="hover">
          <div class="stat-card">
            <div class="stat-icon" style="background: #E6A23C;">
              <i class="el-icon-s-data"></i>
            </div>
            <div class="stat-content">
              <div class="stat-label">{{ $t('field.runningStrategy') }}</div>
              <div class="stat-value">{{ todayStats.runningStrategyCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 今日统计 -->
    <el-card style="margin-bottom: 20px;">
      <div slot="header">
        <span>{{ $t('field.todayStats') }}</span>
      </div>
      <el-row :gutter="20">
        <el-col :span="6">
          <div class="today-stat">
            <div class="today-stat-label">{{ $t('field.tradeCount') }}</div>
            <div class="today-stat-value">{{ todayStats.tradeCount || 0 }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="today-stat">
            <div class="today-stat-label">{{ $t('field.signalCount') }}</div>
            <div class="today-stat-value">{{ todayStats.signalCount || 0 }}</div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="today-stat">
            <div class="today-stat-label">{{ $t('field.dailyPnl') }}</div>
            <div class="today-stat-value" :class="todayStats.dailyPnl >= 0 ? 'text-success' : 'text-danger'">
              {{ todayStats.dailyPnl || '0.00' }}
            </div>
          </div>
        </el-col>
        <el-col :span="6">
          <div class="today-stat">
            <div class="today-stat-label">{{ $t('field.pnlRatio') }}</div>
            <div class="today-stat-value" :class="todayStats.dailyPnlRatio >= 0 ? 'text-success' : 'text-danger'">
              {{ todayStats.dailyPnlRatio || '0.00' }}%
            </div>
          </div>
        </el-col>
      </el-row>
    </el-card>

    <el-row :gutter="20">
      <!-- 持仓概览 -->
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>{{ $t('field.positionOverview') }}</span>
            <el-button style="float: right; padding: 3px 10px" type="text" @click="gotoPosition">
              {{ $t('common.more') }}
            </el-button>
          </div>
          <el-table :data="positions" size="small" max-height="300">
            <el-table-column prop="symbol" :label="$t('field.symbol')" width="100" />
            <el-table-column prop="quantity" :label="$t('field.quantity')" />
            <el-table-column prop="avgPrice" :label="$t('field.avgPrice')" />
            <el-table-column prop="currentPrice" :label="$t('field.currentPrice')" />
            <el-table-column prop="unrealizedPnl" :label="$t('field.unrealizedPnl')">
              <template slot-scope="scope">
                <span :class="scope.row.unrealizedPnl >= 0 ? 'text-success' : 'text-danger'">
                  {{ scope.row.unrealizedPnl }}
                </span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 最近订单 -->
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>{{ $t('field.recentOrders') }}</span>
            <el-button style="float: right; padding: 3px 10px" type="text" @click="gotoOrder">
              {{ $t('common.more') }}
            </el-button>
          </div>
          <el-table :data="recentOrders" size="small" max-height="300">
            <el-table-column prop="symbol" :label="$t('field.symbol')" width="100" />
            <el-table-column prop="side" :label="$t('field.direction')" width="60">
              <template slot-scope="scope">
                <el-tag :type="scope.row.side === 'BUY' ? 'success' : 'danger'" size="mini">
                  {{ scope.row.side === 'BUY' ? $t('field.buy') : $t('field.sell') }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="quantity" :label="$t('field.quantity')" />
            <el-table-column prop="price" :label="$t('field.price')" />
            <el-table-column prop="status" :label="$t('field.status')" width="80">
              <template slot-scope="scope">
                <el-tag size="mini">{{ scope.row.status }}</el-tag>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <!-- 最近信号 -->
    <el-card style="margin-top: 20px;">
      <div slot="header">
        <span>{{ $t('field.recentSignals') }}</span>
        <el-button style="float: right; padding: 3px 10px" type="text" @click="gotoStrategy">
          {{ $t('common.more') }}
        </el-button>
      </div>
      <el-table :data="recentSignals" size="small">
        <el-table-column prop="symbol" :label="$t('field.symbol')" width="120" />
        <el-table-column prop="signalType" :label="$t('field.signalType')" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.signalType === 'BUY' ? 'success' : 'danger'" size="mini">
              {{ scope.row.signalType === 'BUY' ? $t('field.buySignal') : $t('field.sellSignal') }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="price" :label="$t('field.price')" width="150" />
        <el-table-column prop="reason" :label="$t('field.reason')" />
        <el-table-column prop="createTime" :label="$t('field.time')" width="180" />
      </el-table>
    </el-card>
  </div>
</template>

<script>
import { getDashboardData } from "@/api/trading/dashboard";
import websocket from "@/utils/websocket";
import { getToken } from '@/utils/auth'

export default {
  name: "Dashboard",
  data() {
    return {
      accountOverview: {},
      todayStats: {},
      strategyStats: {},
      positions: [],
      recentOrders: [],
      recentSignals: [],
      refreshTimer: null
    };
  },
  created() {
    this.loadData();
    this.startAutoRefresh();
    this.initWebSocket();
  },
  beforeDestroy() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer);
    }
    // 断开 WebSocket
    websocket.disconnect();
  },
  methods: {
    loadData() {
      getDashboardData().then(response => {
        const data = response.data;
        this.accountOverview = data.accountOverview || {};
        this.todayStats = data.todayStats || {};
        this.strategyStats = data.strategyStats || {};
        this.positions = data.positions || [];
        this.recentOrders = data.recentOrders || [];
        this.recentSignals = data.recentSignals || [];
      });
    },
    initWebSocket() {
      // 获取用户ID（从 store 或 token 中获取）
      const userId = this.$store.getters.userId || 1;

      // 连接 WebSocket
      websocket.connect(userId);

      // 注册消息处理器
      websocket.on('new_signal', (data) => {
        this.$notify({
          title: this.$t('field.newSignal'),
          message: `${data.symbol} ${data.signalType === 'BUY' ? this.$t('field.buySignal') : this.$t('field.sellSignal')}${this.$t('field.signalType')}`,
          type: 'info',
          duration: 5000
        });

        // 刷新数据
        this.loadData();
      });

      websocket.on('new_order', (data) => {
        this.$notify({
          title: this.$t('field.newOrder'),
          message: this.$t('field.orderCreated', [data.orderNo]),
          type: 'success',
          duration: 3000
        });

        this.loadData();
      });

      websocket.on('order_status_change', (data) => {
        this.$notify({
          title: this.$t('field.orderStatus'),
          message: this.$t('field.orderStatusChange', [data.orderNo, data.status]),
          type: 'info',
          duration: 3000
        });

        this.loadData();
      });

      websocket.on('position_change', () => {
        this.$notify({
          title: this.$t('field.positionChange'),
          message: this.$t('field.positionUpdated'),
          type: 'info',
          duration: 2000
        });

        this.loadData();
      });

      websocket.on('risk_alert', (message) => {
        this.$notify({
          title: this.$t('field.riskAlert'),
          message: message,
          type: 'warning',
          duration: 10000
        });
      });
    },
    startAutoRefresh() {
      // 每30秒自动刷新
      this.refreshTimer = setInterval(() => {
        this.loadData();
      }, 30000);
    },
    gotoPosition() {
      this.$router.push('/trading/position');
    },
    gotoOrder() {
      this.$router.push('/trading/order');
    },
    gotoStrategy() {
      this.$router.push('/trading/strategy');
    }
  }
};
</script>

<style scoped>
.stat-card {
  display: flex;
  align-items: center;
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 15px;
}

.stat-icon i {
  font-size: 28px;
  color: white;
}

.stat-content {
  flex: 1;
}

.stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 5px;
}

.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.stat-sub {
  font-size: 14px;
  margin-top: 5px;
}

.today-stat {
  text-align: center;
  padding: 20px 0;
}

.today-stat-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}

.today-stat-value {
  font-size: 28px;
  font-weight: bold;
  color: #303133;
}

.text-success {
  color: #67c23a;
}

.text-danger {
  color: #f56c6c;
}
</style>
