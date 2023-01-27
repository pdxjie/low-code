package ${path1}.${path2}.${path3}.utils;

/***
 * 返回码
 */
public enum ResultCode {

    success                 ( 1, "成功"),

    wrong                   ( 0, "无查询结果"),

    failed                  (-1, "失败"),
    ;

    private Integer code;

    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}