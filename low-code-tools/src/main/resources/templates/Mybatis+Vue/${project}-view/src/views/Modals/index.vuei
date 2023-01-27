<template>
  <div>
    <a-card title="基础模态框" class="button-wrap">
      <a-button type="primary" @click="()=>NormalVisible = true">
        Open
      </a-button>
      <a-modal v-model="NormalVisible" title="普通模态框" @ok="NormalVisible = false">
        <p>欢迎来到Bike Share</p>
      </a-modal>

      <a-button type="primary" @click="()=>CustomVisible = true">
        自定义页脚
      </a-button>
      <a-modal v-model="CustomVisible" title="自定义页脚模态框" on-ok="handleOk">
        <template slot="footer">
          <a-button key="back" @click="()=>CustomVisible = false">
            Return
          </a-button>
          <a-button key="submit" type="primary" @click="()=>CustomVisible = false">
            Submit
          </a-button>
        </template>
        <p>欢迎来到Bike Share</p>
      </a-modal>

      <a-button type="primary" @click="() => setModal1Visible(true)">
        顶部20px
      </a-button>
      <a-modal
        title="顶部20px模态框"
        :dialog-style="{ top: '20px' }"
        :visible="modal1Visible"
        @ok="() => setModal1Visible(false)"
        @cancel="() => setModal1Visible(false)"
      >
        <p>欢迎来到Bike Share</p>
      </a-modal>

      <a-button type="primary" @click="() => (modal2Visible = true)">
        水平垂直居中
      </a-button>
      <a-modal
        v-model="modal2Visible"
        title="水平居中模态框"
        centered
        @ok="() => (modal2Visible = false)"
      >
        <p>欢迎来到Bike Share</p>
      </a-modal>
    </a-card>

    <a-card title="信息确认框" class="button-wrap">
      <a-button type="primary" @click="info">Info</a-button>
      <a-button type="primary" @click="success">Success</a-button>
      <a-button type="primary" @click="error">Error</a-button>
      <a-button type="primary" @click="warning">Warning</a-button>
    </a-card>
  </div>
</template>

<script>
import { Modal } from 'ant-design-vue'
export default {
  name: 'Modals',
  data () {
    return {
      NormalVisible: false,
      CustomVisible: false,
      modal1Visible: false,
      modal2Visible: false
    }
  },
  methods: {
    setModal1Visible (modal1Visible) {
      this.modal1Visible = modal1Visible
    },
    info () {
      const h = this.$createElement
      Modal.info({
        title: '警告信息',
        content: h('div', {}, [
          h('p', '这是一个警告信息')
        ]),
        onOk () {}
      })
    },

    success () {
      Modal.success({
        title: '成功',
        content: (
          <div>
            <p>恭喜你，成功了</p>
          </div>
        )
      })
    },

    error () {
      Modal.error({
        title: '错误信息',
        content: '错误警告'
      })
    },

    warning () {
      Modal.warning({
        title: '警告',
        content: '警告信息'
      })
    }
  }
}
</script>

<style lang="less" scoped>
.button-wrap {
  margin-bottom: 10px;
  button {
    margin-right: 10px;
  }
  .circle {
    border-radius: 50% !important;
  }
}
</style>
