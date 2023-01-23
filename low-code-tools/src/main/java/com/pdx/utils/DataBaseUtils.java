package com.pdx.utils;

import com.pdx.entity.*;

import java.sql.*;
import java.util.*;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 数据库处理工具类
 */
public class DataBaseUtils {
    //获取到mysql中所有的数据库名称

    public static Map<String,Object> customMap;

    static {
        customMap = new HashMap<>();
        customMap.put("VARCHAR","String");
        customMap.put("BIGINT","Long");
        customMap.put("INTEGER","Integer");
        customMap.put("INT","Integer");
        customMap.put("DATE","java.util.Date");
        customMap.put("DATETIME","java.util.Date");
        customMap.put("TIMESTAMP","java.util.Date");
        customMap.put("DOUBLE","Double");
        customMap.put("TEXT","String");
        customMap.put("VARCHAR2","String");
        customMap.put("NVARCHAR2","String");
        customMap.put("CHAR","String");
        customMap.put("MEDIUMTEXT","String");
        customMap.put("TINYINT","Integer");
        customMap.put("LONGTEXT","String");
        customMap.put("BIT","Integer");
        customMap.put("MEDIUMINT","Integer");
        customMap.put("SMALLINT","Integer");
        customMap.put("ENUM","String");
        customMap.put("DECIMAL","Integer");
        customMap.put("tableRemovePrefixes","");
    }


    //获取数据库连接
    public static Connection getConnection(ConfigurationInfo configurationInfo) throws Exception {
        String url = String.format("jdbc:mysql://%s:%s?useUnicode=true&characterEncoding=utf8", configurationInfo.getIp(), configurationInfo.getPort());
        Properties props = new Properties();
        props.put("remarksReporting", "true");//获取数据库的备注信息
        props.put("user", configurationInfo.getLoginName());
        props.put("password", configurationInfo.getPassword());
        Class.forName(configurationInfo.getDriver());//注册驱动
        return DriverManager.getConnection(url, props);
    }


