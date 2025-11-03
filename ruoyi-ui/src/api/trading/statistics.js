import request from '@/utils/request'

// 获取交易统计
export function getTradingStatistics() {
  return request({
    url: '/trading/statistics/trading',
    method: 'get'
  })
}
