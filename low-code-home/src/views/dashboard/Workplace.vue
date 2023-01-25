<template>
  <div class="generator-container">
    <div class="left">
      <a-card >
        <div slot="title">
          <a-tooltip placement="top">
            <template slot="title">
              <span>点击全选，只会生成表格当前页的全部表结构的代码</span>
            </template>
            <a-icon type="question-circle" />
          </a-tooltip>
          代码生成</div>
        <div class="config">
          <a-button v-show="!hasToken" @click="configDataSource" type="primary">数据源配置</a-button>
          <a-select v-show="hasToken" :default-value="currentDataSource.sourceName?currentDataSource.sourceName:'请选择数据源'" style="width: 150px" @change="handleDataSourceChange">
            <a-select-option v-for="(item,index) in dataSources" :key="index" :value="item.id">
              {{ item.sourceName }}
            </a-select-option>
          </a-select>
          <a-form layout="inline">
            <a-form-item label="数据库">
              <a-select :default-value="tableData.val ? tableData.val:'请选择'" style="width: 150px" @change="handleDataBaseChange">
                <a-select-option v-for="(item,index) in dataBases" :key="index" :value="item">
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="后端模板">
              <a-select default-value="请选择" style="width: 150px" @change="handleChange">
                <a-select-option v-for="(item,index) in endFrames" :key="index" :value="item">
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
        </div>
        <!-- 表数据 -->
        <a-table
          :rowKey="(record)=>record.key"
          :columns="tableColumns"
          :data-source="tableData.result"
          :pagination="{ pageSize: clientWidth > 2000 ? 12:8 }"
          :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        />
        <!-- 操作区 -->
        <a-button class="generator-code" type="primary" @click="generatorCode">生成代码配置</a-button>
        <!-- 数据源配置 -->
        <a-modal width="680px" v-model="ConfigDataSourceVisible" on-ok="handleOk">
          <div slot="title">
            <a-tooltip placement="top">
              <template slot="title">
                <span>连接本地MySQL服务需要进行内网ip映射</span>
              </template>
              <a-icon type="question-circle" />
            </a-tooltip>
            配置数据源</div>
          <template slot="footer">
            <a-button key="back" @click="()=>ConfigDataSourceVisible=false">
              取消
            </a-button>
            <a-button key="checkout" @click="checkoutConnect">
              测试连接
            </a-button>
            <a-button key="submit" type="primary" @click="handleOk">
              保存配置
            </a-button>
          </template>
          <ConfigDataSource ref="checkOutConn"/>
        </a-modal>
        <!-- 生成代码配置 -->
        <a-modal width="680px" v-model="generatorCodeVisible" on-ok="handleOk">
          <div slot="title">
            <a-tooltip placement="top">
              <template slot="title">
                <span>代码默认生成路径为本地D盘</span>
              </template>
              <a-icon type="question-circle" />
            </a-tooltip>
            生成代码配置</div>
          <template slot="footer">
            <a-button key="back" @click="()=>generatorCodeVisible=false">
              取消
            </a-button>
            <a-button key="submit" type="primary" @click="generatorCodeOk">
              开始生成
            </a-button>
          </template>
          <GeneratorCode ref="generatorRef" :tableNames="tableNames" :options="options" :generatorCodeVisible="generatorCodeVisible"/>
        </a-modal>
      </a-card>
      <BackTop :visibilityHeight="200"/>
    </div>
  </div>
</template>

