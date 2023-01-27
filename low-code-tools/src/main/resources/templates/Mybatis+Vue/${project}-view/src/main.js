import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './styles/common.css'
import './plugin/antd-components'
import '@/assets/fonts/iconfont.css'
import axios from 'axios'
import * as echarts from 'echarts' // 引入echarts
Vue.prototype.$echarts = echarts
Vue.config.productionTip = false

Vue.prototype.$axios = axios

const baseApi = process.env.VUE_APP_BASE_API

const instance = axios.create({
  baseURL: baseApi,
  timeout: 5000
})

// 请求拦截器
instance.interceptors.request.use(config => {
  store.dispatch('system/setloadding', true)
  return config
}, error => {
  return Promise.reject(error.message)
})

// 响应拦截器
instance.interceptors.response.use(res => {
  store.dispatch('system/setloadding', false)
  if (res.status === 200) {
    return res
  }
}, error => {
  return Promise.reject(error.message)
})


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
