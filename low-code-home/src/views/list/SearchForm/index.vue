<template>
  <div>
    <a-form layout="inline">
      <a-form-item
        label="表名"
      >
        <a-input placeholder="请输入表名" />
      </a-form-item>
      <a-form-item
        label="表描述"
      >
        <a-input placeholder="请输入表描述" />
      </a-form-item>
      <a-form-item>
        <a-button type="primary">查询</a-button>
      </a-form-item>
      <a-form-item >
        <a-button >重置</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
export default {
  name: 'SearchForm'
}
</script>

<style lang='less' scoped>

::v-deep .ant-form-inline .ant-form-item {

}
</style>
