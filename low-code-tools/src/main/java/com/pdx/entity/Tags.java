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
@TableName("lc_tags")
public class Tags {
    //主键
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    //用户id
    private String userId;
    //标签名称
    private String tagsName;
    //创建时间
    private Timestamp createTime;


}
