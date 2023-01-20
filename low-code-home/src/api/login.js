import request from '@/utils/request'

const userApi = {
  Logout: '/auth/logout',
  ForgePassword: '/auth/forge-password',
  twoStepCode: '/auth/2step-code',

  SendSmsErr: '/account/sms_err',
  // get my info
  UserInfo: '/user/detail/info',
  SendSms: '/user/code',
  Login: '/user/login',
  Register: '/user/register',
  UserMenu: '/user/nav'
}

/**
 * login func
 * parameter: {
 *     username: '',
 *     password: '',
 *     remember_me: true,
 *     captcha: '12345'
 * }
 * @param parameter
 * @returns {*}
 */
export function login (parameter) {
  return request({
    url: userApi.Login,
    method: 'post',
    data: parameter
  })
}

export function Register (parameter) {
  return request({
    url: userApi.Register,
    method: 'post',
    data: parameter
  })
}

export function getSmsCaptcha (parameter) {
  return request({
    url: userApi.SendSms,
    method: 'post',
    data: parameter
  })
}

export function getInfo (params) {
  return request({
    url: userApi.UserInfo + '?accessToken=' + params,
    method: 'get'
  })
}

export function getCurrentUserNav () {
  return request({
    url: userApi.UserMenu,
    method: 'get'
  })
}
/**
 * get user 2step code open?
 * @param parameter {*}
 */
export function get2step (parameter) {
  return request({
    url: userApi.twoStepCode,
    method: 'post',
    data: parameter
  })
}
