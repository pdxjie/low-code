<template>
  <div>
    <a-form :form="dataSourceConfig" :label-col="{ span: 5 }" :wrapper-col="{ span: 12 }">
      <a-form-item label="数据库类型">
        <a-select default-value="MySQL" disabled></a-select>
      </a-form-item>
      <a-form-item label="IP">
        <a-input
          v-model="dataSourceConfig.ip"
          placeholder="【默认】0.0.0.0"/>
      </a-form-item>
      <a-form-item label="端口号">
        <a-input v-model="dataSourceConfig.port" placeholder="【默认】3306"/>
      </a-form-item>
      <a-form-item label="用户名">
        <a-input v-model="dataSourceConfig.loginName" placeholder="【默认】root"/>
      </a-form-item>
      <a-form-item label="密码">
        <a-input-password v-model="dataSourceConfig.password" placeholder="请输入密码"/>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
export default {
  name: 'ConfigDataSource',
  data () {
    return {
      dataSourceConfig: {
        ip: '127.0.0.1',
        port: '3306',
        loginName: 'root',
        password: '233031'
      }
    }
  }
}
</script>

<style scoped>

</style>
