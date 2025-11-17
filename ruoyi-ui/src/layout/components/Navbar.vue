<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb v-if="!topNav" id="breadcrumb-container" class="breadcrumb-container" />
    <top-nav v-if="topNav" id="topmenu-container" class="topmenu-container" />

    <div class="right-menu">
      <template v-if="device!=='mobile'">
        <search id="header-search" class="right-menu-item" />

        <!-- <el-tooltip :content="$t('module.navbar.sourceCode')" effect="dark" placement="bottom">
          <ruo-yi-git id="ruoyi-git" class="right-menu-item hover-effect" />
        </el-tooltip>

        <el-tooltip :content="$t('module.navbar.document')" effect="dark" placement="bottom">
          <ruo-yi-doc id="ruoyi-doc" class="right-menu-item hover-effect" />
        </el-tooltip> -->

        <screenfull id="screenfull" class="right-menu-item hover-effect" />

        <el-tooltip :content="$t('module.navbar.layoutSize')" effect="dark" placement="bottom">
          <size-select id="size-select" class="right-menu-item hover-effect" />
        </el-tooltip>

        <el-dropdown class="right-menu-item hover-effect" @command="changeLang">
          <span class="el-dropdown-link">
            <span class="lang-icon">{{ currentLangIcon }}</span>
            <span class="lang-text">{{ currentLangLabel }}</span>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item
              v-for="dict in dict.type.sys_i18n_locale"
              :key="dict.value"
              :command="dict.value"
            >
              <span class="lang-option">
                <span class="lang-icon">{{ getLangIcon(dict.value) }}</span>
                <span>{{ dict.label }}</span>
              </span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>

      </template>

      <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="hover">
        <div class="avatar-wrapper">
          <img :src="avatar" class="user-avatar">
          <span class="user-nickname"> {{ nickName }} </span>
        </div>
        <el-dropdown-menu slot="dropdown">
          <router-link to="/user/profile">
            <el-dropdown-item>{{$t('module.navbar.profile')}}</el-dropdown-item>
          </router-link>
          <el-dropdown-item @click.native="setLayout" v-if="setting">
            <span>{{$t('module.navbar.layoutSetting')}}</span>
          </el-dropdown-item>
          <el-dropdown-item divided @click.native="logout">
            <span>{{$t('module.navbar.logout')}}</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch'
import RuoYiGit from '@/components/RuoYi/Git'
import RuoYiDoc from '@/components/RuoYi/Doc'

export default {
  emits: ['setLayout'],
  dicts: ['sys_i18n_locale'],
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
    RuoYiGit,
    RuoYiDoc
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device',
      'nickName'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    },
    currentLangLabel() {
      const locale = this.$i18n && this.$i18n.locale
      if (this.dict && this.dict.type && this.dict.type.sys_i18n_locale) {
        const dictItem = this.dict.type.sys_i18n_locale.find(item => item.value === locale)
        if (dictItem) {
          // 返回简短标签（用于显示在按钮上）
          const shortLabelMap = {
            'zh-CN': '简体',
            'zh-TW': '繁體',
            'en-US': 'EN',
            'ja-JP': '日本語'
          }
          return shortLabelMap[locale] || dictItem.label
        }
      }
      return '简体'
    },
    currentLangIcon() {
      return this.getLangIcon(this.$i18n && this.$i18n.locale)
    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    setLayout(event) {
      this.$emit('setLayout')
    },
    logout() {
      this.$confirm(this.$t('module.navbar.logoutConfirm'), this.$t('common.tips'), {
        confirmButtonText: this.$t('button.confirm'),
        cancelButtonText: this.$t('button.cancel'),
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('LogOut').then(() => {
          location.href = '/index'
        })
      }).catch(() => {})
    },
    changeLang(lang) {
      if (this.$i18n && this.$i18n.locale !== lang) {
        this.$i18n.locale = lang
        localStorage.setItem('lang', lang)
        // 可按需刷新路由文案或强制刷新
        this.$message.success(this.$t('message.success.switchLang'))
      }
    },
    getLangIcon(locale) {
      const iconMap = {
        'zh-CN': '🇨🇳',
        'zh-TW': '🇹🇼',
        'en-US': '🇺🇸',
        'ja-JP': '🇯🇵'
      }
      return iconMap[locale] || '🇨🇳'
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }

      .el-dropdown-link {
        display: flex;
        align-items: center;
        gap: 6px;

        .lang-icon {
          font-size: 18px;
          line-height: 1;
        }

        .lang-text {
          font-size: 14px;
        }
      }
    }

    .avatar-container {
      margin-right: 0px;
      padding-right: 0px;

      .avatar-wrapper {
        margin-top: 10px;
        right: 8px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 30px;
          height: 30px;
          border-radius: 50%;
        }

        .user-nickname{
          position: relative;
          bottom: 10px;
          left: 2px;
          font-size: 14px;
          font-weight: bold;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}

// 语言下拉菜单样式
::v-deep .el-dropdown-menu {
  .lang-option {
    display: flex;
    align-items: center;
    gap: 8px;

    .lang-icon {
      font-size: 18px;
      line-height: 1;
    }
  }
}
</style>
