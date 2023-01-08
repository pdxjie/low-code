package com.pdx.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 获取数据库中表的信息
 */
@Data
public class Table {
    /**
     *表名称
     */
    private String name;
    /**
     *处理后的表名称  实体类名
     */
    private String name2;
    /**
     *介绍
     */
    private String comment;
    /**
     *主键列
     */
    private String key;
    /**
     * 列集合
     */
	private List<Column> columns;
}
