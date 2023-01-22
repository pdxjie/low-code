<template>
  <a-result status="403" title="403" sub-title="权限不足，请返回首页继续使用">
    <template #extra>
      <a-button type="primary" @click="toHome">
        回到首页
      </a-button>
    </template>
  </a-result>
</template>

<script>
export default {
  name: 'Exception403',
  methods: {
    toHome () {
      this.$router.push({ path: '/' })
    }
  }
}
</script>
