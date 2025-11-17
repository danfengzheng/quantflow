import request from '@/utils/request'

// 获取实体的多语言翻译
export function getTranslations(entityType, entityId) {
  return request({
    url: '/system/i18n/' + entityType + '/' + entityId,
    method: 'get'
  })
}

// 获取实体的某个字段的多语言翻译
export function getFieldTranslations(entityType, entityId, fieldName) {
  return request({
    url: '/system/i18n/' + entityType + '/' + entityId + '/' + fieldName,
    method: 'get'
  })
}

// 批量保存实体的多语言翻译
export function saveTranslations(entityType, entityId, translations) {
  return request({
    url: '/system/i18n/' + entityType + '/' + entityId,
    method: 'put',
    data: translations
  })
}

// 保存单个字段的多语言翻译
export function saveFieldTranslations(entityType, entityId, fieldName, translations) {
  return request({
    url: '/system/i18n/' + entityType + '/' + entityId + '/' + fieldName,
    method: 'put',
    data: translations
  })
}

