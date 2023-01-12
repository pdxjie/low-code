package com.pdx.entity;

import lombok.Data;

import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@Data
public class TableInfo {

    private String tableName;

    private String tableType;

    private String remark;

    private String tableCat;

    private List<TableDetailInfo> detailInfos;
}
