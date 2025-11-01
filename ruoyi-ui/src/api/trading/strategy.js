import request from '@/utils/request'

// 查询策略列表
export function listStrategy(query) {
  return request({
    url: '/trading/strategy/list',
    method: 'get',
    params: query
  })
}

// 查询策略详细
export function getStrategy(id) {
  return request({
    url: '/trading/strategy/' + id,
    method: 'get'
  })
}

// 新增策略
export function addStrategy(data) {
  return request({
    url: '/trading/strategy',
    method: 'post',
    data: data
  })
}

// 修改策略
export function updateStrategy(data) {
  return request({
    url: '/trading/strategy',
    method: 'put',
    data: data
  })
}

// 删除策略
export function delStrategy(id) {
  return request({
    url: '/trading/strategy/' + id,
    method: 'delete'
  })
}

// 启动策略
export function startStrategy(id) {
  return request({
    url: '/trading/strategy/start/' + id,
    method: 'post'
  })
}

// 停止策略
export function stopStrategy(id) {
  return request({
    url: '/trading/strategy/stop/' + id,
    method: 'post'
  })
}

// 手动执行策略
export function executeStrategy(id) {
  return request({
    url: '/trading/strategy/execute/' + id,
    method: 'post'
  })
}
