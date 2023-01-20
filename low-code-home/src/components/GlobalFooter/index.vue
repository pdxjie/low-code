<template>
  <global-footer class="footer custom-render">
    <template v-slot:links>
      <a-tooltip placement="top">
        <template slot="title">
          <span>欢迎提出建议和 Bug 反馈</span>
        </template>
        <a-icon type="bug" />
        <a href="https://github.com/pdxjie/low-code/issues" class="anthor">产品反馈</a>
      </a-tooltip>

      <a-tooltip placement="top">
        <template slot="title">
          <span>
            <img :src="wechatImgUrl" class="weichat" alt="">
          </span>
        </template>
        <a-icon type="message" />
        <span class="anthor">联系作者</span>
      </a-tooltip>

      <a-tooltip placement="top">
        <template slot="title">
          <span> 查看本站源码，欢迎 star 🌟</span>
        </template>
        <a-icon type="github" />
        <a href="https://github.com/pdxjie/low-code" class="anthor">支持项目</a>
      </a-tooltip>
    </template>
    <template v-slot:copyright>
      <a-icon type="copyright" />
      <a class="anthor">
        2022 Create By PDX
      </a>
    </template>
  </global-footer>
</template>

<script>
import { GlobalFooter } from '@ant-design-vue/pro-layout'

export default {
  name: 'ProGlobalFooter',
  components: {
    GlobalFooter
  },
  data () {
    return {
      wechatImgUrl: require('@/assets/wechat/wechat.jpg')
    }
  }
}
</script>

<style scoped>
.anthor {
  margin-left: 5px;
  margin-right: 20px;
  color: #000;
}
.weichat {
  width: 150px;
  height: 150px;
}
</style>
