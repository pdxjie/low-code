import storage from 'store'
import expirePlugin from 'store/plugins/expire'
import { login, getInfo, Register } from '@/api/login'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { welcome } from '@/utils/util'

storage.addPlugin(expirePlugin)
const user = {
  state: {
    token: '',
    name: '',
    welcome: '',
    avatar: '',
    roles: [],
    info: {}
  },

  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_NAME: (state, { name, welcome }) => {
      state.name = name
      state.welcome = welcome
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_INFO: (state, info) => {
      state.info = info
    }
  },

  actions: {
    // 登录
    Login ({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        login(userInfo).then(response => {
          const result = response.data
          // storage.set(ACCESS_TOKEN, result.token, new Date().getTime() + 10 * 24 * 60 * 60 * 1000)
          console.log(result.accessToken)
          localStorage.setItem(ACCESS_TOKEN, result.accessToken)
          commit('SET_TOKEN', result.token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo ({ commit }) {
      return new Promise((resolve, reject) => {
        // 请求后端获取用户信息 /api/user/info
        const token = localStorage.getItem(ACCESS_TOKEN)
        getInfo(token).then(response => {
          const { data } = response
          commit('SET_NAME', { name: data.userDetail.nickName, welcome: welcome() })
          commit('SET_AVATAR', data.userDetail.avatar)
          commit('SET_INFO', data.userDetail)
            resolve(data.userDetail)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    Logout ({ commit }) {
      commit('SET_TOKEN', '')
      localStorage.removeItem(ACCESS_TOKEN)
      // TODO清除缓存数据
    },

    // 注册
    RegisterOperate ({ commit }, data) {
      return new Promise((resolve, reject) => {
        Register(data).then(res => {
          resolve(res)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default user
