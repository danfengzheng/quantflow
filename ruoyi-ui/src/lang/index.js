import Vue from 'vue'
import VueI18n from 'vue-i18n'
import settings from '@/settings'
import zhCN from './zh-CN'
import zhTW from './zh-TW'
import enUS from './en-US'
import jaJP from './ja-JP'

Vue.use(VueI18n)

const saved = localStorage.getItem('lang')
const envLocale = process.env.VUE_APP_I18N_LOCALE
const configDefault = (settings && settings.defaultLang) || 'zh-CN'
const locale = saved || envLocale || configDefault || 'zh-CN'

const messages = {
  'zh-CN': zhCN,
  'zh-TW': zhTW,
  'en-US': enUS,
  'ja-JP': jaJP
}

const i18n = new VueI18n({
  locale,
  fallbackLocale: 'zh-CN',
  messages
})

export default i18n

