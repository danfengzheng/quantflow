<template>
  <div class="app-container">
    <el-card class="box-card" style="margin-bottom: 20px;">
      <div slot="header" class="clearfix">
        <span>行情监控</span>
        <el-button style="float: right; padding: 3px 10px" type="primary" size="small" @click="refreshMarket">
          刷新
        </el-button>
      </div>

      <!-- 账户选择 -->
      <el-form :inline="true">
        <el-form-item label="交易账户">
          <el-select v-model="queryParams.accountId" placeholder="请选择账户" @change="handleAccountChange">
            <el-option
              v-for="account in accountList"
              :key="account.id"
              :label="account.accountName"
              :value="account.id"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="交易对">
          <el-select v-model="queryParams.symbol" placeholder="请选择交易对" @change="handleSymbolChange">
            <el-option label="BTC/USDT" value="BTCUSDT" />
            <el-option label="ETH/USDT" value="ETHUSDT" />
            <el-option label="BNB/USDT" value="BNBUSDT" />
          </el-select>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 实时行情卡片 -->
    <el-row :gutter="20" style="margin-bottom: 20px;">
      <el-col :span="6">
        <el-card>
          <div class="ticker-item">
            <div class="ticker-label">最新价</div>
            <div class="ticker-value" :class="priceChangeClass">
              {{ ticker.last || '-' }}
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="ticker-item">
            <div class="ticker-label">24h涨跌幅</div>
            <div class="ticker-value" :class="priceChangeClass">
              {{ ticker.priceChangePercent ? ticker.priceChangePercent + '%' : '-' }}
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="ticker-item">
            <div class="ticker-label">24h最高</div>
            <div class="ticker-value">
              {{ ticker.high || '-' }}
            </div>
          </div>
        </el-card>
      </el-col>

      <el-col :span="6">
        <el-card>
          <div class="ticker-item">
            <div class="ticker-label">24h最低</div>
            <div class="ticker-value">
              {{ ticker.low || '-' }}
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- K线图表 -->
    <el-card>
      <div slot="header" class="clearfix">
        <span>K线图表</span>
        <el-radio-group v-model="queryParams.interval" size="small" style="float: right;" @change="loadKlines">
          <el-radio-button label="1m">1分钟</el-radio-button>
          <el-radio-button label="5m">5分钟</el-radio-button>
          <el-radio-button label="15m">15分钟</el-radio-button>
          <el-radio-button label="1h">1小时</el-radio-button>
          <el-radio-button label="4h">4小时</el-radio-button>
          <el-radio-button label="1d">1天</el-radio-button>
        </el-radio-group>
      </div>

      <div id="kline-chart" style="width: 100%; height: 500px;"></div>
    </el-card>
  </div>
</template>

<script>
import { listAccount } from "@/api/trading/account";
import { getTicker, getKlines } from "@/api/trading/market";
import * as echarts from 'echarts';

