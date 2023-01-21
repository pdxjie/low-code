package com.pdx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.DataSource;
import com.pdx.service.DataSourceService;
import com.pdx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/20 2023/1/20
 * @Description:
 */
@RestController
@RequestMapping("/source")
public class DataSourceController {

    @Autowired
    private DataSourceService dataSourceService;

    /**
     * 分页条件查询
     * @param jsonObject
     * @return
     */
    @PostMapping("/list")
    public Result listByCondition(@RequestBody JSONObject jsonObject){
        String page = jsonObject.getString("page");
        if (!isNumeric(page)){
            page = "1";
        }
        String pageSize = jsonObject.getString("pageSize");
        String sourceName = jsonObject.getString("sourceName");
        String userId = jsonObject.getString("userId");
        Map<String,Object> resultMap = dataSourceService.listByCondition(Integer.valueOf(page),Integer.valueOf(pageSize),sourceName,userId);
        return Result.ok().data(resultMap);
    }

    public boolean isNumeric(String str) {
        String bigStr;
        try {
            bigStr = new BigDecimal(str).toString();
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }

    /**
     * 新增数据源
     * @param jsonObject
     * @return
     */
    @PostMapping("/insert/datasource")
    public Result insertDataSource(@RequestBody JSONObject jsonObject){
        String userId = jsonObject.getString("userId");
        JSONObject datasourceJson = jsonObject.getJSONObject("datasource");
        DataSource dataSource = JSONObject.toJavaObject(datasourceJson, DataSource.class);
        Map<String,Object> resultMap = dataSourceService.insertDataSource(dataSource,userId);
        return Result.ok().data(resultMap);
    }

    /**
     * 数据源详情
     * @param id
     * @return
     */
    @GetMapping("/info/{id}")
    public Result detailInfo(@PathVariable("id")String id){
        Map<String,Object> resultMap = dataSourceService.detailInfo(id);
        return Result.ok().data(resultMap);
    }

    /**
     * 数据源详情
     * @param id
     * @return
     */
    @GetMapping("/detail/{id}")
    public Result detail(@PathVariable("id")String id){
        Map<String,Object> resultMap = dataSourceService.detail(id);
        return Result.ok().data(resultMap);
    }

    /**
     * 更新数据源
     * @param jsonObject
     * @return
     */
    @PostMapping("/update/datasource")
    public Result updateDataSource(@RequestBody JSONObject jsonObject){
        DataSource dataSource = JSONObject.toJavaObject(jsonObject, DataSource.class);
        Map<String,Object> resultMap = dataSourceService.updateDataSource(dataSource);
        return Result.ok().data(resultMap);
    }

    /**
     * 删除数据源
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteById(@PathVariable("id")String id){
        boolean isRemove = dataSourceService.removeById(id);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("isRemove",isRemove);
        return Result.ok().data(resultMap);
    }

    /**
     * 测试连接
     * @param id
     * @return
     */
    @GetMapping("/connect/{id}")
    public Result connectDataSource(@PathVariable("id")String id){
        Map<String,Object> resultMap = dataSourceService.connectDataSource(id);
        return Result.ok().data(resultMap);
    }

    /**
     * 获取所有的数据源列表
     * @param userId
     * @return
     */
    @GetMapping("/datasource/{userId}")
    public Result datasourceList(@PathVariable("userId")String userId){
        Map<String,Object> resultMap = dataSourceService.selectDataSources(userId);
        return Result.ok().data(resultMap);
    }

}
