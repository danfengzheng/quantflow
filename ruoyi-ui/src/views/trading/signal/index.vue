<template>
  <div class="app-container">
    <!-- 顶部工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-refresh"
          size="mini"
          @click="handleAnalyze"
          v-hasPermi="['system:signal:analyze']"
        >实时分析</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-s-data"
          size="mini"
          @click="handleBatchAnalyze"
          v-hasPermi="['system:signal:analyze']"
        >批量分析</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-star-off"
          size="mini"
          @click="handleRecommend"
          v-hasPermi="['system:signal:analyze']"
        >推荐交易</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:signal:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-setting"
          size="mini"
          @click="handleDictManage"
          v-hasPermi="['system:dict:list']"
        >交易对配置</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 搜索栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="交易对" prop="symbol">
        <el-input
          v-model="queryParams.symbol"
          placeholder="请输入交易对"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="K线周期" prop="interval">
        <el-select v-model="queryParams.interval" placeholder="请选择K线周期" clearable>
          <el-option label="1分钟" value="1m" />
          <el-option label="5分钟" value="5m" />
          <el-option label="15分钟" value="15m" />
          <el-option label="1小时" value="1h" />
          <el-option label="4小时" value="4h" />
          <el-option label="1天" value="1d" />
        </el-select>
      </el-form-item>
      <el-form-item label="推荐操作" prop="recommendAction">
        <el-select v-model="queryParams.recommendAction" placeholder="请选择推荐操作" clearable>
          <el-option label="买入" value="BUY" />
          <el-option label="持有" value="HOLD" />
          <el-option label="卖出" value="SELL" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="signalList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="交易对" align="center" prop="symbol" width="120">
        <template slot-scope="scope">
          <el-tag type="info">{{ scope.row.symbol }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="当前价格" align="center" prop="currentPrice" width="120" />
      <el-table-column label="综合评分" align="center" prop="signalScore" width="100">
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.signalScore"
                       :color="getScoreColor(scope.row.signalScore)"
                       :text-inside="true"
                       :stroke-width="22"></el-progress>
        </template>
      </el-table-column>
      <el-table-column label="推荐操作" align="center" prop="recommendAction" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.recommendAction === 'BUY'" type="success">买入</el-tag>
          <el-tag v-else-if="scope.row.recommendAction === 'SELL'" type="danger">卖出</el-tag>
          <el-tag v-else type="info">持有</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="趋势" align="center" prop="trendDirection" width="80">
        <template slot-scope="scope">
          <i v-if="scope.row.trendDirection === 'UP'" class="el-icon-top" style="color: #67C23A; font-size: 20px;"></i>
          <i v-else-if="scope.row.trendDirection === 'DOWN'" class="el-icon-bottom" style="color: #F56C6C; font-size: 20px;"></i>
          <i v-else class="el-icon-minus" style="color: #909399; font-size: 20px;"></i>
        </template>
      </el-table-column>
      <el-table-column label="RSI" align="center" prop="rsiValue" width="80">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.isOverbought === 'Y'" type="danger">{{ scope.row.rsiValue }}</el-tag>
          <el-tag v-else-if="scope.row.isOversold === 'Y'" type="success">{{ scope.row.rsiValue }}</el-tag>
          <el-tag v-else type="info">{{ scope.row.rsiValue }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="MACD信号" align="center" prop="macdSignal" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.macdSignal === '金叉'" type="success">金叉</el-tag>
          <el-tag v-else-if="scope.row.macdSignal === '死叉'" type="danger">死叉</el-tag>
          <el-tag v-else type="info">{{ scope.row.macdSignal }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="风险等级" align="center" prop="riskLevel" width="100">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.riskLevel === 'HIGH'" type="danger">高</el-tag>
          <el-tag v-else-if="scope.row.riskLevel === 'MEDIUM'" type="warning">中</el-tag>
          <el-tag v-else type="success">低</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="推荐原因" align="center" prop="recommendReason" :show-overflow-tooltip="true" min-width="200" />
      <el-table-column label="分析时间" align="center" prop="analysisTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.analysisTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="120">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleDetail(scope.row)"
            v-hasPermi="['system:signal:query']"
          >详情</el-button>
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

    <!-- 单个分析对话框 -->
    <el-dialog title="实时市场分析" :visible.sync="analyzeOpen" width="600px" append-to-body>
      <el-form ref="analyzeForm" :model="analyzeForm" :rules="analyzeRules" label-width="100px">
        <el-form-item label="交易对" prop="symbol">
          <el-input v-model="analyzeForm.symbol" placeholder="请输入交易对，如BTCUSDT">
            <el-select v-model="analyzeForm.symbol" slot="prepend" placeholder="快速选择" style="width: 150px;" filterable>
              <el-option
                v-for="item in availableSymbols"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-input>
        </el-form-item>
        <el-form-item label="K线周期" prop="interval">
          <el-select v-model="analyzeForm.interval" placeholder="请选择K线周期">
            <el-option label="1分钟" value="1m" />
            <el-option label="5分钟" value="5m" />
            <el-option label="15分钟" value="15m" />
            <el-option label="1小时" value="1h" />
            <el-option label="4小时" value="4h" />
            <el-option label="1天" value="1d" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAnalyze" :loading="analyzeLoading">开始分析</el-button>
        <el-button @click="analyzeOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 批量分析对话框 -->
    <el-dialog title="批量分析多个交易对" :visible.sync="batchAnalyzeOpen" width="800px" append-to-body>
      <el-form ref="batchAnalyzeForm" :model="batchAnalyzeForm" :rules="batchAnalyzeRules" label-width="100px">
        <el-form-item label="K线周期" prop="interval">
          <el-select v-model="batchAnalyzeForm.interval" placeholder="请选择K线周期">
            <el-option label="1分钟" value="1m" />
            <el-option label="5分钟" value="5m" />
            <el-option label="15分钟" value="15m" />
            <el-option label="1小时" value="1h" />
            <el-option label="4小时" value="4h" />
            <el-option label="1天" value="1d" />
          </el-select>
        </el-form-item>

        <el-form-item label="选择交易对" prop="symbols">
          <el-checkbox-group v-model="batchAnalyzeForm.symbols">
            <el-row :gutter="20">
              <el-col :span="6" v-for="symbol in availableSymbols" :key="symbol.value">
                <el-checkbox :label="symbol.value" style="margin-bottom: 10px;">
                  <el-tag
                    :type="symbol.cssClass || 'info'"
                    size="small"
                    effect="plain"
                    style="margin-right: 5px;">
                    {{ symbol.label }}
                  </el-tag>
                </el-checkbox>
              </el-col>
            </el-row>
          </el-checkbox-group>
        </el-form-item>

        <el-form-item label="快捷选择">
          <el-button size="small" type="primary" @click="selectByGroup('mainstream')">
            主流币 ({{ getGroupCount('mainstream') }})
          </el-button>
          <el-button size="small" type="success" @click="selectByGroup('top10')">
            Top10 ({{ getGroupCount('top10') }})
          </el-button>
          <el-button size="small" type="info" @click="selectAllCoins">
            全选 ({{ availableSymbols.length }})
          </el-button>
          <el-button size="small" @click="clearSelection">清空</el-button>
        </el-form-item>

        <el-form-item label="自定义" prop="customSymbols">
          <el-input
            v-model="batchAnalyzeForm.customSymbols"
            type="textarea"
            :rows="3"
            placeholder="输入其他交易对，用逗号或换行分隔，如：PEPEUSDT,SHIBUSDT"
          />
          <span style="color: #999; font-size: 12px;">
            提示：输入后点击"添加自定义"按钮 |
            <el-link type="primary" :underline="false" @click="handleDictManage">去字典配置</el-link>
          </span>
        </el-form-item>

        <el-form-item>
          <el-button size="small" @click="addCustomSymbols">添加自定义交易对</el-button>
        </el-form-item>

        <el-alert
          title="提示信息"
          type="info"
          :closable="false"
          style="margin-bottom: 20px;">
          已选择 <strong style="color: #409EFF;">{{ batchAnalyzeForm.symbols.length }}</strong> 个交易对
          <span v-if="availableSymbols.length === 0" style="color: #F56C6C; margin-left: 10px;">
            (未配置交易对，请先在字典中配置)
          </span>
        </el-alert>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitBatchAnalyze" :loading="batchAnalyzeLoading">
          开始批量分析
        </el-button>
        <el-button @click="batchAnalyzeOpen = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 详情对话框 -->
    <el-dialog title="信号详情" :visible.sync="detailOpen" width="900px" append-to-body>
      <el-descriptions :column="2" border v-if="detailData">
        <el-descriptions-item label="交易对">
          <el-tag type="info" size="medium">{{ detailData.symbol }}</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="K线周期">{{ detailData.interval }}</el-descriptions-item>
        <el-descriptions-item label="当前价格">
          <span style="font-size: 18px; font-weight: bold; color: #409EFF;">{{ detailData.currentPrice }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="综合评分">
          <el-progress :percentage="detailData.signalScore" :color="getScoreColor(detailData.signalScore)"></el-progress>
        </el-descriptions-item>

        <el-descriptions-item label="推荐操作">
          <el-tag v-if="detailData.recommendAction === 'BUY'" type="success" size="large">买入</el-tag>
          <el-tag v-else-if="detailData.recommendAction === 'SELL'" type="danger" size="large">卖出</el-tag>
          <el-tag v-else type="info" size="large">持有</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="风险等级">
          <el-tag v-if="detailData.riskLevel === 'HIGH'" type="danger">高风险</el-tag>
          <el-tag v-else-if="detailData.riskLevel === 'MEDIUM'" type="warning">中等风险</el-tag>
          <el-tag v-else type="success">低风险</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="支撑位" :span="2">
          <el-tag v-if="detailData.support1" type="success">S1: {{ detailData.support1 }}</el-tag>
          <el-tag v-if="detailData.support2" type="success" style="margin-left: 10px;">S2: {{ detailData.support2 }}</el-tag>
          <el-tag v-if="detailData.support3" type="success" style="margin-left: 10px;">S3: {{ detailData.support3 }}</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="压力位" :span="2">
          <el-tag v-if="detailData.resistance1" type="danger">R1: {{ detailData.resistance1 }}</el-tag>
          <el-tag v-if="detailData.resistance2" type="danger" style="margin-left: 10px;">R2: {{ detailData.resistance2 }}</el-tag>
          <el-tag v-if="detailData.resistance3" type="danger" style="margin-left: 10px;">R3: {{ detailData.resistance3 }}</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="RSI指标">{{ detailData.rsiValue }}</el-descriptions-item>
        <el-descriptions-item label="超买/超卖">
          <el-tag v-if="detailData.isOverbought === 'Y'" type="danger">超买</el-tag>
          <el-tag v-else-if="detailData.isOversold === 'Y'" type="success">超卖</el-tag>
          <el-tag v-else type="info">正常</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="MACD信号">{{ detailData.macdSignal }}</el-descriptions-item>
        <el-descriptions-item label="布林带位置">{{ detailData.bollingerPosition }}</el-descriptions-item>

        <el-descriptions-item label="趋势方向">
          <el-tag v-if="detailData.trendDirection === 'UP'" type="success">上涨</el-tag>
          <el-tag v-else-if="detailData.trendDirection === 'DOWN'" type="danger">下跌</el-tag>
          <el-tag v-else type="info">震荡</el-tag>
        </el-descriptions-item>
        <el-descriptions-item label="趋势强度">{{ detailData.trendStrength }}%</el-descriptions-item>

        <el-descriptions-item label="成交量">{{ detailData.volumeStatus }}</el-descriptions-item>
        <el-descriptions-item label="突破状态">
          <el-tag v-if="detailData.nearBreakout === 'Y'" type="warning">
            接近{{ detailData.breakoutDirection === 'UP' ? '向上' : '向下' }}突破
          </el-tag>
          <el-tag v-else type="info">无明显突破</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="建议止损价">
          <span style="color: #F56C6C; font-weight: bold;">{{ detailData.stopLossPrice }}</span>
        </el-descriptions-item>
        <el-descriptions-item label="建议止盈价">
          <span style="color: #67C23A; font-weight: bold;">{{ detailData.takeProfitPrice }}</span>
        </el-descriptions-item>

        <el-descriptions-item label="信号可信度">
          <el-progress :percentage="detailData.signalReliability" :color="getReliabilityColor(detailData.signalReliability)"></el-progress>
        </el-descriptions-item>
        <el-descriptions-item label="假信号风险">
          <el-tag v-if="detailData.falseSignalRisk === 'HIGH'" type="danger">高</el-tag>
          <el-tag v-else-if="detailData.falseSignalRisk === 'MEDIUM'" type="warning">中</el-tag>
          <el-tag v-else type="success">低</el-tag>
        </el-descriptions-item>

        <el-descriptions-item label="推荐原因" :span="2">
          <div style="line-height: 1.8;">{{ detailData.recommendReason }}</div>
        </el-descriptions-item>

        <el-descriptions-item label="分析时间" :span="2">
          {{ parseTime(detailData.analysisTime) }}
        </el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { listSignal, getSignal, analyzeSignal, batchAnalyzeSignal, exportSignal } from "@/api/trading/signal";

export default {
  name: "Signal",
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
      // 市场信号分析表格数据
      signalList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 分析对话框
      analyzeOpen: false,
      analyzeLoading: false,
      // 批量分析对话框
      batchAnalyzeOpen: false,
      batchAnalyzeLoading: false,
      // 详情对话框
      detailOpen: false,
      detailData: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        symbol: null,
        interval: null,
        recommendAction: null,
        riskLevel: null,
        trendDirection: null,
      },
      // 分析表单
      analyzeForm: {
        symbol: 'BTCUSDT',
        interval: '1h'
      },
      // 分析表单校验
      analyzeRules: {
        symbol: [
          { required: true, message: "交易对不能为空", trigger: "blur" }
        ],
        interval: [
          { required: true, message: "K线周期不能为空", trigger: "change" }
        ]
      },
      // 批量分析表单
      batchAnalyzeForm: {
        interval: '1h',
        symbols: [],
        customSymbols: ''
      },
      // 批量分析表单校验
      batchAnalyzeRules: {
        interval: [
          { required: true, message: "K线周期不能为空", trigger: "change" }
        ],
        symbols: [
          { required: true, message: "请至少选择一个交易对", trigger: "change", type: 'array', min: 1 }
        ]
      },
      // 可选交易对列表（从字典加载）
      availableSymbols: []
    };
  },
  created() {
    this.getList();
    this.loadSymbolsFromDict();
  },
  methods: {
    /** 从字典加载交易对列表 */
    loadSymbolsFromDict() {
      this.getDicts("market_symbol").then(response => {
        this.availableSymbols = response.data.map(item => ({
          label: item.dictLabel,
          value: item.dictValue,
          sort: item.dictSort,
          cssClass: item.cssClass || 'info',
          remark: item.remark
        }));
        console.log('从字典加载交易对:', this.availableSymbols.length, '个');
      }).catch(error => {
        console.error('加载交易对字典失败:', error);
        this.$message.warning('交易对配置加载失败，请在字典管理中配置"market_symbol"');
      });
    },

    /** 根据分组选择交易对 */
    selectByGroup(groupType) {
      let count = 0;
      if (groupType === 'mainstream') {
        // 主流币：前5个
        count = 5;
      } else if (groupType === 'top10') {
        // Top10：前10个
        count = 10;
      }

      this.batchAnalyzeForm.symbols = this.availableSymbols
        .slice(0, count)
        .map(item => item.value);
    },

    /** 获取分组数量 */
    getGroupCount(groupType) {
      if (groupType === 'mainstream') return Math.min(5, this.availableSymbols.length);
      if (groupType === 'top10') return Math.min(10, this.availableSymbols.length);
      return this.availableSymbols.length;
    },

    /** 查询市场信号分析列表 */
    getList() {
      this.loading = true;
      listSignal(this.queryParams).then(response => {
        this.signalList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.signalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 实时分析按钮操作 */
    handleAnalyze() {
      this.analyzeOpen = true;
      this.analyzeForm = {
        symbol: this.availableSymbols.length > 0 ? this.availableSymbols[0].value : 'BTCUSDT',
        interval: '1h'
      };
    },
    /** 提交分析 */
    submitAnalyze() {
      this.$refs["analyzeForm"].validate(valid => {
        if (valid) {
          this.analyzeLoading = true;
          analyzeSignal(this.analyzeForm.symbol, this.analyzeForm.interval).then(response => {
            this.$modal.msgSuccess("分析完成");
            this.analyzeOpen = false;
            this.analyzeLoading = false;
            this.getList();
          }).catch(() => {
            this.analyzeLoading = false;
          });
        }
      });
    },
    /** 批量分析按钮操作 */
    handleBatchAnalyze() {
      if (this.availableSymbols.length === 0) {
        this.$message.warning('请先在字典管理中配置交易对（字典类型：market_symbol）');
        return;
      }
      this.batchAnalyzeOpen = true;
      this.batchAnalyzeForm = {
        interval: '1h',
        symbols: [],
        customSymbols: ''
      };
    },
    /** 全选 */
    selectAllCoins() {
      this.batchAnalyzeForm.symbols = this.availableSymbols.map(item => item.value);
    },
    /** 清空选择 */
    clearSelection() {
      this.batchAnalyzeForm.symbols = [];
    },
    /** 添加自定义交易对 */
    addCustomSymbols() {
      if (!this.batchAnalyzeForm.customSymbols) {
        this.$message.warning('请输入自定义交易对');
        return;
      }

      // 分割并清理输入
      const customList = this.batchAnalyzeForm.customSymbols
        .split(/[,，\n\s]+/)
        .map(s => s.trim().toUpperCase())
        .filter(s => s.length > 0);

      if (customList.length === 0) {
        this.$message.warning('请输入有效的交易对');
        return;
      }

      // 去重并添加
      const newSymbols = customList.filter(s => !this.batchAnalyzeForm.symbols.includes(s));
      this.batchAnalyzeForm.symbols.push(...newSymbols);

      // 清空输入框
      this.batchAnalyzeForm.customSymbols = '';

      this.$message.success(`成功添加 ${newSymbols.length} 个交易对`);
    },
    /** 提交批量分析 */
    submitBatchAnalyze() {
      this.$refs["batchAnalyzeForm"].validate(valid => {
        if (valid) {
          if (this.batchAnalyzeForm.symbols.length === 0) {
            this.$message.warning('请至少选择一个交易对');
            return;
          }

          this.batchAnalyzeLoading = true;
          this.$message({
            message: `正在分析 ${this.batchAnalyzeForm.symbols.length} 个交易对，请稍候...`,
            type: 'info',
            duration: 3000
          });

          const data = {
            symbols: this.batchAnalyzeForm.symbols,
            interval: this.batchAnalyzeForm.interval
          };

          batchAnalyzeSignal(data).then(response => {
            this.$modal.msgSuccess(`批量分析完成，共分析 ${response.data.length} 个交易对`);
            this.batchAnalyzeOpen = false;
            this.batchAnalyzeLoading = false;

            // 将结果显示在列表中
            this.signalList = response.data;
            this.total = response.data.length;
          }).catch(() => {
            this.batchAnalyzeLoading = false;
          });
        }
      });
    },
    /** 推荐交易按钮操作 */
    handleRecommend() {
      this.loading = true;
      this.$message({
        message: '正在分析主流币种，请稍候...',
        type: 'info',
        duration: 2000
      });

      // 调用推荐接口
      this.$http.get('/system/signal/recommend').then(response => {
        if (response.data.code === 200) {
          this.signalList = response.data.data;
          this.total = this.signalList.length;
          this.$modal.msgSuccess(`发现 ${this.total} 个推荐交易机会`);
        }
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    /** 打开字典管理 */
    handleDictManage() {
      // 跳转到字典管理页面
      this.$router.push({
        path: '/system/dict',
        query: { dictType: 'market_symbol' }
      });
    },
    /** 详情按钮操作 */
    handleDetail(row) {
      this.detailData = row;
      this.detailOpen = true;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/signal/export', {
        ...this.queryParams
      }, `signal_${new Date().getTime()}.xlsx`)
    },
    /** 获取评分颜色 */
    getScoreColor(score) {
      if (score >= 70) {
        return '#67C23A'; // 绿色 - 买入
      } else if (score >= 55) {
        return '#409EFF'; // 蓝色 - 持有偏多
      } else if (score >= 45) {
        return '#909399'; // 灰色 - 持有
      } else if (score >= 30) {
        return '#E6A23C'; // 橙色 - 持有偏空
      } else {
        return '#F56C6C'; // 红色 - 卖出
      }
    },
    /** 获取可信度颜色 */
    getReliabilityColor(reliability) {
      if (reliability >= 85) {
        return '#67C23A'; // 绿色 - 高可信度
      } else if (reliability >= 70) {
        return '#E6A23C'; // 橙色 - 中等可信度
      } else {
        return '#F56C6C'; // 红色 - 低可信度
      }
    }
  }
};
</script>

<style scoped>
.el-tag + .el-tag {
  margin-left: 10px;
}
</style>
