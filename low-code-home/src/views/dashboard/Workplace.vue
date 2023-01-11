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
          :pagination="{ pageSize: 5 }"
          :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        />
        <!-- 操作区 -->
        <a-button type="primary" @click="generatorCode">生成代码配置</a-button>
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
          <GeneratorCode :options="options" :generatorCodeVisible="generatorCodeVisible"/>
        </a-modal>
      </a-card>
    </div>
  </div>
</template>

<script>
import { tableColumns } from '@/utils/columns'
import { message } from 'ant-design-vue'
import ConfigDataSource from '@/views/dashboard/ConfigDataSource'
import GeneratorCode from '@/views/dashboard/GeneratorCode'
export default {
  name: 'Workplace',
  components: {
    GeneratorCode,
    ConfigDataSource
  },
  data () {
    return {
      ConfigDataSourceVisible: false,
      generatorCodeVisible: false,
      endFrames: ['MyBatis-Plus', 'Mybatis'],
      dataBases: [],
      tableColumns,
      selectedRowKeys: [],
      options: [],
      filePath: '请选择代码生成目录',
      tables: [
        {
          tableName: 'user',
          desc: '用户表'
        },
        {
          tableName: 'article',
          desc: '文章表'
        },
        {
          tableName: 'comment',
          desc: '评论表'
        },
        {
          tableName: 'like',
          desc: '点赞表'
        },
        {
          tableName: 'role',
          desc: '角色表'
        }
      ]
    }
  },
  computed: {
    hasToken () {
      return this.$store.getters.token
    }
  },
  mounted () {
    this.getFilesData()
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
    handleDataBaseChange () {},
    // 保存数据源连接
    handleOk () {
      this.$store.dispatch('database/preservationConnect', this.$refs.checkOutConn.dataSourceConfig)
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
      this.generatorCodeVisible = true
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
  }
}
</style>
