import request from '@/utils/request'

// 查询回测列表
export function listBacktest(query) {
  return request({
    url: '/trading/backtest/list',
    method: 'get',
    params: query
  })
}

// 查询回测详情
export function getBacktest(id) {
  return request({
    url: '/trading/backtest/' + id,
    method: 'get'
  })
}

// 新增回测
export function addBacktest(data) {
  return request({
    url: '/trading/backtest',
    method: 'post',
    data: data
  })
}

// 执行回测
export function executeBacktest(id) {
  return request({
    url: '/trading/backtest/execute/' + id,
    method: 'post'
  })
}

// 获取回测结果
export function getBacktestResult(id) {
  return request({
    url: '/trading/backtest/result/' + id,
    method: 'get'
  })
}

// 删除回测
export function delBacktest(id) {
  return request({
    url: '/trading/backtest/' + id,
    method: 'delete'
  })
}
