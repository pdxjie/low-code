import { asyncRouterMap, constantRouterMap } from '@/config/router.config'
import cloneDeep from 'lodash.clonedeep'

function filterConstantRouter (routerMap) {
  const accessedRouters = routerMap.filter(route => {
    if (route.children && route.children.length) {
      route.children = filterConstantRouter(route.children)
    }
    return true
  })
  return accessedRouters
}

function filterAsyncRouter (routerMap) {
  const accessedRouters = routerMap.filter(route => {
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children)
      }
      return true
  })
  return accessedRouters
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
    }
  },
  actions: {
    GenerateRoutes ({ commit }) {
      // return new Promise(resolve => {
        const routerMap = cloneDeep(asyncRouterMap)
        const accessedRouters = filterAsyncRouter(routerMap)
        commit('SET_ROUTERS', accessedRouters)
        // resolve()
      // })
    },
    StaticRoutes ({ commit }) {
      const resultMap = cloneDeep(constantRouterMap)
      const accessedRouters = filterConstantRouter(resultMap)
      commit('SET_ROUTERS', accessedRouters)
    }
  }
}

export default permission
