package com.pdx.entity.dto;

import com.pdx.entity.Tags;
import lombok.Data;

import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@Data
public class UserDetailDto {

    private String userId;

    private String avatar;

    private String nickName;

    private String description;

    private List<Tags> tags;

    private String role;
}
