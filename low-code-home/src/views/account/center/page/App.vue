<template>
  <div class="app-list">
    <a-card class="plus-datasource" >
      <a-form layout="inline" :form="searchForm">
        <a-form-item>
          <a-button @click="addDataSourceInfo" type="primary" icon="plus">新增数据源</a-button>
        </a-form-item>
        <a-form-item >
          <a-input v-model="searchForm.sourceName" placeholder="输入数据源名称">
          </a-input>
        </a-form-item>
        <a-form-item >
          <a-button type="primary" style="margin-right: 10px" @click="searchDataSource">搜索</a-button>
          <a-button @click="resetDataSource">重置</a-button>
        </a-form-item>
      </a-form>
    </a-card>
    <a-list
      :grid="{ gutter: 24, lg: 3, md: 2, sm: 1, xs: 1 }"
      :pagination="pagination"
      :loading="loading"
      :dataSource="dataSource">
      <a-list-item slot="renderItem" slot-scope="item">
        <a-card :hoverable="true">
          <a-card-meta>
            <div style="margin-bottom: 3px" slot="title">{{ item.sourceName }}</div>
            <a-avatar class="card-avatar" slot="avatar" :src="item.sourceCover" size="small"/>
            <div class="meta-cardInfo" slot="description">
              <div>
                <p>连接次数</p>
                <p>
                  <span>{{ item.connectNum }}</span>
                </p>
              </div>
              <div>
                <p>数据库数量</p>
                <p>{{ item.databaseNum | NumberFormat }}</p>
              </div>
            </div>
          </a-card-meta>
          <template class="ant-card-actions" slot="actions">
            <a>
              <a-icon type="edit" @click="editDataSourceInfo(item.id)"/>
            </a>
            <a>
              <a-tooltip placement="top">
                <template slot="title">
                  <span>测试连接</span>
                </template>
                <i class="iconfont icon-lianjie_" @click="checkOutConnect(item.id)"></i>
              </a-tooltip>
            </a>
            <a>
              <a-popconfirm
                :title="`确定要删除${item.sourceName}数据源吗?`"
                ok-text="确定"
                cancel-text="取消"
                @confirm="confirm(item.id)"
                @cancel="cancel"
              >
                <a-icon type="delete" style="color: #f37878"/>
              </a-popconfirm>
            </a>
          </template>
        </a-card>
      </a-list-item>
    </a-list>
    <!-- 修改数据源 -->
    <a-modal v-model="editDataSourceVisible" title="修改数据源配置" @ok="openDataSourceConfigInfo">
      <EditDataSourceConfigInfo :editDataSourceVisible="editDataSourceVisible" :dataSourceInfo="dataSourceInfo" ref="editDataSourceInfoRef"/>
    </a-modal>

    <!-- 添加数据源 -->
    <a-modal v-model="addDataSourceVisible" title="添加数据源配置" @ok="openAddDataSourceConfigInfo">
      <AddDataSourceConfigInfo :addDataSourceVisible="addDataSourceVisible" ref="addDataSourceConfigInfo"/>
    </a-modal>
  </div>
</template>

<script>
import { message } from 'ant-design-vue'
import EditDataSourceConfigInfo from '@/views/account/center/EditDataSourceConfigInfo'
import AddDataSourceConfigInfo from '@/views/account/center/AddDataSourceConfigInfo'
import {
  addDataSourceConfigInfo, connectDataSource, DataSourceDetailInfo,
  editDataSourceConfigInfo,
  listByCondition,
  removeDataSourceInfo
} from '@/api/datasource'
export default {
  name: 'Article',
  components: { AddDataSourceConfigInfo, EditDataSourceConfigInfo },
  data () {
    return {
      searchForm: {
        sourceName: ''
      },
      loading: false,
      pagination: {
        onChange: page => {
          this.searchDataSource(page)
        },
        pageSize: 9
      },
      dataSource: [],
      editDataSourceVisible: false,
      addDataSourceVisible: false,
      dataSourceInfo: {}
    }
  },
  mounted () {
    this.searchDataSource()
  },
  methods: {
    async searchDataSource (page) {
      this.loading = true
      if (!page) {
        page = 1
      }
      const userId = this.$store.getters.userInfo.userId
      const parameter = {
        pageSize: this.pagination.pageSize,
        page: page,
        sourceName: this.searchForm.sourceName,
        userId: userId
      }
      const { data } = await listByCondition(parameter)
      this.dataSource = data.records
      this.loading = false
    },
    resetDataSource () {
      this.searchForm = {}
      this.searchDataSource()
    },
    async checkOutConnect (id) {
      const { data } = await connectDataSource(id)
      if (data.result) {
        message.success('连接成功')
      } else {
        message.error('连接失败')
      }
      this.searchDataSource()
    },
    async editDataSourceInfo (id) {
      const { data } = await DataSourceDetailInfo(id)
      this.dataSourceInfo = data.dataSource
      this.editDataSourceVisible = true
    },
    async openDataSourceConfigInfo () {
      const dataSource = this.$refs.editDataSourceInfoRef.dataSource
      const { data } = await editDataSourceConfigInfo(dataSource)
      if (data.isSuccess) {
        message.success('更新成功')
      } else {
        message.error('更新失败')
      }
      this.searchDataSource()
      this.editDataSourceVisible = false
    },
    addDataSourceInfo () {
      this.addDataSourceVisible = true
    },
    async openAddDataSourceConfigInfo () {
      const parameter = {
        datasource: this.$refs.addDataSourceConfigInfo.datasourceInfo,
        userId: this.$store.getters.userInfo.userId
      }
      const { data } = await addDataSourceConfigInfo(parameter)
      if (data.isSuccess) {
        message.success('新增成功')
        this.searchDataSource()
        this.addDataSourceVisible = false
      }
    },
    async confirm (id) {
      const { data } = await removeDataSourceInfo(id)
      if (data.isRemove) {
        message.success('删除成功')
        this.searchDataSource()
      } else {
        message.error('删除失败')
      }
    },
    cancel () {
      message.info('取消删除')
    }
  }
}
</script>

<style lang="less" scoped>

  .app-list {
    .plus-datasource{
      display: flex;
      justify-content: space-between;
      width: 100%;
      margin-bottom: 10px;
    }

    .meta-cardInfo {
      zoom: 1;
      margin-top: 16px;

      > div {
        position: relative;
        text-align: left;
        float: left;
        width: 50%;

        p {
          line-height: 32px;
          font-size: 24px;
          margin: 0;

          &:first-child {
            color: rgba(0, 0, 0, .45);
            font-size: 12px;
            line-height: 20px;
            margin-bottom: 4px;
          }
        }

      }
    }
    ::v-deep .ant-avatar-sm {
      width: 40px;
      height: 40px;
      border-radius: 5px;
    }
  }

</style>
