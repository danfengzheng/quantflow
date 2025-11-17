<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item :label="$t('field.orderNo')" prop="orderNo">
        <el-input
          v-model="queryParams.orderNo"
          :placeholder="$t('placeholder.orderNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="策略ID" prop="strategyId">
        <el-input
          v-model="queryParams.strategyId"
          placeholder="请输入策略ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.accountId')" prop="accountId">
        <el-input
          v-model="queryParams.accountId"
          :placeholder="$t('placeholder.accountId')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.symbol')" prop="symbol">
        <el-input
          v-model="queryParams.symbol"
          :placeholder="$t('placeholder.symbol')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.direction')" prop="side">
        <el-select v-model="queryParams.side" :placeholder="$t('placeholder.selectDirection')" clearable>
          <el-option
            v-for="dict in dict.type.qf_order_side"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('field.orderType')" prop="type">
        <el-select v-model="queryParams.type" :placeholder="$t('placeholder.selectOrderType')" clearable>
          <el-option
            v-for="dict in dict.type.qf_order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('field.price')" prop="price">
        <el-input
          v-model="queryParams.price"
          :placeholder="$t('placeholder.price')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.quantity')" prop="quantity">
        <el-input
          v-model="queryParams.quantity"
          :placeholder="$t('placeholder.quantity')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="成交数量" prop="filledQty">
        <el-input
          v-model="queryParams.filledQty"
          placeholder="请输入成交数量"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.avgPrice')" prop="avgPrice">
        <el-input
          v-model="queryParams.avgPrice"
          placeholder="请输入成交均价"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="金额" prop="amount">
        <el-input
          v-model="queryParams.amount"
          placeholder="请输入金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="手续费" prop="commission">
        <el-input
          v-model="queryParams.commission"
          placeholder="请输入手续费"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('placeholder.selectStatus')" clearable>
          <el-option
            v-for="dict in dict.type.qf_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="交易所订单ID" prop="exchangeOrderId">
        <el-input
          v-model="queryParams.exchangeOrderId"
          placeholder="请输入交易所订单ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['trading:order:add']"
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
          v-hasPermi="['trading:order:edit']"
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
          v-hasPermi="['trading:order:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['trading:order:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="orderList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="订单ID" align="center" prop="id" />
      <el-table-column label="订单号" align="center" prop="orderNo" />
      <el-table-column label="策略ID" align="center" prop="strategyId" />
      <el-table-column label="账户ID" align="center" prop="accountId" />
      <el-table-column label="交易对" align="center" prop="symbol" />
      <el-table-column label="方向" align="center" prop="side">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qf_order_side" :value="scope.row.side"/>
        </template>
      </el-table-column>
      <el-table-column label="类型" align="center" prop="type">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qf_order_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="价格" align="center" prop="price" />
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="成交数量" align="center" prop="filledQty" />
      <el-table-column label="成交均价" align="center" prop="avgPrice" />
      <el-table-column label="金额" align="center" prop="amount" />
      <el-table-column label="手续费" align="center" prop="commission" />
      <el-table-column label="状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.qf_order_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="交易所订单ID" align="center" prop="exchangeOrderId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['trading:order:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['trading:order:remove']"
          >删除</el-button>
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

    <!-- 添加或修改订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="账户ID" prop="accountId">
          <el-input v-model="form.accountId" placeholder="请输入账户ID" />
        </el-form-item>
        <el-form-item label="交易对" prop="symbol">
          <el-input v-model="form.symbol" placeholder="请输入交易对" />
        </el-form-item>
        <el-form-item label="方向" prop="side">
          <el-select v-model="form.side" placeholder="请选择方向">
            <el-option
              v-for="dict in dict.type.qf_order_side"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型">
            <el-option
              v-for="dict in dict.type.qf_order_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格" />
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量" />
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
import { listOrder, getOrder, delOrder, addOrder, updateOrder } from "@/api/trading/order"

export default {
  name: "Order",
  dicts: ['qf_order_side', 'qf_order_type', 'qf_order_status'],
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
      // 订单表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderNo: null,
        strategyId: null,
        accountId: null,
        symbol: null,
        side: null,
        type: null,
        price: null,
        quantity: null,
        filledQty: null,
        avgPrice: null,
        amount: null,
        commission: null,
        status: null,
        exchangeOrderId: null,
        errorMsg: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        orderNo: [
          { required: true, message: "订单号不能为空", trigger: "blur" }
        ],
        strategyId: [
          { required: true, message: "策略ID不能为空", trigger: "blur" }
        ],
        accountId: [
          { required: true, message: "账户ID不能为空", trigger: "blur" }
        ],
        symbol: [
          { required: true, message: "交易对不能为空", trigger: "blur" }
        ],
        side: [
          { required: true, message: "方向不能为空", trigger: "change" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ],
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询订单列表 */
    getList() {
      this.loading = true
      listOrder(this.queryParams).then(response => {
        this.orderList = response.rows
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
        orderNo: null,
        strategyId: null,
        accountId: null,
        symbol: null,
        side: null,
        type: null,
        price: null,
        quantity: null,
        filledQty: null,
        avgPrice: null,
        amount: null,
        commission: null,
        status: null,
        exchangeOrderId: null,
        errorMsg: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null
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
      this.title = "添加订单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getOrder(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改订单"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addOrder(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除订单编号为"' + ids + '"的数据项？').then(function() {
        return delOrder(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('trading/order/export', {
        ...this.queryParams
      }, `order_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
