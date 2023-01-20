package com.pdx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.DataSource;
import com.pdx.service.DataSourceService;
import com.pdx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        Integer page = jsonObject.getInteger("page");
        Integer pageSize = jsonObject.getInteger("pageSize");
        String sourceName = jsonObject.getString("sourceName");
        String userId = jsonObject.getString("userId");
        Map<String,Object> resultMap = dataSourceService.listByCondition(page,pageSize,sourceName,userId);
        return Result.ok().data(resultMap);
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

    @GetMapping("/connect/{id}")
    public Result connectDataSource(@PathVariable("id")String id){
        Map<String,Object> resultMap = dataSourceService.connectDataSource(id);
        return Result.ok().data(resultMap);
    }

}
