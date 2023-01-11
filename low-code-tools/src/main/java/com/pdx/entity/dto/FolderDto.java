package com.pdx.entity.dto;

import lombok.Data;

import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/10 2023/1/10
 * @Description:
 */
@Data
public class FolderDto {
    private String value;

    private String label;

    private Boolean isLeaf;

    private List<FolderDto> children;
}
