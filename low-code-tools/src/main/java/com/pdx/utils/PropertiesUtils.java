package com.pdx.utils;

import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.ConfigurationInfo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    /***
     * 加载全局配置
     * @throws IOException 默认抛出IO异常
     */
    public static void loadProperties() throws IOException {
        // 兼容Jar包外 处理配置文件
        String filePath = System.getProperty("user.dir") + File.separator + "application.properties";
        InputStream inStream;
        if (new File(filePath).exists()) {
            inStream = new FileInputStream(filePath);
        } else {
            inStream = PropertiesUtils.class.getClassLoader().getResourceAsStream("application.properties");
        }

        Properties prop = new Properties();
        prop.load(inStream);

        // FastJson 构造对象
        JSONObject json = new JSONObject();

//        for (int i = 0; i < KEYS.length; i++) {
//            String value = prop.getProperty(KEYS[i], VALUES[i]);
//            json.put(KEYS[i], value);
//        }

//        ConfigurationInfo configurationInfo = json.toJavaObject(ConfigurationInfo.class);
//        configurationInfo.setIncludeMap(parseInclude(configurationInfo.getInclude()));
//        configurationInfo.setCustomHandleIncludeMap(parseInclude(configurationInfo.getCustomHandleInclude()));
//
//        // 解析项目目录地址
//        String projectPath = configurationInfo.getRootPath() + File.separator + configurationInfo.getProjectName();
//        configurationInfo.setProjectPath(projectPath);
//
//        GlobleConfig.setGlobleConfig(configurationInfo);

    }


}
