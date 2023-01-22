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
@TableName(value = "lc_user")
public class User {

    //主键
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    //昵称
    private String nickName;
    //QQ邮箱
    private String email;
    //密码
    private String password;
    //加密盐
    private String salt;
    //头像
    private String avatar;
    //用户描述
    private String description;
    //暂定
    private String tags_id;
    //注册时间
    private Timestamp registerTime;
    //更新时间
    private Timestamp updateTime;
    //角色名称
    private String role;
}
