import request from '@/utils/request'

// 获取实时行情
export function getTicker(accountId, symbol) {
  return request({
    url: '/trading/market/ticker',
    method: 'get',
    params: { accountId, symbol }
  })
}

// 批量获取行情
export function getMultipleTickers(accountId, symbols) {
  return request({
    url: '/trading/market/tickers',
    method: 'get',
    params: { accountId, symbols }
  })
}

// 获取K线数据
export function getKlines(accountId, symbol, interval, limit) {
  return request({
    url: '/trading/market/klines',
    method: 'get',
    params: { accountId, symbol, interval, limit }
  })
}
