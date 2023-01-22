package com.pdx.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pdx.entity.User;

import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
public interface UserService extends IService<User> {
    /**
     * 新增用户
     * @param user
     */
    void insertUser(User user);

    /**
     * 用户登录
     * @param email
     * @param password
     */
    Map<String,Object> loginOperate(String email, String password) throws Exception;

    Map<String, Object> userDetailInfo(String userId);

    /**
     * 注册
     * @param email
     * @param password
     * @param code
     * @return
     */
    Map<String,Object> registerOperate(String nickName,String email, String password, String code);

    /**
     * 获取验证码
     * @param email
     * @return
     */
    Map<String, Object> getCodeInfo(String email);

    Map<String, Object> listByCondition(String nickName,String beginTime,String endTime);
}
