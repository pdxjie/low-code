package com.pdx.service.impl;

import com.pdx.service.DBService;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@Service
public class DBServiceImpl implements DBService {
    @Override
    public List<String> allTables(Connection connection) {
        List<String> tables = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            //获取数据库列表
            ResultSet resultSet = metaData.getCatalogs();
            while (resultSet.next()){
                String tableName = resultSet.getString(1);
                tables.add(tableName);
            }
            resultSet.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        return tables;
    }
}