export default {
  name: "Market",
  data() {
    return {
      accountList: [],
      queryParams: {
        accountId: null,
        symbol: 'BTCUSDT',
        interval: '1h',
        limit: 100
      },
      ticker: {},
      klines: [],
      chart: null,
      refreshTimer: null
    };
  },
  computed: {
    priceChangeClass() {
      if (!this.ticker.priceChangePercent) return '';
      return this.ticker.priceChangePercent > 0 ? 'price-up' : 'price-down';
    }
  },
  created() {
    this.loadAccounts();
  },
  beforeDestroy() {
    if (this.refreshTimer) {
      clearInterval(this.refreshTimer);
    }
    if (this.chart) {
      this.chart.dispose();
    }
  },
  methods: {
    /** 加载账户列表 */
    loadAccounts() {
      listAccount({}).then(response => {
        this.accountList = response.rows;
        if (this.accountList.length > 0) {
          this.queryParams.accountId = this.accountList[0].id;
          this.loadMarketData();
        }
      });
    },

    /** 加载行情数据 */
    loadMarketData() {
      if (!this.queryParams.accountId) {
        this.$message.warning('请先选择交易账户');
        return;
      }
      this.loadTicker();
      this.loadKlines();
      this.startAutoRefresh();
    },

    /** 加载实时行情 */
    loadTicker() {
      getTicker(this.queryParams.accountId, this.queryParams.symbol).then(response => {
        this.ticker = response.data;
      });
    },

    /** 加载K线数据 */
    loadKlines() {
      getKlines(
        this.queryParams.accountId,
        this.queryParams.symbol,
        this.queryParams.interval,
        this.queryParams.limit
      ).then(response => {
        this.klines = response.data;
        this.$nextTick(() => {
          this.renderChart();
        });
      });
    },

    /** 渲染K线图 */
    renderChart() {
      if (!this.chart) {
        this.chart = echarts.init(document.getElementById('kline-chart'));
      }

      const dates = this.klines.map(item => {
        const date = new Date(item.openTime);
        return date.toLocaleString();
      });

      const data = this.klines.map(item => {
        return [item.open, item.close, item.low, item.high];
      });

      const volumes = this.klines.map(item => item.volume);

      const option = {
        title: {
          text: `${this.queryParams.symbol} - ${this.queryParams.interval}`,
          left: 'center'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          }
        },
        grid: [
          {
            left: '10%',
            right: '10%',
            height: '50%'
          },
          {
            left: '10%',
            right: '10%',
            top: '70%',
            height: '15%'
          }
        ],
        xAxis: [
          {
            type: 'category',
            data: dates,
            boundaryGap: true,
            axisLine: { onZero: false },
            splitLine: { show: false },
            min: 'dataMin',
            max: 'dataMax'
          },
          {
            type: 'category',
            gridIndex: 1,
            data: dates,
            boundaryGap: true,
            axisLine: { onZero: false },
            axisTick: { show: false },
            splitLine: { show: false },
            axisLabel: { show: false },
            min: 'dataMin',
            max: 'dataMax'
          }
        ],
        yAxis: [
          {
            scale: true,
            splitArea: {
              show: true
            }
          },
          {
            scale: true,
            gridIndex: 1,
            splitNumber: 2,
            axisLabel: { show: false },
            axisLine: { show: false },
            axisTick: { show: false },
            splitLine: { show: false }
          }
        ],
        dataZoom: [
          {
            type: 'inside',
            xAxisIndex: [0, 1],
            start: 80,
            end: 100
          },
          {
            show: true,
            xAxisIndex: [0, 1],
            type: 'slider',
            bottom: '5%',
            start: 80,
            end: 100
          }
        ],
        series: [
          {
            name: 'K线',
            type: 'candlestick',
            data: data,
            itemStyle: {
              color: '#ef232a',
              color0: '#14b143',
              borderColor: '#ef232a',
              borderColor0: '#14b143'
            }
          },
          {
            name: '成交量',
            type: 'bar',
            xAxisIndex: 1,
            yAxisIndex: 1,
            data: volumes
          }
        ]
      };

      this.chart.setOption(option);
    },

    /** 开始自动刷新 */
    startAutoRefresh() {
      if (this.refreshTimer) {
        clearInterval(this.refreshTimer);
      }
      // 每5秒刷新一次行情
      this.refreshTimer = setInterval(() => {
        this.loadTicker();
      }, 5000);
    },

    /** 手动刷新 */
    refreshMarket() {
      this.loadMarketData();
      this.$message.success('刷新成功');
    },

    /** 账户切换 */
    handleAccountChange() {
      this.loadMarketData();
    },

    /** 交易对切换 */
    handleSymbolChange() {
      this.loadMarketData();
    }
  }
};
</script>

<style scoped>
.ticker-item {
  text-align: center;
  padding: 20px 0;
}

.ticker-label {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}

.ticker-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
}

.price-up {
  color: #67c23a;
}

.price-down {
  color: #f56c6c;
}
</style>
