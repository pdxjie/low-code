import request from '@/utils/request'

export const listByCondition = (parameter) => {
  return request({
    url: '/source/list',
    method: 'post',
    data: parameter
  })
}
