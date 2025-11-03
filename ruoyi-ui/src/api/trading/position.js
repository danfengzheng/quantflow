import request from '@/utils/request'

// 查询持仓列表
export function listPosition(query) {
  return request({
    url: '/trading/position/list',
    method: 'get',
    params: query
  })
}
