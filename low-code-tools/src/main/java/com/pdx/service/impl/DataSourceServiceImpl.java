package com.pdx.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pdx.entity.ConfigurationInfo;
import com.pdx.entity.DataSource;
import com.pdx.mapper.DataSourceMapper;
import com.pdx.service.DataSourceService;
import com.pdx.utils.DataBaseUtils;
import com.pdx.utils.DesUtils;
import com.pdx.utils.PasswordUtils;
import com.pdx.utils.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/20 2023/1/20
 * @Description:
 */
@Service
public class DataSourceServiceImpl extends ServiceImpl<DataSourceMapper, DataSource> implements DataSourceService {

    @Resource
    private DataSourceMapper dataSourceMapper;

    @Override
    public Map<String, Object> listByCondition(Integer page, Integer pageSize, String sourceName,String userId) {
        Page<DataSource> pageOne = new Page<>(page,pageSize);
        QueryWrapper<DataSource> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(sourceName)){
            wrapper.like("source_name",sourceName);
        }
        wrapper.eq("user_id",userId);
        wrapper.orderByDesc("create_time");
        IPage<DataSource> dataSourceIPage = dataSourceMapper.selectPage(pageOne, wrapper);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("records",dataSourceIPage.getRecords());
        resultMap.put("total",dataSourceIPage.getTotal());
        return resultMap;
    }

    @Override
    public Map<String, Object> insertDataSource(DataSource dataSource,String userId) {
        dataSource.setId(IdWorker.getIdStr());
        dataSource.setConnectNum(0);
        dataSource.setCreateTime(new Timestamp(new Date().getTime()));
        dataSource.setUpdateTime(new Timestamp(new Date().getTime()));
        ConfigurationInfo configurationInfo = new ConfigurationInfo();
        configurationInfo.setPassword(dataSource.getSourcePassword());
        configurationInfo.setPort(dataSource.getSourcePort());
        configurationInfo.setLoginName(dataSource.getSourceAccount());
        configurationInfo.setIp(dataSource.getSourceIp());
        try {
            List<String> schemas = DataBaseUtils.getSchemas(configurationInfo);
            dataSource.setDatabaseNum(null == schemas ? 0 : schemas.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String encrypt = DesUtils.encrypt(dataSource.getSourcePassword());
        dataSource.setSourcePassword(encrypt);
        dataSource.setUserId(userId);
        int result = dataSourceMapper.insert(dataSource);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isSuccess",result == 1);
        return resultMap;
    }

    @Override
    public Map<String, Object> detailInfo(String id) {
        DataSource dataSource = dataSourceMapper.selectById(id);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("dataSource",dataSource);
        return resultMap;
    }

    @Override
    public Map<String, Object> updateDataSource(DataSource dataSource) {
        int result = dataSourceMapper.updateById(dataSource);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isSuccess",result == 1);
        return resultMap;
    }

    @Override
    public Map<String, Object> connectDataSource(String id) {
        Map<String,Object> resultMap = new HashMap<>();
        DataSource dataSource = dataSourceMapper.selectById(id);
        ConfigurationInfo configurationInfo = new ConfigurationInfo();
        configurationInfo.setIp(dataSource.getSourceIp());
        configurationInfo.setPort(dataSource.getSourcePort());
        configurationInfo.setLoginName(dataSource.getSourceAccount());
        configurationInfo.setPassword(DesUtils.decrypt(dataSource.getSourcePassword()));
        try {
            Connection connection = DataBaseUtils.getConnection(configurationInfo);
            if (connection == null){
                resultMap.put("result",false);
            }else {
                dataSource.setConnectNum(dataSource.getConnectNum()+1);
                dataSourceMapper.updateById(dataSource);
                resultMap.put("result",true);
            }
            if (connection != null){
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> selectDataSources(String userId) {
        QueryWrapper<DataSource> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<DataSource> dataSources = dataSourceMapper.selectList(wrapper);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("dataSources",dataSources);
        return resultMap;
    }

    @Override
    public Map<String, Object> detail(String id) {
        DataSource dataSource = dataSourceMapper.selectById(id);
        dataSource.setSourcePassword(DesUtils.decrypt(dataSource.getSourcePassword()));
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("dataSource",dataSource);
        return resultMap;
    }
}
