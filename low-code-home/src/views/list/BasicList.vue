<template>
  <div class="online-container">
    <a-card class="data-container">
      <div class="operation">
        <a-button @click="handlePlus" icon="plus" type="primary">新增</a-button>
        <a-tooltip placement="top" v-if="!hasToken">
          <template slot="title">
            <span>登录后可配置数据源</span>
          </template>
          <a-select default-value="请选择数据源" :disabled="!hasToken" style="width: 130px">
          </a-select>
        </a-tooltip>
        <a-select
          v-if="hasToken"
          allowClear
          default-value="请选择数据源"
          :disabled="hasToken"
          style="width: 130px"
          @change="handleChangeDatabase">
          <a-select-option v-for="(item,index) in dataSource" :key="index" :value="item">
            {{ item }}
          </a-select-option>
        </a-select>
        <a-select allowClear :default-value="onlineTableData.val?onlineTableData.val:'请选择数据库'" style="width: 130px" @change="handleChangeDatabase">
          <a-select-option v-for="(item,index) in databases" :key="index" :value="item">
            {{ item }}
          </a-select-option>
        </a-select>
      </div>
      <a-table
        :pagination="{ pageSize: clientWidth > 2000 ? 12:8 }"
        :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :columns="tableColumn"
        :data-source="onlineTableData.result"
      >
      </a-table>
      <a-modal
        title="编辑"
        closable
        :style="{maxWidth:'100vw',paddingBottom:'0'}"
        width="100vw"
        :visible="columnEditVisible"
        :dialog-style="{ top: '0px',position:'fixed'}"
        @ok="handleOk"
        @cancel="()=>columnEditVisible = false"
      >
        <ConfigFormEdit :tableName="tableName" :tableDetailData="tableDetailData" ref="configEditFormRef"/>
      </a-modal>

      <a-modal
        title="新增"
        closable
        :style="{maxWidth:'100vw',paddingBottom:'0'}"
        width="100vw"
        :visible="columnPlusVisible"
        :dialog-style="{ top: '0px',position:'fixed'}"
        @ok="handlePlusOk"
        @cancel="()=>columnPlusVisible = false"
      >
        <ConfigFormPlus :tableName="tableName" :columnPlusVisible="columnPlusVisible" ref="configPlusFormRef"/>
      </a-modal>
    </a-card>
    <BackTop :visibilityHeight="200"/>
  </div>
</template>

<script>
import { message, BackTop } from 'ant-design-vue'
import { tableColumn } from '@/utils/columns'
import SearchForm from '@/views/list/SearchForm'
import ConfigFormEdit from '@/views/list/ConfigFormEdit'
import ConfigFormPlus from '@/views/list/ConfigFormPlus'
import { TableInfos, TableDetailInfo, CreateTable } from '@/api/database'
export default {
  name: 'StandardList',
  components: { ConfigFormPlus, ConfigFormEdit, SearchForm, BackTop },
  data () {
    return {
      clientWidth: document.body.clientWidth,
      tableColumn,
      columnEditVisible: false,
      columnPlusVisible: false,
      selectedRowKeys: [],
      dataSource: ['博客系统', '人力资源系统'],
      databases: [],
      tableDetailData: {},
      tableName: '',
      data: []
    }
  },
  mounted () {
    this.allTables()
    const that = this
    window.onresize = () => {
      return (() => {
        window.clientWidth = document.body.clientWidth
        that.clientWidth = window.clientWidth
      })()
    }
  },
  watch: {
    clientWidth (newVal, oldVal) {
      console.log(newVal)// 浏览器窗口变化时，打印宽度。
    }
  },
  computed: {
    hasToken () {
      return this.$store.getters.token
    },
    onlineTableData () {
      return this.$store.getters.onlineTableData
    }
  },
  methods: {
    allTables () {
      this.databases = this.$store.state.database.tables
    },
    async configSetting (record) {
      const dataSource = this.$store.state.database.dataSource
      const params = {
        config: dataSource,
        dbName: this.tableName,
        tableName: record.tableName
      }
      const { data: { result } } = await TableDetailInfo(params)
      result.remark = record.remark
      this.tableDetailData = result
      this.columnEditVisible = true
    },
    handleOk () {
      console.log(this.$refs.configEditFormRef.databaseTable)
    },
    async handlePlusOk () {
      if (this.$refs.configPlusFormRef.databaseTable.tableName === '') {
        message.error('表名不能为空')
      } else {
        const dataSource = this.$store.state.database.dataSource
        const tableData = this.$refs.configPlusFormRef.databaseTable
        const parmas = {
          config: dataSource,
          dbName: this.tableName,
          tableDetailData: JSON.stringify(tableData)
        }
        const { data: { result } } = await CreateTable(parmas)
        if (result) {
          message.success('新增成功')
          this.columnPlusVisible = false
        } else {
          message.error('该表结构目前已存在')
        }
      }
    },
    handlePlus () {
      this.tableName = this.onlineTableData.val
      if (!this.tableName || this.tableName.trim() === '') {
        message.warning('请选择数据库')
      } else {
        this.columnPlusVisible = true
      }
    },
    onSelectChange (keys) {
      this.selectedRowKeys = keys
    },
    async handleChangeDatabase (val) {
      this.tableName = val
      const dataSource = this.$store.state.database.dataSource
      const params = {
        config: dataSource,
        tableName: val
      }
      const { data: { result } } = await TableInfos(params)
      const tableData = {
        result,
        val
      }
      this.$store.dispatch('table/onlineTableData', tableData)
    }
  }
}
</script>

<style lang="less" scoped>
.online-container {
  width: 100%;
  height: 100vh;
  .data-container {
    height: 100%;
    margin-top: 10px;
    .operation {
      width: 430px;
      display: flex;
      justify-content: space-between;
      margin-bottom: 10px;
    }
  }
}
.button-style{
  border-radius: 50%;
}
::v-deep .ant-modal-body{
  height: calc(100vh - 55px - 53px)
}
</style>
