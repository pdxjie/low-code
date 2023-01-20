package com.pdx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pdx.entity.DataSource;

import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/20 2023/1/20
 * @Description:
 */
public interface DataSourceService extends IService<DataSource> {
    Map<String, Object> listByCondition(Integer page, Integer pageSize, String sourceName,String userId);

    Map<String, Object> insertDataSource(DataSource dataSource,String userId);

    Map<String, Object> detailInfo(String id);

    Map<String, Object> updateDataSource(DataSource dataSource);

    Map<String, Object> connectDataSource(String id);
}
