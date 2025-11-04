import request from '@/utils/request'

// 查询市场信号分析列表
export function listSignal(query) {
  return request({
    url: '/trading/signal/list',
    method: 'get',
    params: query
  })
}

// 查询市场信号分析详细
export function getSignal(signalId) {
  return request({
    url: '/trading/signal/' + signalId,
    method: 'get'
  })
}

// 实时分析市场信号
export function analyzeSignal(symbol, interval) {
  return request({
    url: `/trading/signal/analyze/${symbol}/${interval}`,
    method: 'get'
  })
}

// 批量分析多个交易对
export function batchAnalyzeSignal(data) {
  return request({
    url: '/trading/signal/batchAnalyze',
    method: 'post',
    data: data
  })
}

// 获取推荐交易列表
export function getRecommendSignals() {
  return request({
    url: '/trading/signal/recommend',
    method: 'get'
  })
}

// 新增市场信号分析
export function addSignal(data) {
  return request({
    url: '/trading/signal',
    method: 'post',
    data: data
  })
}

// 修改市场信号分析
export function updateSignal(data) {
  return request({
    url: '/trading/signal',
    method: 'put',
    data: data
  })
}

// 删除市场信号分析
export function delSignal(signalId) {
  return request({
    url: '/trading/signal/' + signalId,
    method: 'delete'
  })
}

// 导出市场信号分析
export function exportSignal(query) {
  return request({
    url: '/trading/signal/export',
    method: 'post',
    params: query
  })
}
