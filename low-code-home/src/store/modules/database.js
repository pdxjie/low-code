import { CheckoutConnectionApi, AllTables } from '@/api/database'
import { message } from 'ant-design-vue'
import { ACCESS_TOKEN } from '@/store/mutation-types'

/**
 * 根据不同的状态获取信息
 */
function getDataSourceByStatus () {
  if (localStorage.getItem(ACCESS_TOKEN)) {
    return JSON.parse(localStorage.getItem('dataSource')) || {}
  } else {
    return JSON.parse(sessionStorage.getItem('dataSource')) || {}
  }
}

/**
 * 根据不同的登录状态获取表数据
 * @returns {any|*[]}
 */
function getAllTables () {
  if (localStorage.getItem(ACCESS_TOKEN)) {
    return JSON.parse(localStorage.getItem('dataBases')) || []
  } else {
    return JSON.parse(sessionStorage.getItem('dataBases')) || []
  }
}

const state = {
  dataSource: getDataSourceByStatus(),
  tables: getAllTables()
}

const actions = {
  /**
   * 测试连接数据源
   * @param commit
   * @param data
   * @returns {Promise<void>}
   */
  async checkOutConnectResult ({ commit }, data) {
    const { result } = await CheckoutConnectionApi(data)
    if (result) {
      message.success('连接成功')
      // 未登录
      if (!localStorage.getItem(ACCESS_TOKEN)) {
        sessionStorage.setItem('dataSource', JSON.stringify(data))
      }
    } else {
      message.error('连接失败，请核查数据源信息')
    }
  },
  /**
   * 保存数据源连接
   * @param commit
   * @param params
   * @returns {Promise<boolean>}
   */
  async preservationConnect ({ commit }, params) {
    const data = await AllTables(params)
    if (data.success) {
      message.success('配置成功')
      commit('SET_DATABASEINFO', params)
      if (localStorage.getItem(ACCESS_TOKEN)) {
        // 存储数据源信息
        localStorage.setItem('dataSource', JSON.stringify(params))
        // 存储数据源中表数据
        localStorage.setItem('dataBases', JSON.stringify(data.data.result))
        commit('SET_DATABASETABLES', data.data.result)
      } else {
        // 同上
        sessionStorage.setItem('dataSource', JSON.stringify(params))
        sessionStorage.setItem('dataBases', JSON.stringify(data.data.result))
        commit('SET_DATABASETABLES', data.data.result)
      }
      return data.success
    } else {
      return data.success
    }
  },
  async allTableList ({ commit }, params) {
    const data = await AllTables(params)
    if (data.success) {
      commit('SET_DATABASEINFO', params)
      if (localStorage.getItem(ACCESS_TOKEN)) {
        // 存储数据源信息
        localStorage.setItem('dataSource', JSON.stringify(params))
        // 存储数据源中表数据
        localStorage.setItem('dataBases', JSON.stringify(data.data.result))
        commit('SET_DATABASETABLES', data.data.result)
      }
      return data.data.result
    } else {
      return data.success
    }
  }
}

const mutations = {
  SET_DATABASEINFO (state, databaseInfo) {
    state.dataSource = databaseInfo
  },
  SET_DATABASETABLES (state, tables) {
    state.tables = tables
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
