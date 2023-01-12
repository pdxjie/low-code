import request from '@/utils/request'

/**
 * 测试连接数据源
 * @param config
 * @returns {AxiosPromise}
 * @constructor
 */
export const CheckoutConnectionApi = (config) => {
  return request({
    url: '/db/connect/checkout',
    method: 'post',
    data: config
  })
}
/**
 * 所有的数据库列表
 * @param config
 * @returns {AxiosPromise}
 * @constructor
 */
export const AllTables = (config) => {
  return request({
    url: '/db/tables/all',
    method: 'post',
    data: config
  })
}
/**
 * 获取表信息
 * @param params
 * @returns {AxiosPromise}
 * @constructor
 */
export const TableInfos = (params) => {
  return request({
    url: '/db/tables/info',
    method: 'post',
    data: params
  })
}
/**
 * 获取表字段信息
 * @param params
 * @returns {AxiosPromise}
 * @constructor
 */
export const TableDetailInfo = (params) => {
  return request({
    url: '/db/table/detail',
    method: 'post',
    data: params
  })
}

/**
 * 创建表结构
 * @param params
 * @returns {AxiosPromise}
 * @constructor
 */
export const CreateTable = (params) => {
  return request({
    url: '/db/create/table',
    method: 'post',
    data: params
  })
}
