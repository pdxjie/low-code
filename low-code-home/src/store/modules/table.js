import { ACCESS_TOKEN } from '@/store/mutation-types'

/**
 * 获取当前数据
 * @returns {any|{}}
 */
function getCurrentTableData () {
  // 判断是否已经登录
  if (localStorage.getItem(ACCESS_TOKEN)) {
    return JSON.parse(localStorage.getItem('tableData')) || {}
  } else {
    return JSON.parse(sessionStorage.getItem('tableData')) || {}
  }
}

/**
 * 获取当前数据
 * @returns {any|{}}
 */
function getOnlineTableData () {
  // 判断是否已经登录
  if (localStorage.getItem(ACCESS_TOKEN)) {
    return JSON.parse(localStorage.getItem('onlineTableData')) || {}
  } else {
    return JSON.parse(sessionStorage.getItem('onlineTableData')) || {}
  }
}

const state = {
  currentTableData: getCurrentTableData(),
  onlineTableData: getOnlineTableData(),
  currentDataSource: JSON.parse(localStorage.getItem('currentDataSource')) || {}
}

const actions = {
  currentTablesData ({ commit }, data) {
    commit('SET_CURRENTTABLES', data)
  },
  onlineTableData ({ commit }, data) {
    commit('SET_ONLINETABLEDATA', data)
  },
  currentDataSourceData ({ commit }, data) {
    commit('SET_CURRENTDATASOURCE', data)
  }
}

const mutations = {
  SET_CURRENTTABLES (state, tableData) {
    // 存入本地
    if (localStorage.getItem(ACCESS_TOKEN)) {
      // 已登录 localStorage
      // 每次存前 先删除
      localStorage.removeItem('tableData')
      localStorage.setItem('tableData', JSON.stringify(tableData))
    } else {
      sessionStorage.removeItem('tableData')
      sessionStorage.setItem('tableData', JSON.stringify(tableData))
    }
    state.currentTableData = tableData
  },
  SET_ONLINETABLEDATA (state, data) {
    // 存入本地
    if (localStorage.getItem(ACCESS_TOKEN)) {
      // 已登录 localStorage
      // 每次存前 先删除
      localStorage.removeItem('onlineTableData')
      localStorage.setItem('onlineTableData', JSON.stringify(data))
    } else {
      sessionStorage.removeItem('onlineTableData')
      sessionStorage.setItem('onlineTableData', JSON.stringify(data))
    }
    state.onlineTableData = data
  },
  SET_CURRENTDATASOURCE (state, data) {
    localStorage.removeItem('currentDataSource')
    localStorage.setItem('currentDataSource', JSON.stringify(data))
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}
