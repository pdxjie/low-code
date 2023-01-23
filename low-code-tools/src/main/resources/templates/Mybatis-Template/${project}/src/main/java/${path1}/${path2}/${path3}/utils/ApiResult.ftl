package ${path1}.${path2}.${path3}.utils;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @Author:XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: Api响应类
 */
public class ApiResult<T> implements Serializable {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 数据
     */
    private T data;

    /**
     * 错误信息
     */
    private String msg;

    /***
     * 时间
     */
    private String time;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public ApiResult(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.time = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }
}