package com.pdx.config;

import com.pdx.entity.ConfigurationInfo;
import com.pdx.utils.PropertiesUtils;

import java.io.IOException;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
public class DBConfiguration {

    //配置信息
    private volatile static ConfigurationInfo CONFIGURATIONINFO = null;

    public static ConfigurationInfo getGlobleConfig() {
        if (null == CONFIGURATIONINFO) {
            synchronized (DBConfiguration.class) {
                if (null == CONFIGURATIONINFO) {
                    try {
                        PropertiesUtils.loadProperties();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return CONFIGURATIONINFO;
    }
}
