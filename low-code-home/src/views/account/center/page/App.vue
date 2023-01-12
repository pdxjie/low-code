<template>
  <div class="app-list">
    <a-list
      :grid="{ gutter: 24, lg: 3, md: 2, sm: 1, xs: 1 }"
      :dataSource="dataSource">
      <a-list-item slot="renderItem" slot-scope="item">
        <a-card :hoverable="true">
          <a-card-meta>
            <div style="margin-bottom: 3px" slot="title">{{ item.title }}</div>
            <a-avatar class="card-avatar" slot="avatar" :src="item.avatar" size="small"/>
            <div class="meta-cardInfo" slot="description">
              <div>
                <p>连接次数</p>
                <p>
                  <span>{{ item.activeUser }}</span>
                </p>
              </div>
              <div>
                <p>数据库数量</p>
                <p>{{ item.newUser | NumberFormat }}</p>
              </div>
            </div>
          </a-card-meta>
          <template class="ant-card-actions" slot="actions">
            <a>
              <a-icon type="edit" @click="editDataSourceInfo"/>
            </a>
            <a>
              <i class="iconfont icon-lianjie_" @click="checkOutConnect"></i>
            </a>
          </template>
        </a-card>
      </a-list-item>
    </a-list>
    <a-modal v-model="editDataSourceVisible" title="修改数据源信息" @ok="openDataSourceConfigInfo">
      <EditDataSourceConfigInfo/>
    </a-modal>
  </div>
</template>

<script>
import { message } from 'ant-design-vue'
import EditDataSourceConfigInfo from '@/views/account/center/EditDataSourceConfigInfo'
export default {
  name: 'Article',
  components: { EditDataSourceConfigInfo },
  data () {
    return {
      dataSource: [
        {
          title: '人力资源',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 71,
          newUser: 8
        },
        {
          title: '博客系统',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 62,
          newUser: 12
        },
        {
          title: '低代码',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 89,
          newUser: 23
        },
        {
          title: '图书管理系统',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 25,
          newUser: 11
        },
        {
          title: '进销货系统',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 11,
          newUser: 6
        },
        {
          title: 'RPM',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 45,
          newUser: 4
        },
        {
          title: 'CSDN',
          avatar: 'https://gw.alipayobjects.com/zos/rmsportal/WdGqmHpayyMjiEhcKoVE.png',
          activeUser: 22,
          newUser: 2
        }
      ],
      editDataSourceVisible: false
    }
  },
  methods: {
    checkOutConnect () {
      message.success('连接成功')
    },
    editDataSourceInfo () {
      this.editDataSourceVisible = true
    },
    openDataSourceConfigInfo () {
      this.editDataSourceVisible = false
    }
  }
}
</script>

<style lang="less" scoped>

  .app-list {

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
