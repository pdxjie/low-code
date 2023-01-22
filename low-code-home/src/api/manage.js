import request from '@/utils/request'

const api = {
  user: '/user/list/condition'

}

/**
 * 用户列表
 * @param parameter
 * @returns {AxiosPromise}
 * @constructor
 */
export const OperatorDatas = (parameter) => {
  return request({
    url: api.user,
    method: 'post',
    data: parameter
  })
}