    /**
     * 获取数据库列表
     *
     * @param db 连接数据库的信息
     */
    public static List<String> getSchemas(ConfigurationInfo db) throws Exception {
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
     * 获取Table信息
     * @param db
     * @param tableName
     * @return
     * @throws Exception
     */
    public static List<TableInfo> getTableInfo(ConfigurationInfo db,String tableName)throws Exception {
        //获取连接
        List<TableInfo> tableInfos = new ArrayList<>();
        Connection connection = getConnection(db);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet tables = metaData.getTables(tableName, "", null, new String[]{"TABLE"});
        while (tables.next()) {
            TableInfo tableInfo = new TableInfo();
            tableInfo.setTableName(tables.getString("TABLE_NAME"));
            tableInfo.setTableCat(tables.getString("TABLE_CAT"));
            tableInfo.setTableType(tables.getString("TABLE_TYPE"));
            tableInfo.setRemark(tables.getString("REMARKS"));
            tableInfos.add(tableInfo);
        }
        tables.close();
        connection.close();
        return tableInfos;
    }


    /**
     * 创建表数据
     * @param configurationInfo
     * @param dbName
     * @param tableInfo
     * @return
     */
    public static Boolean createTable(ConfigurationInfo configurationInfo,String dbName,TableInfo tableInfo) throws Exception{
        List<TableDetailInfo> detailInfos = tableInfo.getDetailInfos();
        Connection connection = getConnection(configurationInfo);
        Statement stmt = null;
        stmt = connection.createStatement();
        //切换数据库
        connection.setCatalog(dbName);
        connection.getCatalog();
        stmt.close();
        //需要重新获取stmt
        stmt = connection.createStatement();
        //创建表
        StringBuffer executeSql = new StringBuffer("create table "+tableInfo.getTableName()+" ( ");
        for (TableDetailInfo detailInfo : detailInfos) {
            StringBuffer fieldColumn = new StringBuffer();
            fieldColumn.append(detailInfo.getField()).append(" ").append(detailInfo.getFieldType())
                    .append("(").append(detailInfo.getFieldLength()).append(") ");
            if (!detailInfo.getIsNull()){
                fieldColumn.append("not null ");
            }
            if (detailInfo.getIsPrimaryKey() != null && detailInfo.getIsPrimaryKey()){
                fieldColumn.append("primary key ");
            }
            fieldColumn.append("comment \'").append(detailInfo.getFieldComment()).append("\',");
            executeSql.append(fieldColumn);
        }
        executeSql = new StringBuffer(executeSql.toString().substring(0, executeSql.length() - 1));
        executeSql.append(")").append("ENGINE=InnoDB DEFAULT CHARSET=utf8 comment \'").append(tableInfo.getRemark()+"\'");

        boolean execute = stmt.execute(executeSql.toString());
        return !execute;
    }


    /**
     * 查询表详细信息
     * @return
     */
    public static TableInfo getTableDetailInfo(ConfigurationInfo db,String dbName,String tableName) throws Exception{
        Connection connection = getConnection(db);
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet primaryKeys = metaData.getPrimaryKeys(null, "%", tableName);
        String primaryKey = "";
        while (primaryKeys.next()){
            primaryKey = primaryKeys.getString("COLUMN_NAME");
        }
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);
        List<TableDetailInfo> tableDetailInfos = new ArrayList<>();
        ResultSet columns = metaData.getColumns(dbName, null, tableName, null);
        while (columns.next()){
            TableDetailInfo tableDetailInfo = new TableDetailInfo();
            tableDetailInfo.setField(columns.getString("COLUMN_NAME"));
            tableDetailInfo.setFieldComment(columns.getString("REMARKS"));
            tableDetailInfo.setFieldType(columns.getString("TYPE_NAME"));
            tableDetailInfo.setFieldLength(Integer.valueOf(columns.getString("COLUMN_SIZE")));
            if ("YES".equals(columns.getString("IS_NULLABLE"))){
                tableDetailInfo.setIsNull(true);
            }else {
                tableDetailInfo.setIsNull(false);
            }
            if (columns.getString("COLUMN_NAME").equals(primaryKey)){
                tableDetailInfo.setIsPrimaryKey(true);
            }else {
                tableDetailInfo.setIsPrimaryKey(false);
            }
            tableDetailInfos.add(tableDetailInfo);
        }
        tableInfo.setDetailInfos(tableDetailInfos);
        columns.close();
        connection.close();
        primaryKeys.close();
        return tableInfo;
    }


    /**
     * 获取数据库中的表和字段构造实体类
     */
    public static List<Table> getDbInfo(ConfigurationInfo db,String databaseName,List<String> tableNames) throws Exception {
        //获取连接
        Connection connection = getConnection(db);
        //获取元数据
        DatabaseMetaData metaData = connection.getMetaData();
        //获取当前数据库中的所有表
        ResultSet tables = metaData.getTables(databaseName, null, null, new String[]{"TABLE"});
        //表集合
        List<Table> list = new ArrayList<>();

        //遍历数据库信息获取表
        while (tables.next()) {
            Table tab = new Table();
            //i.表名
            String tableName = tables.getString("TABLE_NAME");
            if (tableNames.size() != 0 && tableNames.indexOf(tableName) == -1) continue;
            //ii.类名 去除tb_  bl_前缀
            String className = removePrefix(tableName);
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
                String columnName = columns.getString("COLUMN_NAME");
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
                //String javaType = PropertiesUtils.customMap.get(dbType);
                String javaType = customMap.get(dbType).toString();
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
                columnList.add(cn);
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
        String prefix = customMap.get("tableRemovePrefixes").toString();
        String temp = tableName;
        String[] split = prefix.split(",");
        for (String pf : split) {
            temp = StringUtils.removePrefix(temp, pf, true);
        }
        return StringUtils.makeAllWordFirstLetterUpperCase(temp);
    }
}
