package com.pdx.generator;

import com.pdx.entity.ConfigurationInfo;
import com.pdx.entity.Configures;
import com.pdx.entity.Table;
import com.pdx.utils.DataBaseUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description:
 */
public class GeneratorFacade {

    private String templatePath;

    private String outPath;

    private Configures configures;

    private ConfigurationInfo dataBase;

    public GeneratorFacade(String templatePath, String outPath, Configures configures, ConfigurationInfo dataBase) {
        this.templatePath = templatePath;
        this.outPath = outPath;
        this.configures = configures;
        this.dataBase = dataBase;
    }

    public void generatorByDataBase() throws Exception {
        List<Table> tables = DataBaseUtils.getDbInfo(dataBase);
        for (Table table : tables) {
            //对每一个table对象进行代码生成
            /**
             * 数据模型
             * 调用核心生成类
             */
            Map<String,Object> dataModel = getDataModel(table);
            for (Map.Entry<String,Object> entry:dataModel.entrySet()){
                System.out.println(entry.getKey() +"---------"+entry.getValue());
            }
        }
    }

    /**
     * 根据table对象获取数据模型
     * @param table
     * @return
     */
    private Map<String, Object> getDataModel(Table table) {
        Map<String,Object> dataModel = new HashMap<>();
        dataModel.putAll(DataBaseUtils.customMap);
        dataModel.put("table",table);
        dataModel.putAll(this.configures.getSettingMap());
        dataModel.put("ClassName",table.getName2());
        return null;
    }
}
