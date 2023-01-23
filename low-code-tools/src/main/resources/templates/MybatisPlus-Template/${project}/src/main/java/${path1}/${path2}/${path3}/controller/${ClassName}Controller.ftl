package ${path1}.${path2}.${path3}.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import ${path1}.${path2}.${path3}.entity.*;
import ${path1}.${path2}.${path3}.utils.ApiResult;
import ${path1}.${path2}.${path3}.utils.PageList;
import ${path1}.${path2}.${path3}.utils.ResultCode;
import ${path1}.${path2}.${path3}.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

/**
 * @Author: XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: 前端控制层
 */
@RestController
@RequestMapping(value = "/${ClassName?lower_case}/api")
public class ${ClassName}Controller {

    @Autowired
    ${ClassName}Service service;

    /**
     * 具体字段请根据实际情况处理
     * 参数请求响应数据:
     *
     * {
     *   "paramOne": 1,
     *   "paramTwo": "xxx"
     * }
     */
    @PostMapping(value = "/insert")
    public ApiResult insert (@RequestBody ${ClassName} parameter) {
        int affectRows = service.insert(parameter);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getMessage());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "paramOne": 1,
     *   "paramTwo": "xxx"
     * }
     */
    @PostMapping(value = "/update")
    public ApiResult update (@RequestBody ${ClassName} parameter) {
        int affectRows = service.update(parameter);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getMessage());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "key":1
     * }
     */
    @PostMapping(value = "/delete")
    public ApiResult delete (@RequestBody String key) {
        int affectRows = service.delete(key);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getMessage());
    }

    /**
     * 参数请求报文:
     *
     * [
     *     9,
     *     11
     * ]
     */
    @PostMapping(value = "/batchDelete")
    public ApiResult batchDelete (@RequestBody List<String> keys) {
        int affectRows = service.batchDelete(keys);
        return new ApiResult<>(ResultCode.success.getCode(), affectRows, ResultCode.success.getMessage());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "key":1
     * }
     */
    @PostMapping(value = "/selectByKey")
    public ApiResult selectByKey (@RequestBody String key) {
        ${ClassName} result = service.selectByKey(key);
        return new ApiResult<>(ResultCode.success.getCode(), result, ResultCode.success.getMessage());
    }

    /***
     * 参数请求报文:
     *
     * {
     *   "paramOne": 1,
     *   "paramTwo": "xxx"
     * }
     */
    @RequestMapping(value = "/selectPage")
    public ApiResult selectPage (@RequestBody JSONObject object) {
        Integer page = (Integer) object.getOrDefault("page", 1);
        Integer pageSize = (Integer) object.getOrDefault("pageSize", 10);
        Map<String ,Object> resultMap = service.selectPage(page,pageSize);
        return new ApiResult<>(ResultCode.success.getCode(), resultMap, ResultCode.success.getMessage());
    }
}