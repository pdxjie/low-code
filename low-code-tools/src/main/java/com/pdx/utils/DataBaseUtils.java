package com.pdx.utils;

import com.pdx.entity.Column;
import com.pdx.entity.DataBase;
import com.pdx.entity.Table;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 数据库处理工具类
 */
public class DataBaseUtils {
    //获取到mysql中所有的数据库名称

    //获取数据库连接
    public static Connection getConnection(DataBase db) throws Exception {
        String url = "jdbc:mysql://127.0.0.1:3306/book?useUnicode=true&characterEncoding=utf8";
        Properties props = new Properties();
        props.put("remarksReporting", "true");//获取数据库的备注信息
        props.put("user", db.getUserName());
        props.put("password", db.getPassWord());
        Class.forName(db.getDriver());//注册驱动
        return DriverManager.getConnection(url, props);
    }


    /**
     * 获取数据库列表
     *
     * @param db 连接数据库的信息
     */
    public static List<String> getSchemas(DataBase db) throws Exception {
        //获取元数据
        Connection connection = getConnection(db);
        DatabaseMetaData metaData = connection.getMetaData();
        //获取所有数据库列表
        ResultSet rs = metaData.getCatalogs();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        rs.close();
        connection.close();
        return list;
    }

    /**
     * 获取数据库中的表和字段构造实体类
     */
    public static List<Table> getDbInfo(DataBase db) throws Exception {
        //获取连接
        Connection connection = getConnection(db);
        //获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //获取当前数据库中的所有表
        ResultSet tables = metaData.getTables("book", null, null, new String[]{"TABLE"});
        //表集合
        List<Table> list = new ArrayList<>();

        //遍历数据库信息获取表
        while (tables.next()) {
            Table tab = new Table();
            //i.表名
            String tableName = tables.getString("TABLE_NAME");
            //ii.类名 去除tb_  bl_前缀
            String className = removePrefix(tableName);//User
            //iii.表描述
            String remarks = tables.getString("REMARKS");
            //iv.主键
            ResultSet primaryKeys = metaData.getPrimaryKeys(null, null, tableName);
            StringBuilder keys = new StringBuilder();
            while (primaryKeys.next()) {
                String keyName = primaryKeys.getString("COLUMN_NAME");
                keys.append(keyName).append(",");
            }
            tab.setName(tableName);
            tab.setName2(className);
            tab.setComment(remarks);
            tab.setKey(keys.toString());
            //处理表中的所有字段
            //获取列集合
            ResultSet columns = metaData.getColumns(null, null, tableName, null);
            List<Column> columnList = new ArrayList<>();
            //遍历列集合
            while (columns.next()) {
                Column cn = new Column();
                //构造Column对象
                //列名称
                String columnName = columns.getString("COLUMN_NAME"); //user_id
                cn.setColumnName(columnName);
                //属性名 将_改成驼峰形式
                String attName = StringUtils.toJavaVariableName(columnName);
                cn.setColumnName2(attName);
                //java类型和数据库类型
                //这里有可能有出现 INT UNSIGNED(无符号),所以只取前面的INT就可以了
                String dbType = columns.getString("TYPE_NAME").split(" ")[0];//VARCHAR,DATETIME
                /*
                 *这里之所以改这些数据,是因为在mapper.xml的resultMap那里的jdbcType无法正常映射
                 */
                if ("DATETIME".equals(dbType)) {
                    dbType = "TIMESTAMP";
                }
                if ("INT".equals(dbType)) {
                    dbType = "INTEGER";
                }
                if ("TEXT".equals(dbType)) {
                    dbType = "VARCHAR";
                }
                if ("MEDIUMINT".equals(dbType)) {
                    dbType = "INTEGER";
                }
                cn.setColumnDbType(dbType);
                String javaType = PropertiesUtils.customMap.get(dbType);
                cn.setColumnType(javaType);
                //获取列备注
                String columnRemark = columns.getString("REMARKS");//VARCHAR,DATETIME
                cn.setColumnComment(columnRemark);
                //是否主键
                String pri = null;
                if (StringUtils.contains(columnName, keys.toString().split(","))) {
                    pri = "PRI";
                }
                cn.setColumnKey(pri);
                // 去过列是id或着version等基础列数据,不进行生成
                if ("id".equals(attName) || "version".equals(attName) || "flag".equals(attName)
                        || "createTime".equals(attName) || "createBy".equals(attName) ||
                        "updateBy".equals(attName) || "updateTime".equals(attName)) {
                } else {//不是以上数据进行生成
                    columnList.add(cn);
                }
            }
            columns.close();
            tab.setColumns(columnList);
            list.add(tab);
        }
        tables.close();
        connection.close();
        return list;
    }

    /**
     * @param tableName  表名
     */
    public static String removePrefix(String tableName) {
        //获取配置文件的去除前缀的东西
        String prefix = PropertiesUtils.customMap.get("tableRemovePrefixes");
        String temp = tableName;
        String[] split = prefix.split(",");
        for (String pf : split) {
            temp = StringUtils.removePrefix(temp, pf, true);
        }
        return StringUtils.makeAllWordFirstLetterUpperCase(temp);
    }

    public static void main(String[] args) throws Exception {
        DataBase dataBase = new DataBase("localhost","3306","book");
        dataBase.setUserName("root");
        dataBase.setPassWord("233031");

        List<String> dbInfo = DataBaseUtils.getSchemas(dataBase);
        for (String s : dbInfo) {
            System.out.println(s);
        }
    }
}
