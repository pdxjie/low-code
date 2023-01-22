import router from './router'//, { resetRouter }
import store from './store'
import { message } from 'ant-design-vue'
import NProgress from 'nprogress' // progress bar
import '@/components/NProgress/nprogress.less' // progress bar custom style
// import notification from 'ant-design-vue/es/notification'
import { setDocumentTitle, domTitle } from '@/utils/domUtil'
import { ACCESS_TOKEN } from '@/store/mutation-types'
import { i18nRender } from '@/locales'

NProgress.configure({ showSpinner: false }) // NProgress Configuration

const allowList = ['login', 'register', 'registerResult', 'Workplace', 'BaseForm', 'Config', 'Exception403'] // no redirect allowList
const loginRoutePath = '/user/login'
const defaultRoutePath = '/dashboard/workplace'

router.beforeEach((to, from, next) => {
  NProgress.start() // start progress bar
  to.meta && typeof to.meta.title !== 'undefined' && setDocumentTitle(`${i18nRender(to.meta.title)} - ${domTitle}`)
  /* has token */
  const token = localStorage.getItem(ACCESS_TOKEN)
  if (token) {
    if (to.path === loginRoutePath) {
      next({ path: defaultRoutePath })
      NProgress.done()
    } else {
      store.dispatch('StaticRoutes')
      store.dispatch('GetInfo')
      if (to.path === '/data/manage') {
        if (store.getters.userInfo.role === 'admin') {
          next()
        } else {
          next({ path: '/exception' })
        }
      } else {
        next()
      }
    }
  } else {
    if (allowList.includes(to.name)) {
      // 在免登录名单，直接进入
      store.dispatch('StaticRoutes')
      next()
    } else {
      console.log(to.path)
      if (to.path === '/data/manage') {
        next({ path: '/exception' })
      } else {
        message.warning('请先登录')
        next({ path: loginRoutePath, query: { redirect: to.fullPath } })
      }

      NProgress.done() // if current page is login will not trigger afterEach hook, so manually handle it
    }
  }
})

router.afterEach(() => {
  NProgress.done() // finish progress bar
})
