package com.pdx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.ConfigurationInfo;
import com.pdx.entity.Configures;
import com.pdx.entity.TableInfo;
import com.pdx.generator.GeneratorCore;
import com.pdx.generator.GeneratorConfig;
import com.pdx.utils.DataBaseUtils;
import com.pdx.utils.Result;
import com.pdx.utils.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/22 2023/1/22
 * @Description:
 */
@RestController
@RequestMapping("/generate")
public class GeneratorController {

    @PostMapping("/code")
    public Result generatorCode(@RequestBody JSONObject jsonObject) throws Exception {
        JSONObject parameter = jsonObject.getJSONObject("config");
        ConfigurationInfo configurationInfo = JSONObject.toJavaObject(parameter, ConfigurationInfo.class);
        JSONObject generatorConfig = jsonObject.getJSONObject("generatorConfig");
        //线上不可自定义代码生成路径，默认生成D盘
        String outPath = generatorConfig.getString("outPath");
        if (StringUtils.isBlank(outPath)){
            outPath = "D:\\";
        }
        String templatePath = jsonObject.getString("templatePath");
        String pPackage = generatorConfig.getString("pPackage");
        List<String> tableInfos = new ArrayList<>();
        JSONArray tableNames = jsonObject.getJSONArray("tableNames");
        for (Object tableName : tableNames) {
            JSONObject jsonObject1 = JSON.parseObject(tableName.toString());
            TableInfo tableInfo = JSONObject.toJavaObject(jsonObject1, TableInfo.class);
            tableInfos.add(tableInfo.getTableName());
        }
        String[] pack = pPackage.split("\\.");
        String property =System.getProperty("user.dir")+ "\\src\\main\\resources\\templates\\"+templatePath;
        String project = generatorConfig.getString("project");
        String databaseName = jsonObject.getString("databaseName");
        //自定义命名
        String customDesignate = generatorConfig.getString("customDesignate");
        if (StringUtils.isNotBlank(customDesignate)){
            //customMap.put("tableRemovePrefixes","");
            DataBaseUtils.customMap.put("tableRemovePrefixes",customDesignate);
        }
        GeneratorCore generator = new GeneratorCore(property,outPath);
        Configures configures = new Configures(project,pPackage,"");
        configures.setPath1(pack[0]).setPath2(pack[1]).setPath3(pack[2]);
        configures.setPPackage(pPackage).setProject(project);
        GeneratorConfig generatorFacade = new GeneratorConfig(property,outPath,configures,configurationInfo);
        generatorFacade.setDataBase(configurationInfo);
        generatorFacade.setGenerator(generator);
        generatorFacade.setDatabaseName(databaseName);
        generatorFacade.setTableNames(tableInfos);
        generatorFacade.generatorByDataBase();
        return Result.ok().success(true);
    }
}
