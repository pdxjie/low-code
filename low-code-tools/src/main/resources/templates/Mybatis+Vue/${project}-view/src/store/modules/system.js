import router from '@/router'
const state = {
  collapsed: false,
  menus: [],
  loading: false
}
const actions = {
  setloadding ({ commit }, load) {
    commit('SHOWLOADDING', load)
  }
}
const mutations = {
  SETCOLLAPSED (state) {
    state.collapsed = !state.collapsed
  },
  SET_MENUS (state) {
    state.menus = router.options.routes[0].children
  },
  SHOWLOADDING (state, load) {
    state.loadding = load
  }
}
export default {
  namespaced: true,
  state,
  actions,
  mutations
}
