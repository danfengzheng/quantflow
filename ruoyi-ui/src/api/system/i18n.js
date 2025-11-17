import request from '@/utils/request'

// 查询多语言翻译列表
export function listI18n(query) {
  return request({
    url: '/system/i18n/list',
    method: 'get',
    params: query
  })
}

// 查询多语言翻译详细
export function getI18n(id) {
  return request({
    url: '/system/i18n/' + id,
    method: 'get'
  })
}

// 新增多语言翻译
export function addI18n(data) {
  return request({
    url: '/system/i18n',
    method: 'post',
    data: data
  })
}

// 修改多语言翻译
export function updateI18n(data) {
  return request({
    url: '/system/i18n',
    method: 'put',
    data: data
  })
}

// 删除多语言翻译
export function delI18n(id) {
  return request({
    url: '/system/i18n/' + id,
    method: 'delete'
  })
}

// 刷新所有多语言翻译的Redis缓存
export function refreshCache() {
  return request({
    url: '/system/i18n/refreshCache',
    method: 'delete'
  })
}

// 根据实体类型刷新多语言翻译的Redis缓存
export function refreshCacheByEntityType(entityType) {
  return request({
    url: '/system/i18n/refreshCache/' + entityType,
    method: 'delete'
  })
}

// 获取实体的多语言翻译
export function getTranslations(entityType, entityId) {
  return request({
    url: '/system/i18n/entity/' + entityType + '/' + entityId,
    method: 'get'
  })
}

// 获取实体的某个字段的多语言翻译
export function getFieldTranslations(entityType, entityId, fieldName) {
  return request({
    url: '/system/i18n/entity/' + entityType + '/' + entityId + '/' + fieldName,
    method: 'get'
  })
}

// 批量保存实体的多语言翻译
export function saveTranslations(entityType, entityId, translations) {
  return request({
    url: '/system/i18n/entity/' + entityType + '/' + entityId,
    method: 'put',
    data: translations
  })
}

// 保存单个字段的多语言翻译
export function saveFieldTranslations(entityType, entityId, fieldName, translations) {
  return request({
    url: '/system/i18n/entity/' + entityType + '/' + entityId + '/' + fieldName,
    method: 'put',
    data: translations
  })
}

