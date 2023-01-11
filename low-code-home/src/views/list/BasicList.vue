<template>
  <div class="online-container">
    <!--    <a-card>-->
    <!--      <SearchForm/>-->
    <!--    </a-card>-->
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
        <a-select allowClear default-value="请选择数据库" style="width: 130px" @change="handleChangeDatabase">
          <a-select-option v-for="(item,index) in databases" :key="index" :value="item">
            {{ item }}
          </a-select-option>
        </a-select>
      </div>
      <a-table
        :pagination="{ pageSize: 6 }"
        :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
        :columns="tableColumn"
        :data-source="data"
      >
        <template slot="status" slot-scope="text,record">
          <span v-if="record.status === 1" style="color: #35c035">已更新</span>
          <span v-else style="color: red">未更新</span>
        </template>
        <template slot="action">
          <a-tooltip placement="topLeft">
            <template slot="title">
              <span>更新</span>
            </template>
            <a-button class="button-style" @click="configSetting" type="primary" icon="edit"/>
          </a-tooltip>
        </template>
      </a-table>
      <a-modal
        title="编辑"
        closable
        :style="{maxWidth:'100vw',paddingBottom:'0'}"
        width="100vw"
        :visible="columnEditVisible"
        :dialog-style="{ top: '0px'}"
        @ok="handleOk"
        @cancel="()=>columnEditVisible = false"
      >
        <ConfigFormEdit ref="configEditFormRef"/>
      </a-modal>

      <a-modal
        title="编辑"
        closable
        :style="{maxWidth:'100vw',paddingBottom:'0'}"
        width="100vw"
        :visible="columnPlusVisible"
        :dialog-style="{ top: '0px'}"
        @ok="handlePlusOk"
        @cancel="()=>columnPlusVisible = false"
      >
        <ConfigFormPlus :tableName="tableName" :columnPlusVisible="columnPlusVisible" ref="configPlusFormRef"/>
      </a-modal>
    </a-card>
  </div>
</template>

<script>
import { message } from 'ant-design-vue'
import { tableColumn } from '@/utils/columns'
import SearchForm from '@/views/list/SearchForm'
import ConfigFormEdit from '@/views/list/ConfigFormEdit'
import ConfigFormPlus from '@/views/list/ConfigFormPlus'
import { TableInfos } from '@/api/database'
export default {
  name: 'StandardList',
  components: { ConfigFormPlus, ConfigFormEdit, SearchForm },
  data () {
    return {
      tableColumn,
      columnEditVisible: false,
      columnPlusVisible: false,
      selectedRowKeys: [],
      dataSource: ['博客系统', '人力资源系统'],
      databases: [],
      tableName: '',
      data: []
    }
  },
  mounted () {
    this.allTables()
  },
  computed: {
    hasToken () {
      return this.$store.getters.token
    }
  },
  methods: {
    allTables () {
      this.databases = JSON.parse(this.$store.state.database.tables)
    },
    configSetting () {
      this.columnEditVisible = true
    },
    handleOk () {
      console.log(this.$refs.configEditFormRef.databaseTable)
    },
    handlePlusOk () {
      this.columnPlusVisible = false
    },
    handlePlus () {
      if (this.tableName.trim() === '') {
        message.warning('请选择数据库')
      } else {
        this.columnPlusVisible = true
      }
    },
    onSelectChange (keys) {
      this.selectedRowKeys = keys
    },
    async handleChangeDatabase (val) {
      console.log(val)
      const dataSource = this.$store.state.database.dataSource
      const params = {
        config: dataSource,
        tableName: val
      }
      const { data: { result } } = await TableInfos(params)
      this.data = result
    }
  }
}
</script>

<style lang="less" scoped>
.online-container {
  width: 100%;
  height: 100vh;
  .data-container {
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
