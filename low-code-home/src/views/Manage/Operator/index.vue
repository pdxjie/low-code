<template>
  <div>
    <a-card class="search-form">
      <a-form layout="inline" :form="searchForm">
        <a-form-item >
          <a-input v-model="searchForm.nickName" placeholder="请输入用户名昵称"/>
        </a-form-item>
        <a-form-item >
          <a-range-picker @change="onChange" />
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="searchData">搜索</a-button>
        </a-form-item>

        <a-form-item>
          <a-button @click="resetSearForm">重置</a-button>
        </a-form-item>
      </a-form>
    </a-card>
    <a-card>
      <a-table
        :rowKey="(record)=>record.id"
        :columns="userColumns"
        :loading="loading"
        :data-source="operatorData"
        :pagination="{ pageSize: clientWidth > 2000 ? 12:8 }"
      >
        <template slot="avatar" slot-scope="text,record">
          <img class="user-avatar" :src="record.avatar">
        </template>
      </a-table>
    </a-card>
  </div>
</template>

<script>
import { userColumns } from '@/utils/columns'
import { OperatorDatas } from '@/api/manage'
import moment from 'moment'
export default {
  name: 'Operator',
  data () {
    return {
      clientWidth: document.body.clientWidth,
      userColumns,
      operatorData: [],
      searchForm: {
        nickName: '',
        beginTime: '',
        endTime: ''
      },
      loading: false
    }
  },
  mounted () {
    this.searchData()
    const that = this
    window.onresize = () => {
      return (() => {
        window.clientWidth = document.body.clientWidth
        that.clientWidth = window.clientWidth
      })()
    }
  },
  methods: {
    onChange (date, dateString) {
      this.searchForm.beginTime = dateString[0]
      this.searchForm.endTime = dateString[1]
    },
    async searchData () {
      this.loading = true
      const { data: { users } } = await OperatorDatas(this.searchForm)
      users.forEach(item => {
        item.registerTime = moment(item.registerTime).format('YYYY-MM-DD')
      })
      this.operatorData = users
      this.loading = false
    },
    resetSearForm () {
      this.searchForm = {}
      this.searchData()
    }
  }
}
</script>

<style lang='less' scoped>
.search-form {
  margin-bottom: 10px;
}
.user-avatar {
  height: 50px;
  width: 50px;
  border-radius: 5px;
}
</style>
