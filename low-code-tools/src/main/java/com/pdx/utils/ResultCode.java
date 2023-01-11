package com.pdx.utils;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
public enum ResultCode implements CustomizeResultCode{
    /* 成功 */
    SUCCESS(200, "成功"),
    /*错误*/
    ERROR(400,"请求失败"),
    ;
    private Integer code;
    private String message;
    ResultCode(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
