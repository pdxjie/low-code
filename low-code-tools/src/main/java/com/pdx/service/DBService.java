package com.pdx.service;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
public interface DBService {


    List<String> allTables(Connection connection);
}
