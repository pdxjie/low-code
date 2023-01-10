<template>
  <div>
    <a-card>
      <a-form layout="inline" :form="databaseTable">
        <div class="form-item">
          <a-form-item label="表名">
            <a-input v-model="databaseTable.tableName" placeholder="请输入表名"/>
          </a-form-item>
          <a-form-item label="表描述">
            <a-input v-model="databaseTable.desc" placeholder="请输入表描述" />
          </a-form-item>
          <a-form-item label="主题模板">
            <a-input disabled placeholder="默认主题"/>
          </a-form-item>
        </div>
      </a-form>

      <a-tabs default-active-key="1">
        <a-tab-pane key="1" tab="数据库配置">
          <a-button style="margin-bottom: 10px" icon="plus" type="primary" @click="handleAdd">新增</a-button>
          <a-table
            :row-selection="{ selectedRowKeys: selectedRowKeys, onChange: onSelectChange }"
            :rowKey="(record)=>record.field"
            :pagination="{ pageSize: 5 }"
            :scroll="{ y: 440 }"
            :columns="databaseColumns"
            :data-source="databaseTable.columnsData"
          >
            <template slot="field" slot-scope="text,record">
              <a-input placeholder="字段名称" v-model="record.field"></a-input>
            </template>
            <template slot="fieldComment" slot-scope="text,record">
              <a-input placeholder="字段备注" v-model="record.fieldComment"/>
            </template>
            <template slot="fieldLength" slot-scope="text,record">
              <a-input-number placeholder="字段备注" v-model="record.fieldLength"/>
            </template>
            <template slot="fieldType" slot-scope="text,record">
              <a-select
                placeholder="字段类型"
                style="width: 200px"
                @change="handleChange($event,record)"
              >
                <a-select-option v-for="(item,index) in fieldType" :key="index" :value="item">
                  {{ item }}
                </a-select-option>
              </a-select>
            </template>
            <template slot="primaryKey" slot-scope="text,record">
              <a-checkbox @change="onChangePrimaryKey($event,record)"/>
            </template>
            <template slot="isNull" slot-scope="text,record">
              <a-checkbox @change="onChangeIsNull($event,record)"/>
            </template>
          </a-table>
        </a-tab-pane>
      </a-tabs>
    </a-card>
  </div>
</template>
<script>
import { databaseColumns } from '@/utils/columns'
export default {
  name: 'ConfigForm',
  data () {
    return {
      databaseColumns,
      selectedRowKeys: [],
      databaseTable: {
        columnsData: []
      },
      fieldType: ['VARCHAR', 'BIGINT', 'INT', 'TINYINT', 'DATETIME', 'TIMESTAMP', 'DOUBLE', 'TEXT', 'CHAR']
    }
  },
  methods: {
    handleChange (value, record) {
      record.fieldType = value
      console.log(value)
    },
    onChangePrimaryKey (e, record) {
      record.primaryKey = e.target.checked
      console.log('primaryKey', e.target.checked)
    },
    onChangeIsNull (e, record) {
      record.isNull = e.target.checked
      console.log('isNull', e.target.checked)
    },
    handleAdd () {
      const newOne = {
        field: '',
        fieldComment: '',
        fieldLength: 0,
        fieldType: '',
        primaryKey: false,
        isNull: false
      }
      this.databaseTable.columnsData = [...this.databaseTable.columnsData, newOne]
    },
    onSelectChange (keys) {
      this.selectedRowKeys = keys
    }
  }
}
</script>
<style scoped>
  .form-item {
    display: flex;
    justify-content: space-around;
  }
</style>
