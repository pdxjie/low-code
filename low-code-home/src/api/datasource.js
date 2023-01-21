import request from '@/utils/request'

/**
 * 分页查询
 * @param parameter
 * @returns {AxiosPromise}
 */
export const listByCondition = (parameter) => {
  return request({
    url: '/source/list',
    method: 'post',
    data: parameter
  })
}

/**
 * 新增数据源
 * @param parameter
 * @returns {AxiosPromise}
 */
export const addDataSourceConfigInfo = (parameter) => {
  return request({
    url: '/source/insert/datasource',
    method: 'post',
    data: parameter
  })
}

/**
 * 更新数据源
 * @param parameter
 * @returns {AxiosPromise}
 */
export const editDataSourceConfigInfo = (parameter) => {
  return request({
    url: '/source/update/datasource',
    method: 'post',
    data: parameter
  })
}

/**
 * 数据源详情
 * @param id
 * @returns {AxiosPromise}
 */
export const DataSourceDetailInfo = (id) => {
  return request({
    url: `/source/info/${id}`,
    method: 'get'
  })
}
/**
 * 数据源详情
 * @param id
 * @returns {AxiosPromise}
 */
export const DataSourceDetail = (id) => {
  return request({
    url: `/source/detail/${id}`,
    method: 'get'
  })
}

/**
 * 删除数据源
 * @param id
 * @returns {AxiosPromise}
 */
export const removeDataSourceInfo = (id) => {
  return request({
    url: `/source/delete/${id}`,
    method: 'delete'
  })
}

/**
 * 测试连接数据源
 * @param id
 * @returns {AxiosPromise}
 */
export const connectDataSource = (id) => {
  return request({
    url: `/source/connect/${id}`,
    method: 'get'
  })
}

/**
 * 数据源列表
 * @param userId
 * @returns {AxiosPromise}
 */
export const dataSources = (userId) => {
  return request({
    url: `/source/datasource/${userId}`,
    method: 'get'
  })
}
