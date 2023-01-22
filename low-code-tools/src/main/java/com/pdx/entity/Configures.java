package com.pdx.entity;

import com.pdx.utils.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 基础配置
 */
@Data
@Accessors(chain = true)
public class Configures {

    /**
     *项目名
     */
    private String project = "example";
    /**
     * 包名
     */
    private String pPackage = "com.example";
    /**
     * 作者
     */
    private String author;
    /**
     * 一级包名
     */
    private String path1 = "com";
    /**
     * 二级包名
     */
    private String path2 = "example";
    /**
     * 三级包名
     */
    private String path3 = "";
    /**
     * 包名格式化
     */
    private String pathAll;

    /**
     * 构造函数
     * @param project   项目名字
     * @param pPackage  前面写的包名
     * @param author      作者
     */
    public Configures(String project, String pPackage, String author) {

        if (StringUtils.isNotBlank(project)) {
            this.project = project;
        }
        if (StringUtils.isNotBlank(pPackage)) {
            this.pPackage = pPackage;
        }
        this.author = author;
        //对各级包名进行赋值
        String[] paths = pPackage.split("\\.");
        path1 = paths[0];
        path2 = paths.length > 1 ? paths[1] : path2;
        path3 = paths.length > 2 ? paths[2] : path3;
        pathAll = pPackage.replaceAll("\\.", "/");
    }

    public Map<String, Object> getSettingMap() {
        Map<String, Object> map = new HashMap<>();
        //获得所有申明的字段
        Field[] declaredFields = Configures.class.getDeclaredFields();

        for (Field field : declaredFields) {
            field.setAccessible(true); //、获取此类的私有成员变量的value
            try {
                map.put(field.getName(), field.get(this));
            } catch (Exception e) {
            }
        }
        return map;
    }

}