<script>
import { TableInfos } from '@/api/database'
import { tableColumns } from '@/utils/columns'
import { message, BackTop } from 'ant-design-vue'
import ConfigDataSource from '@/views/dashboard/ConfigDataSource'
import GeneratorCode from '@/views/dashboard/GeneratorCode'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { DataSourceDetail, dataSources } from '@/api/datasource'
import { generatorCode } from '@/api/code'
export default {
  name: 'Workplace',
  components: {
    GeneratorCode,
    ConfigDataSource,
    BackTop
  },
  data () {
    return {
      clientWidth: document.body.clientWidth,
      ConfigDataSourceVisible: false,
      generatorCodeVisible: false,
      endFrames: ['MybatisPlus-Template', 'Mybatis-Template'],
      dataBases: [],
      tableColumns,
      selectedRowKeys: [],
      options: [],
      filePath: '请选择代码生成目录',
      tables: [],
      tableNames: [],
      hasToken: '',
      dataSources: [],
      templatePath: ''
    }
  },
  computed: {
    tableData () {
      return this.$store.getters.tableData
    },
    currentDataSource () {
      return this.$store.getters.currentDataSource
    }

  },
  watch: {
    generatorCodeVisible: {
      immediate: true,
      handler (newVal, oldVal) {
        if (!newVal) {
          this.tableNames = []
        }
      }
    }
  },
  mounted () {
    this.openNotificationWithIcon()
    this.hasToken = localStorage.getItem(ACCESS_TOKEN)
    // 用户已登录再执行
    if (this.hasToken) {
      this.AllDataSource()
    }
    this.getFilesData()
    this.allTables()
    const that = this
    window.onresize = () => {
      return (() => {
        window.clientWidth = document.body.clientWidth
        that.clientWidth = window.clientWidth
      })()
    }
  },
  methods: {
    openNotificationWithIcon () {
      this.$notification['warning']({
        message: '温馨提示',
        description:
          `如果连接本地Mysql数据库，请使用nat123配置内网ip映射，具体操作教程请前往本站GitHub仓库`
      })
    },
    async AllDataSource () {
      const userId = this.$store.getters.userInfo.userId
      const { data } = await dataSources(userId)
      this.dataSources = data.dataSources
    },
    configDataSource () {
      this.ConfigDataSourceVisible = true
    },
    // 获取本地磁盘目录
    getFilesData () {
      this.$http.post('/file/Folders', { files: this.filePath }).then(res => {
        this.options = res.folders
      })
    },
    async handleDataSourceChange (val) {
      const { data } = await DataSourceDetail(val)
      this.$store.dispatch('table/currentDataSourceData', data.dataSource)
      const parameter = {
        ip: data.dataSource.sourceIp,
        port: data.dataSource.sourcePort,
        loginName: data.dataSource.sourceAccount,
        password: data.dataSource.sourcePassword
      }
      const result = await this.$store.dispatch('database/allTableList', parameter)
      this.dataBases = result
    },
    async handleDataBaseChange (val) {
      this.selectedRowKeys = []
      const dataSource = this.$store.state.database.dataSource
      const params = {
        config: dataSource,
        tableName: val
      }
      const { data: { result } } = await TableInfos(params)
      this.tables = result
      const tableData = {
        result,
        val
      }
      this.$store.dispatch('table/currentTablesData', tableData)
    },
    // 保存数据源连接
    handleOk () {
      this.$store.dispatch('database/preservationConnect', this.$refs.checkOutConn.dataSourceConfig).then(res => {
        this.loading = true
        if (res) {
          this.allTables()
          this.ConfigDataSourceVisible = false
        } else {
          message.error('连接失败，请核查数据源信息或nat123是否开启')
          this.ConfigDataSourceVisible = true
        }
      })
    },
    // 获取所有的表数据
    allTables () {
      this.dataBases = this.$store.getters.tables
    },
    /**
     * 测试连接数据源
     */
    checkoutConnect () {
      this.$store.dispatch('database/checkOutConnectResult', this.$refs.checkOutConn.dataSourceConfig)
    },
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    generatorCode () {
      if (this.selectedRowKeys.length <= 0) {
        message.warning('请选择需要生成代码的表')
      } else {
        console.log(this.selectedRowKeys)
        this.tableNames = this.$store.getters.tableData.result.filter((item, index) => {
          if (this.selectedRowKeys.indexOf(index) !== -1) {
            return item
          }
        })
        this.$store.dispatch('table/choseTableData', this.tableNames)
        this.generatorCodeVisible = true
      }
    },
    async generatorCodeOk () {
      if (this.templatePath.trim() === '') {
        message.warn('生成模板为空，请选择后继续操作')
        return
      }
      const generatorConfig = this.$refs.generatorRef.generatorConfig
      const datasource = this.$store.state.database.dataSource
      const parameter = {
        generatorConfig,
        config: datasource,
        databaseName: this.tableData.val,
        templatePath: this.templatePath,
        tableNames: this.tableNames.length === this.tableData.result ? [] : JSON.stringify(this.tableNames)
      }
      const data = await generatorCode(parameter)
      if (data.code === 200) {
        message.success('代码生成成功')
        this.generatorCodeVisible = false
      } else {
        message.error('代码生成失败，请检查字段数据类型')
      }
    },
    handleChange (val) {
      this.templatePath = val
    }
  }
}
</script>
<style lang='less' scoped>
.generator-container {
  width: 100%;
  height: 100vh;
  background-color: #FFF;
  display: flex;
  justify-content: space-between;
  .left {
    width: 100%;
    .config {
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }
    .generator-code {
      margin-top: 10px;
    }
  }
}
</style>
