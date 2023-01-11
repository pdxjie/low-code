package com.pdx.utils;

/**
 * @Author: 派大星
 * @Date: 2023/01/11 2023/1/11
 * @Description:
 */
public interface CustomizeResultCode {
    /*
   获取错误码
   @return 错误状态码
    */
    Integer getCode();
    /*
    获取错误信息
    @return 错误信息
     */
    String getMessage();
}
