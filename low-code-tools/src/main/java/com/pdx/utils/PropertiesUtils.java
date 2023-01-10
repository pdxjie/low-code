package com.pdx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: Properties工具类
 */
public class PropertiesUtils {

    //自定义的数据模型map集合
    public static Map<String,String> customMap = new HashMap<String,String>();

    static {
        File dir = new File("properties");
        try {
            //查询某个目录下的所有文件
            List<File> files = FileUtils.searchAllFile(new File(dir.getAbsolutePath()));
            for (File file : files) {
                if(file.getName().endsWith(".properties")) {
                    Properties prop = new Properties();
                    prop.load(new FileInputStream(file));
                    customMap.putAll((Map) prop);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
