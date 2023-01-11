package com.pdx.entity;

import lombok.Data;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@Data
public class ConfigurationInfo {

    private String ip;

    private String port;

    private String driver = "com.mysql.jdbc.Driver";

    private String loginName;

    private String password;



}
