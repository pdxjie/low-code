// eslint-disable-next-line
import { UserLayout, BasicLayout, BlankLayout } from '@/layouts'
// import { bxAnaalyse } from '@/core/icons'

const RouteView = {
  name: 'RouteView',
  render: h => h('router-view')
}

export const asyncRouterMap = [
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: 'menu.home' },
    redirect: '/generator/code',
    children: [
      {
        path: '/generator/code',
        name: 'Workplace',
        component: () => import('@/views/dashboard/Workplace'),
        meta: { title: '代码生成', icon: 'code', keepAlive: true }
      },
      {
        path: '/online/config',
        name: 'Config',
        component: () => import('@/views/list/BasicList'),
        meta: { title: 'Online配置', icon: 'cloud-server', keepAlive: true }
      },
      {
        path: '/account/center',
        name: 'center',
        component: () => import('@/views/account/center'),
        meta: { title: '个人中心', keepAlive: true }
      }
    ]
  },
  {
    path: '*',
    redirect: '/404',
    hidden: true
  }
]

/**
 * 基础路由
 * @type { *[] }
 */
export const constantRouterMap = [
  {
    path: '/user',
    component: UserLayout,
    redirect: '/user/login',
    hidden: true,
    children: [
      {
        path: 'login',
        name: 'login',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Login')
      },
      {
        path: 'register',
        name: 'register',
        component: () => import(/* webpackChunkName: "user" */ '@/views/user/Register')
      }
    ]
  },
  {
    path: '/',
    name: 'index',
    component: BasicLayout,
    meta: { title: 'menu.home' },
    redirect: '/generator/code',
    children: [
      {
        path: '/generator/code',
        name: 'Workplace',
        component: () => import('@/views/dashboard/Workplace'),
        meta: { title: '代码生成', icon: 'code', keepAlive: true }
      },
      {
        path: '/online/config',
        name: 'Config',
        component: () => import('@/views/list/BasicList'),
        meta: { title: 'Online配置', icon: 'cloud-server', keepAlive: true }
      },
      {
        path: '/exception',
        name: 'exception',
        hidden: true,
        component: RouteView,
        redirect: '/exception/403',
        meta: { title: 'menu.exception', icon: 'warning' },
        children: [
          {
            path: '/exception/403',
            name: 'Exception403',
            component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/403'),
            meta: { title: 'menu.exception.not-permission' }
          },
          {
            path: '/exception/404',
            name: 'Exception404',
            component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404'),
            meta: { title: 'menu.exception.not-find' }
          },
          {
            path: '/exception/500',
            name: 'Exception500',
            component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/500'),
            meta: { title: 'menu.exception.server-error' }
          }
        ]
      },
      {
        path: '/account/center',
        name: 'center',
        component: () => import('@/views/account/center'),
        meta: { title: '个人中心', icon: 'user', keepAlive: true }
      },
      {
        path: '/data/manage',
        name: 'manage',
        component: () => import('@/views/Manage'),
        meta: { title: '后台运维', icon: 'pull-request' }
      }
    ]
  },
  {
    path: '/404',
    component: () => import(/* webpackChunkName: "fail" */ '@/views/exception/404')
  }
]
