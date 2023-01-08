package com.pdx.entity;


import lombok.Data;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 接数据库相关配置
 */
@Data
public class DataBase {

    private static String mysqlUrl = "jdbc:mysql://[ip]:[port]/[db]?useUnicode=true&characterEncoding=UTF8&useSSL=false";
    private String userName;
    private String passWord;
    private String driver;
    private String url;

    /**
     * 构造类,用于没写初始化信息的自动配置
     */
    public DataBase() {
        this("localhost", "3306", "");
    }

    /**
     * @param db  数据库名字
     */
    public DataBase(String db) {
        this("localhost","3306",db);
    }


    /**
     *
     * @param ip  localhost
     * @param port 端口号
     * @param db 数据库
     */
    public DataBase(String ip,String port,String db) {
            this.driver="com.mysql.jdbc.Driver";
            this.url=mysqlUrl.replace("[ip]",ip).replace("[port]",port).replace("[db]",db);
    }


}
