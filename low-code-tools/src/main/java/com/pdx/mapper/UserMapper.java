package com.pdx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pdx.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
