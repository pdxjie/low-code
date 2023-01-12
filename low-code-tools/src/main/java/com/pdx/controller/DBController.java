package com.pdx.controller;

import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.ConfigurationInfo;
import com.pdx.entity.TableInfo;
import com.pdx.service.DBService;
import com.pdx.utils.DataBaseUtils;
import com.pdx.utils.Result;
import jdk.nashorn.internal.runtime.regexp.joni.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@RestController
@RequestMapping("/db")
public class DBController {

    @Autowired
    private DBService dbService;


    /**
     * 测试连接数据源
     * @param configuration
     * @return
     */
    @PostMapping("/connect/checkout")
    public Map<String, Object> checkoutConn(@RequestBody(required = false) ConfigurationInfo configuration) throws Exception{
        Map<String, Object> resultMap = new HashMap<>();
        Connection connection = null;
        try {
            connection = DataBaseUtils.getConnection(configuration);
            if (connection == null){
                resultMap.put("result",false);
            }else {
                resultMap.put("result",true);
            }
            if (connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultMap;
    }

    /**
     * 获取所有的数据库信息
     * @param configuration
     * @return
     */
    @PostMapping("/tables/all")
    public Result allTables(@RequestBody(required = false) ConfigurationInfo configuration) throws Exception{
        try {
            List<String> schemas = DataBaseUtils.getSchemas(configuration);
            return Result.ok().data("result",schemas);
        }catch (Exception e){
            return Result.error().success(false);
        }

    }

    /**
     * 获取tableInfo
     * @param jsonObject
     * @return
     */
    @PostMapping("/tables/info")
    public Result tableInfos(@RequestBody(required = false) JSONObject jsonObject){
        List<TableInfo> tableInfo = new ArrayList<>();
        try {
            //ConfigurationInfo configurationInfo = new ConfigurationInfo();
            JSONObject paramObject = jsonObject.getJSONObject("config");
            ConfigurationInfo configurationInfo = JSONObject.toJavaObject(paramObject, ConfigurationInfo.class);
            String tableName = jsonObject.getString("tableName");
            tableInfo = DataBaseUtils.getTableInfo(configurationInfo, tableName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.ok().data("result",tableInfo);
    }

    /**
     * 获取表字段信息
     * @param jsonObject
     * @return
     */
    @PostMapping("/table/detail")
    public Result getTableDetailInfo(@RequestBody JSONObject jsonObject){
        TableInfo tableInfo = new TableInfo();
        try {
            JSONObject paramObject = jsonObject.getJSONObject("config");
            ConfigurationInfo configurationInfo = JSONObject.toJavaObject(paramObject, ConfigurationInfo.class);
            String dbName = jsonObject.getString("dbName");
            String tableName = jsonObject.getString("tableName");
            tableInfo = DataBaseUtils.getTableDetailInfo(configurationInfo,dbName,tableName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return Result.ok().data("result",tableInfo);
    }

    /**
     * 创建表结构
     * @param jsonObject
     * @return
     */
    @PostMapping("/create/table")
    public Result createTable(@RequestBody JSONObject jsonObject){
        Boolean result = dbService.createTable(jsonObject);
        return Result.ok().data("result",result);
    }
}
