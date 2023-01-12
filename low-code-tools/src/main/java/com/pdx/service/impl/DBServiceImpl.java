package com.pdx.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.ConfigurationInfo;
import com.pdx.entity.TableDetailInfo;
import com.pdx.entity.TableInfo;
import com.pdx.service.DBService;
import com.pdx.utils.DataBaseUtils;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@Service
public class DBServiceImpl implements DBService {
    @Override
    public List<String> allTables(Connection connection) {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            //获取数据库列表
            ResultSet resultSet = metaData.getCatalogs();
            while (resultSet.next()){
                String tableName = resultSet.getString(1);
                tables.add(tableName);
            }
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tables;
    }

    @Override
    public Boolean createTable(JSONObject jsonObject) {
        //ConfigurationInfo configurationInfo = new ConfigurationInfo();
        JSONObject paramObject = jsonObject.getJSONObject("config");
        ConfigurationInfo configurationInfo = JSON.toJavaObject(paramObject, ConfigurationInfo.class);
        String dbName = jsonObject.getString("dbName");
        String tableDetailData = jsonObject.getString("tableDetailData");
        JSONObject tableInfoJson = JSONObject.parseObject(tableDetailData);
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableInfoJson.getString("tableName"));
        tableInfo.setRemark(tableInfoJson.getString("remark"));
        JSONArray columnsData = tableInfoJson.getJSONArray("columnsData");
        List<TableDetailInfo> tableDetailInfos = new ArrayList<>();
        for (Object columnsDatum : columnsData) {
            String jsonString = columnsDatum.toString();
            JSONObject parseObject = JSONObject.parseObject(jsonString);
            TableDetailInfo tableDetailInfo = JSON.toJavaObject(parseObject, TableDetailInfo.class);
            tableDetailInfos.add(tableDetailInfo);
        }
        tableInfo.setDetailInfos(tableDetailInfos);
        Boolean result = false;
        try {
            result = DataBaseUtils.createTable(configurationInfo, dbName, tableInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}
