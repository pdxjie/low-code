package com.pdx.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
@Data
public class Result {

    private Boolean success;
    private Integer code;
    private String message;
    private Map<String,Object> data= new HashMap<>();
    /*
    构造方法私有化，里面的方法都为静态方法
    达到保护属性的作用
     */
    private Result(){

    }
    /*
    使用链式编程
    该部分在项目组中一般是项目组长写好的
     */
    public static Result ok(){
        Result result=new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS.getCode());
        result.setMessage(ResultCode.SUCCESS.getMessage());
        return result;
    }

    public static Result error(){
        Result result=new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.ERROR.getCode());
        result.setMessage(ResultCode.ERROR.getMessage());
        return result;
    }
    /*
    自定义返回成功与否
     */
    public Result success(Boolean success){
        this.setSuccess(success);
        return this;
    }
    public Result message(String message){
        this.setMessage(message);
        return this;
    }
    public Result code(Integer code){
        this.setCode(code);
        return this;
    }
    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    public Result data(Map<String,Object> map){
        this.setData(map);
        return this;
    }
}
