package com.pdx.controller;

import com.alibaba.fastjson.JSONObject;
import com.pdx.service.UserService;
import com.pdx.utils.JwtTokenUtil;
import com.pdx.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/19 2023/1/19
 * @Description:
 */
@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param jsonObject
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public Result loginOperate(@RequestBody JSONObject jsonObject) throws Exception {
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        Map<String, Object> resultMap = userService.loginOperate(email, password);
        return Result.ok().data(resultMap);
    }

    /**
     * 获取详情
     * @param accessToken
     * @return
     */
    @GetMapping("/detail/info")
    public Result userDetailInfo(@RequestParam(value = "accessToken")String accessToken){
        String userId = JwtTokenUtil.getUserId(accessToken);
        Map<String,Object> resultMap = userService.userDetailInfo(userId);
        return Result.ok().data(resultMap);
    }

    /**
     * 注册
     * @param jsonObject
     * @return
     */
    @PostMapping("/register")
    public Result registerOperate(@RequestBody JSONObject jsonObject){
        String nickName = jsonObject.getString("nickName");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String code = jsonObject.getString("code");
        Map<String,Object> resultMap = userService.registerOperate(nickName,email,password,code);
        return Result.ok().data(resultMap);
    }

    @PostMapping("/code")
    public Result codeInfo(@RequestBody JSONObject jsonObject){
        String email = jsonObject.getString("email");
        Map<String,Object> resultMap = userService.getCodeInfo(email);
        return Result.ok().data(resultMap);
    }
}
