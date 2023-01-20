package com.pdx.service;

import com.alibaba.fastjson.JSONObject;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
public interface DBService {

    Boolean createTable(JSONObject jsonObject);

}
