<template>
  <div>
    <a-card title="基础按钮" class="button-wrap">
      <a-button type="primary">默认按钮</a-button>
      <a-button>普通按钮</a-button>
      <a-button type="dashed">虚线按钮</a-button>
      <a-button type="danger" ghost>警告按钮</a-button>
      <a-button type="primary" disabled>禁用按钮</a-button>
    </a-card>

    <a-card title="图形按钮" class="button-wrap">
      <a-button icon="plus">创建</a-button>
      <a-button icon="edit">编辑</a-button>
      <a-button icon="search">搜索</a-button>
      <a-button type="danger" ghost icon="delete">删除</a-button>
      <a-button type="primary" icon="upload">上传</a-button>
      <a-button type="primary" icon="download">下载</a-button>
      <a-button class="circle" shape="circle" icon="search" />
    </a-card>

    <a-card title="Loading按钮" class="button-wrap">
      <a-button type="primary" loading>确定</a-button>
      <a-button class="circle" shape="circle" loading />
      <a-button type="primary" :loading="loading" @mouseenter="enterLoading">鼠标划过</a-button>
      <a-button type="primary" :loading="iconLoading" @click="enterIconLoading">
        延迟1s
      </a-button>
    </a-card>

    <a-card title="按钮组">
      <a-button-group>
        <a-button type="primary"> <a-icon type="left" />后退 </a-button>
        <a-button type="primary"> 前进<a-icon type="right" /> </a-button>
      </a-button-group>
    </a-card>

    <a-card title="按钮尺寸" class="button-wrap">
      <a-radio-group name="radioGroup" :value="size" @change="handleSizeChange">
        <a-radio value="large">大</a-radio>
        <a-radio value="default">中</a-radio>
        <a-radio value="small">小</a-radio>
      </a-radio-group>
      <a-button type="primary" :size="size">Primary</a-button>
      <a-button :size="size">Normal</a-button>
      <a-button type="dashed" :size="size">Dashed</a-button>
      <a-button type="danger" :size="size">Danger</a-button>
    </a-card>
  </div>
</template>

<script>
export default {
  name: 'Button',
  data () {
    return {
      loading: false,
      iconLoading: false,
      size: 'large'
    }
  },
  methods: {
    enterLoading () {
      this.loading = true
    },
    enterIconLoading () {
      this.iconLoading = { delay: 1000 }
    },
    handleSizeChange (e) {
      this.size = e.target.value
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
