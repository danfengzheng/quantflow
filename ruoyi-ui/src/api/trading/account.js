import request from '@/utils/request'

// 查询账户列表
export function listAccount(query) {
  return request({
    url: '/trading/account/list',
    method: 'get',
    params: query
  })
}

// 查询账户详细
export function getAccount(id) {
  return request({
    url: '/trading/account/' + id,
    method: 'get'
  })
}

// 新增账户
export function addAccount(data) {
  return request({
    url: '/trading/account',
    method: 'post',
    data: data
  })
}

// 修改账户
export function updateAccount(data) {
  return request({
    url: '/trading/account',
    method: 'put',
    data: data
  })
}

// 删除账户
export function delAccount(id) {
  return request({
    url: '/trading/account/' + id,
    method: 'delete'
  })
}

// 测试连接
export function testConnection(id) {
  return request({
    url: '/trading/account/testConnection/' + id,
    method: 'post',
    data: {
      id: id
    }
  })
}