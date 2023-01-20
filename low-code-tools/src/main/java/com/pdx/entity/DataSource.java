package com.pdx.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@Data
@TableName("lc_data_source")
public class DataSource {

    //主键
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    //用户id
    private String userId;
    //数据源IP
    private String sourceIp;
    //数据源图标
    private String sourceCover;
    //数据源名称
    private String sourceName;
    //数据源账户
    private String sourceAccount;
    //数据源密码
    private String sourcePassword;
    //数据源端口号
    private String sourcePort;
    //创建时间
    private Timestamp createTime;
    //更新时间
    private Timestamp updateTime;
    //连接次数
    private Integer connectNum;
    //数据库数量
    private Integer databaseNum;
    private String salt;

}
