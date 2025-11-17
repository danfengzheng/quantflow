<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item :label="$t('field.menuName')" prop="menuName">
        <el-input
          v-model="queryParams.menuName"
          :placeholder="$t('placeholder.menuName')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('field.status')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('placeholder.selectStatus')" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('button.search')}}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('button.reset')}}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:menu:add']"
        >{{ $t('button.add')}}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >{{ $t('button.expand')}}/{{ $t('button.collapse')}}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="menuList"
      row-key="menuId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="menuName" :label="$t('field.menuName')" :show-overflow-tooltip="true" width="160"></el-table-column>
      <el-table-column prop="icon" :label="$t('field.icon')" align="center" width="100">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" :label="$t('field.sort')" width="60"></el-table-column>
      <el-table-column prop="perms" :label="$t('field.perms')" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="component" :label="$t('field.component')" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="status" :label="$t('field.status')" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('field.createTime')" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('common.operate')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:menu:edit']"
          >{{ $t('button.edit')}}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
          >{{ $t('button.add')}}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
          >{{ $t('button.delete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 添加或修改菜单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('field.parentId')" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                :placeholder="$t('placeholder.selectParentMenu')"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item :label="$t('field.menuType')" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">{{ $t('module.system.menu.menuTypeDirectory') }}</el-radio>
                <el-radio label="C">{{ $t('module.system.menu.menuTypeMenu') }}</el-radio>
                <el-radio label="F">{{ $t('module.system.menu.menuTypeButton') }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item :label="$t('field.icon')" prop="icon">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" :active-icon="form.icon" />
                <el-input slot="reference" v-model="form.icon" :placeholder="$t('placeholder.selectIcon')" readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    style="width: 25px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('field.orderNum')" prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('field.menuName')" prop="menuName">
              <el-input v-model="form.menuName" :placeholder="$t('placeholder.menuName')" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="routeName">
              <el-input v-model="form.routeName" :placeholder="$t('placeholder.routeName')" />
              <span slot="label">
                <el-tooltip content="默认不填则和路由地址相同：如地址为：`user`，则名称为`User`（注意：为避免名字的冲突，特殊情况下请自定义，保证唯一性）" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.routeName') }}
              </span>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 多语言设置（折叠面板） -->
        <el-row>
          <el-col :span="24">
            <el-collapse v-model="activeI18nCollapse" style="margin-bottom: 10px;">
              <el-collapse-item title="多语言设置" name="i18n">
                <el-tabs v-model="i18nActiveTab" type="border-card">
                  <el-tab-pane label="菜单名称" name="menu_name">
                    <el-form :model="i18nForm" label-width="140px" size="small">
                      <el-form-item label="简体中文 (zh-CN)">
                        <el-input v-model="i18nForm.menu_name['zh-CN']" placeholder="请输入简体中文菜单名称" />
                      </el-form-item>
                      <el-form-item label="繁体中文 (zh-TW)">
                        <el-input v-model="i18nForm.menu_name['zh-TW']" placeholder="請輸入繁體中文菜單名稱" />
                      </el-form-item>
                      <el-form-item label="English (en-US)">
                        <el-input v-model="i18nForm.menu_name['en-US']" placeholder="Enter English menu name" />
                      </el-form-item>
                      <el-form-item label="日本語 (ja-JP)">
                        <el-input v-model="i18nForm.menu_name['ja-JP']" placeholder="メニュー名を入力してください" />
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                  <el-tab-pane label="备注" name="remark">
                    <el-form :model="i18nForm" label-width="140px" size="small">
                      <el-form-item label="简体中文 (zh-CN)">
                        <el-input v-model="i18nForm.remark['zh-CN']" type="textarea" :rows="3" placeholder="请输入简体中文备注" />
                      </el-form-item>
                      <el-form-item label="繁体中文 (zh-TW)">
                        <el-input v-model="i18nForm.remark['zh-TW']" type="textarea" :rows="3" placeholder="請輸入繁體中文備註" />
                      </el-form-item>
                      <el-form-item label="English (en-US)">
                        <el-input v-model="i18nForm.remark['en-US']" type="textarea" :rows="3" placeholder="Enter English remark" />
                      </el-form-item>
                      <el-form-item label="日本語 (ja-JP)">
                        <el-input v-model="i18nForm.remark['ja-JP']" type="textarea" :rows="3" placeholder="備考を入力してください" />
                      </el-form-item>
                    </el-form>
                  </el-tab-pane>
                </el-tabs>
                <div style="margin-top: 10px; text-align: right;">
                  <el-button type="primary" size="small" @click="saveI18nTranslationsInline" :disabled="!form.menuId">
                    {{ $t('button.save') || '保存多语言' }}
                  </el-button>
                  <el-button size="small" @click="loadI18nTranslations(form.menuId)" :disabled="!form.menuId">
                    {{ $t('button.refresh') || '刷新' }}
                  </el-button>
                </div>
              </el-collapse-item>
            </el-collapse>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="isFrame">
              <span slot="label">
                <el-tooltip content="选择是外链则路由地址需要以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.isFrame') }}
              </span>
              <el-radio-group v-model="form.isFrame">
                <el-radio label="0">{{ $t('common.yes') }}</el-radio>
                <el-radio label="1">{{ $t('common.no') }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="path">
              <span slot="label">
                <el-tooltip content="访问的路由地址，如：`user`，如外网地址需内链访问则以`http(s)://`开头" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.path') }}
              </span>
              <el-input v-model="form.path" :placeholder="$t('placeholder.routePath')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="访问的组件路径，如：`system/user/index`，默认在`views`目录下" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.component') }}
              </span>
              <el-input v-model="form.component" :placeholder="$t('placeholder.componentPath')" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'M'">
            <el-form-item prop="perms">
              <el-input v-model="form.perms" :placeholder="$t('placeholder.perms')" maxlength="100" />
              <span slot="label">
                <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.perms') }}
              </span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="query">
              <el-input v-model="form.query" :placeholder="$t('placeholder.routeParams')" maxlength="255" />
              <span slot="label">
                <el-tooltip content='访问路由的默认传递参数，如：`{"id": 1, "name": "ry"}`' placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.query') }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="isCache">
              <span slot="label">
                <el-tooltip content="选择是则会被`keep-alive`缓存，需要匹配组件的`name`和地址保持一致" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.isCache') }}
              </span>
              <el-radio-group v-model="form.isCache">
                <el-radio label="0">{{ $t('common.cache') }}</el-radio>
                <el-radio label="1">{{ $t('common.notCache') }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="visible">
              <span slot="label">
                <el-tooltip content="选择隐藏则路由将不会出现在侧边栏，但仍然可以访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.visible') }}
              </span>
              <el-radio-group v-model="form.visible">
                <el-radio
                  v-for="dict in dict.type.sys_show_hide"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="status">
              <span slot="label">
                <el-tooltip content="选择停用则路由将不会出现在侧边栏，也不能被访问" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                {{ $t('field.status') }}
              </span>
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">{{ $t('button.submit')}}</el-button>
        <el-button @click="cancel">{{ $t('button.cancel')}}</el-button>
      </div>
      </el-dialog>

      <!-- 多语言设置对话框 -->
      <el-dialog :title="$t('field.multiLanguage') || '多语言设置'" :visible.sync="showI18nDialog" width="600px" append-to-body>
        <el-tabs v-model="i18nActiveTab">
          <el-tab-pane label="菜单名称" name="menu_name">
            <el-form :model="i18nForm" label-width="100px">
              <el-form-item label="简体中文">
                <el-input v-model="i18nForm.menu_name['zh-CN']" placeholder="请输入简体中文菜单名称" />
              </el-form-item>
              <el-form-item label="繁体中文">
                <el-input v-model="i18nForm.menu_name['zh-TW']" placeholder="请输入繁体中文菜单名称" />
              </el-form-item>
              <el-form-item label="English">
                <el-input v-model="i18nForm.menu_name['en-US']" placeholder="Enter English menu name" />
              </el-form-item>
              <el-form-item label="日本語">
                <el-input v-model="i18nForm.menu_name['ja-JP']" placeholder="メニュー名を入力してください" />
              </el-form-item>
            </el-form>
          </el-tab-pane>
          <el-tab-pane label="备注" name="remark">
            <el-form :model="i18nForm" label-width="100px">
              <el-form-item label="简体中文">
                <el-input v-model="i18nForm.remark['zh-CN']" type="textarea" :rows="3" placeholder="请输入简体中文备注" />
              </el-form-item>
              <el-form-item label="繁体中文">
                <el-input v-model="i18nForm.remark['zh-TW']" type="textarea" :rows="3" placeholder="請輸入繁體中文備註" />
              </el-form-item>
              <el-form-item label="English">
                <el-input v-model="i18nForm.remark['en-US']" type="textarea" :rows="3" placeholder="Enter English remark" />
              </el-form-item>
              <el-form-item label="日本語">
                <el-input v-model="i18nForm.remark['ja-JP']" type="textarea" :rows="3" placeholder="備考を入力してください" />
              </el-form-item>
            </el-form>
          </el-tab-pane>
        </el-tabs>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="saveI18nTranslations">{{ $t('button.confirm') }}</el-button>
          <el-button @click="showI18nDialog = false">{{ $t('button.cancel') }}</el-button>
        </div>
      </el-dialog>
    </div>
  </template>

<script>
import { listMenu, getMenu, delMenu, addMenu, updateMenu } from "@/api/system/menu"
import { getFieldTranslations, saveFieldTranslations } from "@/api/system/i18n"
import Treeselect from "@riophae/vue-treeselect"
import "@riophae/vue-treeselect/dist/vue-treeselect.css"
import IconSelect from "@/components/IconSelect"

export default {
  name: "Menu",
  dicts: ['sys_show_hide', 'sys_normal_disable'],
  components: { Treeselect, IconSelect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 菜单表格树数据
      menuList: [],
      // 菜单树选项
      menuOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部折叠
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        menuName: undefined,
        visible: undefined
      },
      // 表单参数
      form: {},
      // 多语言设置对话框
      showI18nDialog: false,
      // 多语言折叠面板（主表单中）
      activeI18nCollapse: [],
      i18nActiveTab: 'menu_name',
      i18nForm: {
        menu_name: {
          'zh-CN': '',
          'zh-TW': '',
          'en-US': '',
          'ja-JP': ''
        },
        remark: {
          'zh-CN': '',
          'zh-TW': '',
          'en-US': '',
          'ja-JP': ''
        }
      },
      // 表单校验
      rules: {
        menuName: [
          { required: true, message: this.$t('message.validate.required'), trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: this.$t('message.validate.required'), trigger: "blur" }
        ],
        path: [
          { required: true, message: this.$t('message.validate.required'), trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 选择图标
    selected(name) {
      this.form.icon = name
    },
    /** 查询菜单列表 */
    getList() {
      this.loading = true
      listMenu(this.queryParams).then(response => {
        this.menuList = this.handleTree(response.data, "menuId")
        this.loading = false
      })
    },
    /** 转换菜单数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      }
    },
    /** 查询菜单下拉树结构 */
    getTreeselect() {
      listMenu().then(response => {
        this.menuOptions = []
        const menu = { menuId: 0, menuName: this.$t('module.system.menu.menuTypeDirectory'), children: [] }
        menu.children = this.handleTree(response.data, "menuId")
        this.menuOptions.push(menu)
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        menuId: undefined,
        parentId: 0,
        menuName: undefined,
        icon: undefined,
        menuType: "M",
        orderNum: undefined,
        isFrame: "1",
        isCache: "0",
        visible: "0",
        status: "0"
      }
      // 重置多语言表单
      this.activeI18nCollapse = []
      this.i18nForm = {
        menu_name: {
          'zh-CN': '',
          'zh-TW': '',
          'en-US': '',
          'ja-JP': ''
        },
        remark: {
          'zh-CN': '',
          'zh-TW': '',
          'en-US': '',
          'ja-JP': ''
        }
      }
      this.resetForm("form")
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset()
      this.getTreeselect()
      if (row != null && row.menuId) {
        this.form.parentId = row.menuId
      } else {
        this.form.parentId = 0
      }
      this.open = true
      this.title = this.$t('module.system.menu.title') + ' - ' + this.$t('button.add')
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false
      this.isExpandAll = !this.isExpandAll
      this.$nextTick(() => {
        this.refreshTable = true
      })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      this.getTreeselect()
      getMenu(row.menuId).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('module.system.menu.title') + ' - ' + this.$t('button.edit')
        // 加载多语言翻译
        this.loadI18nTranslations(row.menuId)
        // 自动展开多语言折叠面板（如果已有翻译数据）
        this.$nextTick(() => {
          // 延迟检查，确保数据已加载
          setTimeout(() => {
            const hasTranslations = Object.values(this.i18nForm.menu_name).some(v => v) || 
                                   Object.values(this.i18nForm.remark).some(v => v)
            if (hasTranslations && !this.activeI18nCollapse.includes('i18n')) {
              this.activeI18nCollapse.push('i18n')
            }
          }, 300)
        })
      })
    },
    /** 加载多语言翻译 */
    loadI18nTranslations(menuId) {
      if (!menuId) return
      
      // 加载菜单名称的多语言翻译
      getFieldTranslations('menu', menuId, 'menu_name').then(response => {
        if (response.data) {
          Object.assign(this.i18nForm.menu_name, response.data)
        }
      }).catch(() => {
        // 如果加载失败，使用默认值
      })
      
      // 加载备注的多语言翻译
      getFieldTranslations('menu', menuId, 'remark').then(response => {
        if (response.data) {
          Object.assign(this.i18nForm.remark, response.data)
        }
      }).catch(() => {
        // 如果加载失败，使用默认值
      })
    },
    /** 保存多语言翻译（独立对话框） */
    saveI18nTranslations() {
      if (!this.form.menuId) {
        this.$modal.msgError('请先保存菜单基本信息')
        return
      }
      
      // 保存菜单名称的多语言翻译
      saveFieldTranslations('menu', this.form.menuId, 'menu_name', this.i18nForm.menu_name).then(() => {
        // 保存备注的多语言翻译
        return saveFieldTranslations('menu', this.form.menuId, 'remark', this.i18nForm.remark)
      }).then(() => {
        this.$modal.msgSuccess(this.$t('message.success.edit'))
        this.showI18nDialog = false
        // 刷新菜单列表
        this.getList()
      }).catch(() => {
        this.$modal.msgError('保存多语言翻译失败')
      })
    },
    /** 保存多语言翻译（主表单中） */
    saveI18nTranslationsInline() {
      if (!this.form.menuId) {
        this.$modal.msgError('请先保存菜单基本信息')
        return
      }
      
      // 保存菜单名称的多语言翻译
      saveFieldTranslations('menu', this.form.menuId, 'menu_name', this.i18nForm.menu_name).then(() => {
        // 保存备注的多语言翻译
        return saveFieldTranslations('menu', this.form.menuId, 'remark', this.i18nForm.remark)
      }).then(() => {
        this.$modal.msgSuccess(this.$t('message.success.edit') || '保存成功')
        // 刷新菜单列表
        this.getList()
      }).catch(() => {
        this.$modal.msgError('保存多语言翻译失败')
      })
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.menuId != undefined) {
            updateMenu(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.success.edit'))
              // 如果是编辑，自动保存多语言翻译（如果折叠面板已展开且有数据）
              if (this.form.menuId && this.activeI18nCollapse.includes('i18n')) {
                this.saveI18nTranslationsInline()
              }
              this.open = false
              this.getList()
              // 重新加载多语言翻译
              if (this.form.menuId) {
                this.loadI18nTranslations(this.form.menuId)
              }
            })
          } else {
            addMenu(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('message.success.add'))
              // 新增后获取菜单ID并保存多语言翻译
              if (response.data && response.data.menuId) {
                this.form.menuId = response.data.menuId
                // 如果折叠面板已展开，保存多语言翻译
                if (this.activeI18nCollapse.includes('i18n')) {
                  // 确保默认语言有值
                  if (!this.i18nForm.menu_name['zh-CN']) {
                    this.i18nForm.menu_name['zh-CN'] = this.form.menuName || ''
                  }
                  if (!this.i18nForm.remark['zh-CN'] && this.form.remark) {
                    this.i18nForm.remark['zh-CN'] = this.form.remark
                  }
                  this.saveI18nTranslationsInline()
                } else {
                  // 否则只保存默认语言（zh-CN）的翻译
                  const defaultTranslations = {
                    'zh-CN': this.form.menuName || ''
                  }
                  saveFieldTranslations('menu', this.form.menuId, 'menu_name', defaultTranslations).catch(() => {})
                  if (this.form.remark) {
                    saveFieldTranslations('menu', this.form.menuId, 'remark', { 'zh-CN': this.form.remark }).catch(() => {})
                  }
                }
              }
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除名称为"' + row.menuName + '"的数据项？').then(function() {
        return delMenu(row.menuId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('message.success.delete'))
      }).catch(() => {})
    }
  }
}
</script>
