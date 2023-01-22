package com.pdx.controller;

import com.alibaba.fastjson.JSONObject;
import com.pdx.service.UserService;
import com.pdx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/22 2023/1/22
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/list/condition")
    public Result listByCondition(@RequestBody JSONObject jsonObject){
        String nickName = jsonObject.getString("nickName");
        String beginTime = jsonObject.getString("beginTime");
        String endTime = jsonObject.getString("endTime");
        Map<String,Object> resultMap = userService.listByCondition(nickName,beginTime,endTime);
        return Result.ok().data(resultMap);
    }
}
