<template>
  <div class="main">
    <a-form
      id="formLogin"
      class="user-layout-login"
      ref="formLogin"
    >
      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
      >
        <a-tab-pane key="tab1" >
          <span slot="tab">
            <a-icon type="wechat" />
            微信扫码登录
          </span>
          <a-form-item style="text-align: center">
            <a-spin v-show="!wechatLoginImgUrl" tip="奋力加载中...">
            </a-spin>
            <img v-show="wechatLoginImgUrl" style="height: 80%;width: 80%" :src="wechatLoginImgUrl" alt="">
          </a-form-item>
          <a-form-item style="text-align: center">
            <span>使用微信扫一扫登录 “代码生成器”</span>
          </a-form-item>
        </a-tab-pane>
      </a-tabs>
    </a-form>
  </div>
</template>

<script>
import { mapActions } from 'vuex'
import { timeFix } from '@/utils/util'

export default {
  data () {
    return {
      customActiveKey: 'tab1',
      wechatLoginImgUrl: ''
    }
  },
  created () {
    this.getQrCode()
    const timer = setTimeout(() => {
      this.loadCode = false
      clearTimeout(timer)
    }, 2000)

    // 轮询后台，看用户是否扫码成功
    this.loopFun()
  },
  methods: {
    ...mapActions(['Login', 'Logout']),
    // 获取二维码
    getQrCode () {
      const myid = parseInt(Math.random() * 100000)
      sessionStorage.setItem('myid', myid)
      const data = {
        expire_seconds: 604800,
        action_name: 'QR_SCENE',
        action_info: {
          scene: { scene_id: myid }
        }
      }
      this.$http.post('/getQrCode', data).then(res => {
        this.wechatLoginImgUrl = `https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=${res.ticket}`
      })
    },

    // 扫码登录操作
    sleep (dealy) {
      // eslint-disable-next-line promise/param-names
      return new Promise((res, _) => {
        const tm = setTimeout(() => {
          clearTimeout(tm)
          res()
        }, dealy)
      })
    },
    async loopFun () {
      // 根据本地存储的myid区分用户,在生成二维码的时候存储的，也传给后台了
      const myid = sessionStorage.getItem('myid') || 'a'
      const rel = await this.$http.get(`/Login?myid=${myid}`)
      // 如果还没登录，休眠3s再问一次
      if (!rel && !rel.data) {
        // 休眠3s
        await this.sleep(3000)
        this.loopFun()
      } else {
        // 如果扫码了，跳转页面
        this.$router.push({ path: '/account/center' })
        // TODO 设置用户token信息
        // 延迟 1 秒显示欢迎信息
        setTimeout(() => {
          this.$notification.success({
            message: '欢迎',
            description: `${timeFix()}，欢迎回来`
          })
        }, 1000)
      }
    }
  }
}
</script>

<style lang="less" scoped>
.user-layout-login {
  height: calc(100vh - 174px);
  label {
    font-size: 14px;
  }
  .wh100{
    width: 100%;
    height: 100%;
  }
  .box{
    border: none;
    overflow: hidden;
  }
  .qrCode{
    border: 1px solid #ddd;
    width: 430px;
    height: 430px;
    margin: 20px auto;
  }
}
</style>
