<template>
  <div class="online-container">
    <a-card>
      <SearchForm/>
    </a-card>
    <a-card class="data-container">
      <div class="operation">
        <a-button icon="plus" type="primary">新增</a-button>
        <a-select default-value="请选择数据库" style="width: 120px" @change="handleChangeDatabase">
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
        :visible="columnVisible"
        :dialog-style="{ top: '0px'}"
        @ok="handleOk"
        @cancel="()=>columnVisible = false"
      >
        <ConfigForm ref="configFormRef"/>
      </a-modal>
    </a-card>
  </div>
</template>

<script>
import { tableColumn } from '@/utils/columns'
import SearchForm from '@/views/list/SearchForm'
import ConfigForm from '@/views/list/ConfigForm'
export default {
  name: 'StandardList',
  components: { ConfigForm, SearchForm },
  data () {
    return {
      tableColumn,
      columnVisible: false,
      selectedRowKeys: [],
      databases: ['book', 'blog', 'jeecg', 'ihrm'],
      data: [
        {
          tableName: 'user',
          desc: '用户表',
          version: 1,
          status: 1
        }
      ]
    }
  },
  methods: {
    configSetting () {
      this.columnVisible = true
    },
    handleOk () {
      console.log(this.$refs.configFormRef.databaseTable)
    },
    onSelectChange (keys) {
      this.selectedRowKeys = keys
    },
    handleChangeDatabase () {

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
      width: 230px;
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
