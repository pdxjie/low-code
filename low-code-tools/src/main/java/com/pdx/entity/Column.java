package com.pdx.entity;

import lombok.Data;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 列对象:数据库的列
 */
@Data
public class Column {
    /**
     * 列名称
     */
	private String columnName;
    /**
     * 抽象的属性名称
     */
	private String columnName2;
    /**
     * java类型
     */
    private String columnType;
    /**
     * 列数据库类型
     */
	private String columnDbType;
    /**
     * 列备注
     */
	private String columnComment;
    /**
     * 是否是主键
     */
	private String columnKey;
}
