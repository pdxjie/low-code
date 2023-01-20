<template>
  <div class="main user-layout-register">
    <a-form ref="formRegister" :form="form" id="formRegister">

      <a-tabs
        :activeKey="customActiveKey"
        :tabBarStyle="{ textAlign: 'center', borderBottom: 'unset' }"
        @change="handleTabClick"
      >
        <a-tab-pane key="tab1">
          <span slot="tab">
            <a-icon type="user-add" />
            用户注册
          </span>
          <a-form-item>
            <a-input
              size="large"
              type="text"
              placeholder="请输入QQ邮箱"
              v-decorator="['email', {rules: [{ required: true, type: 'email', message: '邮箱内容不能为空' }], validateTrigger: ['change', 'blur']}]"
            >
              <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input-password
              size="large"
              @click="handlePasswordInputClick"
              placeholder="请输入用户密码"
              v-decorator="['password', {rules: [{ required: true, message: '用户密码不能为空' }, { validator: this.handlePasswordLevel }], validateTrigger: ['change', 'blur']}]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input-password>
          </a-form-item>

          <a-form-item>
            <a-input-password
              size="large"
              placeholder="确认用户密码"
              v-decorator="['password2', {rules: [{ required: true, message: '确认用户密码不能为空' }, { validator: this.handlePasswordCheck }], validateTrigger: ['change', 'blur']}]"
            >
              <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input-password>
          </a-form-item>

          <a-row :gutter="16">
            <a-col class="gutter-row" :span="16">
              <a-form-item>
                <a-input size="large" type="text" placeholder="请输入验证码" v-decorator="['captcha', {rules: [{ required: true, message: '请输入验证码' }], validateTrigger: 'blur'}]">
                  <a-icon slot="prefix" type="code" :style="{ color: 'rgba(0,0,0,.25)' }"/>
                </a-input>
              </a-form-item>
            </a-col>
            <a-col class="gutter-row" :span="8">
              <a-button
                class="getCaptcha"
                size="large"
                :disabled="state.smsSendBtn"
                @click="getCaptcha"
                v-text="!state.smsSendBtn && '发送验证码'||(state.time+' s')"></a-button>
            </a-col>
          </a-row>
        </a-tab-pane>
      </a-tabs>

      <a-form-item>
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="register-button"
          :loading="registerBtn"
          @click.stop.prevent="handleSubmit"
          :disabled="registerBtn">{{ '注册' }}
        </a-button>
        <router-link class="login" :to="{ name: 'login' }">已有账户</router-link>
      </a-form-item>

      </a-tabs></a-form>
  </div>
</template>

