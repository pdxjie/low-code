package com.pdx.entity;

import lombok.Data;

/**
 * @Author: 派大星
 * @Date: 2023/01/12 2023/1/12
 * @Description:
 */
@Data
public class TableDetailInfo {

    private String field;

    private String fieldComment;

    private Integer fieldLength;

    private String fieldType;

    private Boolean isPrimaryKey;

    private Boolean isNull;
}
