import request from '@/utils/request'

export const generatorCode = (parameter) => {
  return request({
    url: '/generate/code',
    method: 'post',
    data: parameter
  })
}
