import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '@/views/Login/index.vue'
import Error from '@/views/Error/index.vue'
import Admin from '@/layout/Admin.vue'

Vue.use(VueRouter)

const originPush = VueRouter.prototype.push
const originReplace = VueRouter.prototype.replace
VueRouter.prototype.push = function (location, resolve, reject) {
  if (resolve && reject) {
    originPush.call(this, location, resolve, reject)
  } else {
    originPush.call(this, location, () => {}, () => {})
  }
}

VueRouter.prototype.replace = function (location, resolve, reject) {
  if (resolve && reject) {
    originReplace.call(this, location, resolve, reject)
  } else {
    originReplace.call(this, location, () => {}, () => {})
  }
}

const routes = [
  {
    path: '/',
    redirect: '/home',
    component: Admin,
    children: [
      {
        path: '/home',
        component: () => import('@/views/Home/index.vue'),
        meta: { title: '首页', icon: 'home' }
      },
      {
        path: '/ui',
        meta: { title: '组件', icon: 'gold' },
        component: { render: h => h('router-view') },
        children: [
          {
            path: '/ui/buttons',
            meta: { title: '按钮' },
            component: () => import('@/views/Button/index.vue')
          },
          {
            path: '/ui/modals',
            meta: { title: '弹框' },
            component: () => import('@/views/Modals/index.vue')
          },
          {
            path: '/ui/notification',
            meta: { title: '通知提醒' },
            component: () => import('@/views/Notification/index.vue')
          },
          {
            path: '/ui/messages',
            meta: { title: '全局Message' },
            component: () => import('@/views/Message/index.vue')
          },
        ]
      },
      //----------以下配置新路由-----------------
    ]
  },
  {
    path: '/login',
    hideInMenu: true,
    component: Login
  },
  {
    path: '*',
    hideInMenu: true,
    component: Error
  },
]

const router = new VueRouter({
  routes
})

export default router
