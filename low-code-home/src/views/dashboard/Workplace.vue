<template>
  <div class="generator-container">
    <div class="left">
      <a-card title="代码生成">
        <div class="config">
          <a-button @click="configDataSource" type="primary">数据源配置</a-button>
          <a-form layout="inline">
            <a-form-item label="数据库">
              <a-select default-value="请选择" style="width: 120px" @change="handleDataBaseChange">
                <a-select-option v-for="(item,index) in dataBases" :key="index" :value="item">
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="后端框架">
              <a-select default-value="请选择" style="width: 120px" @change="handleChange">
                <a-select-option v-for="(item,index) in endFrames" :key="index" :value="item">
                  {{ item }}
                </a-select-option>
              </a-select>
            </a-form-item>
            <a-form-item label="前端框架">
              <a-select default-value="请选择" style="width: 120px" @change="handleChange">
                <a-select-option value="element" :disabled="!hasToken">
                  ElementUI
                </a-select-option>
                <a-select-option value="antd" :disabled="!hasToken">
                  AntDesignUI
                </a-select-option>
                <a-select-option value="react" :disabled="!hasToken">
                  React
                </a-select-option>
              </a-select>
            </a-form-item>
          </a-form>
        </div>
        <!-- 表数据 -->
        <a-table
          :rowKey="(record)=>record.key"
          :columns="tableColumns"
          :data-source="tables"
          :pagination="{ pageSize: clientWidth > 2000 ? 12:8 }"
          :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        />
        <!-- 操作区 -->
        <a-button class="generator-code" type="primary" @click="generatorCode">生成代码配置</a-button>
        <!-- 数据源配置 -->
        <a-modal width="680px" v-model="ConfigDataSourceVisible" title="配置数据源" on-ok="handleOk">
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
        <a-modal width="680px" v-model="generatorCodeVisible" title="生成代码配置" on-ok="handleOk">
          <template slot="footer">
            <a-button key="back" @click="()=>generatorCodeVisible=false">
              取消
            </a-button>
            <a-button key="submit" type="primary" @click="generatorCodeOk">
              开始生成
            </a-button>
          </template>
          <GeneratorCode :tableNames="tableNames" :options="options" :generatorCodeVisible="generatorCodeVisible"/>
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
      endFrames: ['MyBatis-Plus', 'Mybatis'],
      dataBases: [],
      tableColumns,
      selectedRowKeys: [],
      options: [],
      filePath: '请选择代码生成目录',
      tables: [],
      tableNames: []
    }
  },
  computed: {
    hasToken () {
      return this.$store.getters.token
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
    this.getFilesData()
    this.allTables()
    const that = this
    window.onresize = () => {
      return (() => {
        window.clientWidth = document.body.clientWidth
        that.clientWidth = window.clientWidth
      })()
    }
    console.log(this.clientWidth)
  },
  methods: {
    configDataSource () {
      this.ConfigDataSourceVisible = true
    },
    // 获取本地磁盘目录
    getFilesData () {
      this.$http.post('/file/Folders', { files: this.filePath }).then(res => {
        this.options = res.folders
      })
    },
    async handleDataBaseChange (val) {
      console.log(val)
      const dataSource = this.$store.state.database.dataSource
      const params = {
        config: dataSource,
        tableName: val
      }
      const { data: { result } } = await TableInfos(params)
      this.tables = result
      console.log(result)
    },
    // 保存数据源连接
    handleOk () {
      this.$store.dispatch('database/preservationConnect', this.$refs.checkOutConn.dataSourceConfig).then(res => {
        this.loading = true
        if (res) {
          this.allTables()
          console.log('@@', this.dataBases)
          this.ConfigDataSourceVisible = false
        } else {
          message.error('连接失败，请核查数据源信息')
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
      console.log('selectedRowKeys changed: ', selectedRowKeys)
      this.selectedRowKeys = selectedRowKeys
    },
    generatorCode () {
      if (this.selectedRowKeys.length <= 0) {
        message.warning('请选择需要生成代码的表')
      } else {
        const names = this.tables.filter((item, index) => {
          if (this.selectedRowKeys.includes(index)) {
            return item.tableName
          }
        })
        names.forEach(item => {
          this.tableNames.push(item.tableName)
        })
        this.generatorCodeVisible = true
      }
    },
    generatorCodeOk () {
      message.success('代码生成成功')
    },
    handleChange () {}
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