<script>
import { getSmsCaptcha } from '@/api/login'
import { deviceMixin } from '@/store/device-mixin'
import { scorePassword } from '@/utils/util'
import { message } from 'ant-design-vue'
import { ACCESS_TOKEN } from '@/store/mutation-types'
const levelNames = {
  0: 'user.password.strength.short',
  1: 'user.password.strength.low',
  2: 'user.password.strength.medium',
  3: 'user.password.strength.strong'
}
const levelClass = {
  0: 'error',
  1: 'error',
  2: 'warning',
  3: 'success'
}
const levelColor = {
  0: '#ff0000',
  1: '#ff0000',
  2: '#ff7e05',
  3: '#52c41a'
}
export default {
  name: 'Register',
  components: {
  },
  mixins: [deviceMixin],
  data () {
    return {
      customActiveKey: 'tab1',
      form: this.$form.createForm(this),
      state: {
        time: 60,
        level: 0,
        smsSendBtn: false,
        passwordLevel: 0,
        passwordLevelChecked: false,
        percent: 10,
        progressColor: '#FF0000'
      },
      registerBtn: false
    }
  },
  computed: {
    passwordLevelClass () {
      return levelClass[this.state.passwordLevel]
    },
    passwordLevelName () {
      return levelNames[this.state.passwordLevel]
    },
    passwordLevelColor () {
      return levelColor[this.state.passwordLevel]
    }
  },
  methods: {
    handleTabClick (key) {
      this.customActiveKey = key
      // this.form.resetFields()
    },
    handlePasswordLevel (rule, value, callback) {
      if (!value) {
        return callback()
      }
      if (value.length >= 6) {
        if (scorePassword(value) >= 30) {
          this.state.level = 1
        }
        if (scorePassword(value) >= 60) {
          this.state.level = 2
        }
        if (scorePassword(value) >= 80) {
          this.state.level = 3
        }
      } else {
        this.state.level = 0
        callback(new Error(this.$t('user.password.strength.msg')))
      }
      this.state.passwordLevel = this.state.level
      this.state.percent = this.state.level * 33
      callback()
    },
    handlePasswordCheck (rule, value, callback) {
      const password = this.form.getFieldValue('password')
      if (value === undefined) {
        callback(new Error(this.$t('user.password.required')))
      }
      if (value && password && value.trim() !== password.trim()) {
        callback(new Error(this.$t('user.password.twice.msg')))
      }
      callback()
    },
    handlePasswordInputClick () {
      if (!this.isMobile) {
        this.state.passwordLevelChecked = true
        return
      }
      this.state.passwordLevelChecked = false
    },
    handleSubmit () {
      const { form: { validateFields }, state, $router } = this
      validateFields({ force: true }, (err, values) => {
        if (!err) {
          const registerInfo = {
            email: values.email,
            password: values.password,
            code: values.captcha
          }
          this.$store.dispatch('RegisterOperate', registerInfo).then(res => {
            if (res.data !== {} && res.data.isSuccess) {
              localStorage.setItem(ACCESS_TOKEN, res.data.accessToken)
              message.success('注册成功')
              state.passwordLevelChecked = false
              $router.push({ name: 'Workplace' })
            } else {
              console.log(res)
              message.error('该邮箱已被注册')
            }
          })
        }
      })
    },
    getCaptcha (e) {
      const { form: { validateFields }, state, $message, $notification } = this
      validateFields(['email'], { force: true },
        (err, values) => {
          if (!err) {
            state.smsSendBtn = true
            console.log(values.email)
            // 校验QQ邮箱
            if (values.email.trim() === '') {
              message.error('注册邮箱不能为空')
              return
            }
            console.log(values.email)
            const qEmail = values.email.split('\@')
            const regex = /^[1-9][0-9]{4,10}$/
            if (!regex.test(qEmail[0]) && values.email.indexOf('@qq') === -1) {
              message.error('QQ号码或邮箱格式异常')
              state.loginBtn = false
              return
            }
            const interval = window.setInterval(() => {
              if (state.time-- <= 0) {
                state.time = 60
                state.smsSendBtn = false
                window.clearInterval(interval)
              }
            }, 1000)
            const hide = $message.loading('验证码发送中..', 0)
            getSmsCaptcha({ email: values.email }).then(res => {
              console.log('结果', res)
              setTimeout(hide, 2500)
              $notification['success']({
                message: '提示',
                description: '验证码获取成功，您的验证码为：' + res.data.code,
                duration: 8
              })
            }).catch(err => {
              setTimeout(hide, 1)
              clearInterval(interval)
              state.time = 60
              state.smsSendBtn = false
              this.requestFailed(err)
            })
          }
        }
      )
    },
    requestFailed (err) {
      this.$notification['error']({
        message: '错误',
        description: ((err.response || {}).data || {}).message || '请求出现错误，请稍后再试',
        duration: 4
      })
      this.registerBtn = false
    }
  },
  watch: {
    'state.passwordLevel' (val) {
      console.log(val)
    }
  }
}
</script>
<style lang="less">
.user-register {
  &.error {
    color: #ff0000;
  }
  &.warning {
    color: #ff7e05;
  }
  &.success {
    color: #52c41a;
  }
}
.user-layout-register {
  .ant-input-group-addon:first-child {
    background-color: #fff;
  }
}
</style>
<style lang="less" scoped>
.user-layout-register {
  height: calc(100vh - 174px);
  & > h3 {
    font-size: 16px;
    margin-bottom: 20px;
  }
  .getCaptcha {
    display: block;
    width: 100%;
    height: 40px;
  }
  .register-button {
    width: 50%;
  }
  .login {
    float: right;
    line-height: 40px;
  }
}
</style>
