<template>
  <div>
    <a-card title="基础Message" class="button-wrap">
      <a-button type="primary" @click="info">
        普通Message
      </a-button>
      <a-button type="primary" @click="success">
        自定义时长
      </a-button>
      <a-button @click="successLoading">
        Loading Message
      </a-button>
    </a-card>
    <a-card title="提示类型Message" class="button-wrap">
      <a-button type="primary" @click="successMessage">
        Success
      </a-button>
      <a-button type="primary" @click="error">
        Error
      </a-button>
      <a-button type="primary" @click="warning">
        Warning
      </a-button>
    </a-card>
  </div>
</template>

<script>
import { message } from 'ant-design-vue'
export default {
  name: 'Message',
  methods: {
    info () {
      message.info('这是一个普通的信息')
    },
    success () {
      message.success(
        '欢迎来到Bike Share',
        10
      )
    },
    successLoading () {
      const hide = message.loading('Action in progress..', 0)
      setTimeout(hide, 2500)
    },
    successMessage () {
      message.success('成功')
    },
    error () {
      message.error('错误')
    },
    warning () {
      message.warning('警告')
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
}
</style>
