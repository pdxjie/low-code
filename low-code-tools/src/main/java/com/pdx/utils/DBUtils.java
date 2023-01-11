package com.pdx.utils;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.pdx.entity.ConfigurationInfo;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@Slf4j
public class DBUtils {
    private volatile static Connection connection = null;

    private static Connection produceConnection(ConfigurationInfo configurationInfo) throws Exception {
        String url = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=utf8", configurationInfo.getIp(), configurationInfo.getPort());
        String loginName = configurationInfo.getLoginName();
        String password = configurationInfo.getPassword();
        Properties properties = new Properties();
        properties.put("remarkReporting","true");//获取数据库的备注
        properties.put("user",loginName);
        properties.put("password",password);
        Class.forName(configurationInfo.getDriver());
        return DriverManager.getConnection(url, properties);
    }

    /**
     * Druid连接池
     * @param configurationInfo
     * @return
     */
    private static Connection getDruidConnection(ConfigurationInfo configurationInfo){
        DruidPooledConnection connection = null;
        DruidDataSource dataSource = new DruidDataSource();
        String url = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=utf8", configurationInfo.getIp(), configurationInfo.getPort());
        Map<String,Object> connConfig = new HashMap<>();
        connConfig.put("driverClassName","com.mysql.jdbc.Driver");
        connConfig.put("url",url);
        connConfig.put("username",configurationInfo.getLoginName());
        connConfig.put("password",configurationInfo.getPassword());
        connConfig.put("initialSize",5);
        connConfig.put("maxAction",20);
        try {
            dataSource.setDriverClassName("com.mysql.jdbc.Driver");
            dataSource.setUrl(url);
            dataSource.setUsername(configurationInfo.getLoginName());
            dataSource.setPassword(configurationInfo.getPassword());
            dataSource.setInitialSize(5);
            dataSource.setMaxActive(20);
            connection = dataSource.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }

    /***
     * 获取数据库连接,双重校验保证线程安全
     */
    public static Connection getConnection(ConfigurationInfo configurationInfo){
        try {
            if (null == connection) {
                synchronized (DBUtils.class) {
                    if (null == connection) {
                        long start = System.currentTimeMillis();
                        connection = produceConnection(configurationInfo);
                        log.info("Connection is ready. consume time is: " + (System.currentTimeMillis() - start) + "ms");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
