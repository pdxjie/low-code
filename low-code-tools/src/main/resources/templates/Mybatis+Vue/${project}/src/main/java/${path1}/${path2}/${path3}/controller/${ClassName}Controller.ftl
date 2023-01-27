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
import org.apache.commons.lang3.StringUtils;

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
    * 参数请求报文:
    *
    * {
    *   "paramOne": 1,
    *   "paramTwo": "xxx"
    * }
    */
    @PostMapping(value = "/insert")
    public ApiResult insert (@RequestBody ${ClassName} ${ClassName?lower_case}) {
        service.insert(${ClassName?lower_case});
        return new ApiResult<>(ResultCode.success.getCode(), null, ResultCode.success.getMessage());
    }

    /**
    * 参数请求报文:
    *
    * [
    *     {
    *       "paramOne": 1,
    *       "paramTwo": "xxx"
    *     },
    *     {
    *       "paramOne": 1,
    *       "paramTwo": "xxx"
    *     }
    * ]
    */
    @PostMapping(value = "/batchInsert")
    public ApiResult batchInsert (@RequestBody List<${ClassName}> list) {
        service.batchInsert(list);
        return new ApiResult<>(ResultCode.success.getCode(), null, ResultCode.success.getMessage());
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
    public ApiResult update (@RequestBody ${ClassName} ${ClassName?lower_case}) {
        service.update(${ClassName?lower_case});
        return new ApiResult<>(ResultCode.success.getCode(), null, ResultCode.success.getMessage());
    }

    /**
    * 参数请求报文:
    *
    * {
    *   "key":1
    * }
    */
    @DeleteMapping(value = "/delete")
    public ApiResult delete (@RequestBody Object key) {
        service.delete(key);
        return new ApiResult<>(ResultCode.success.getCode(), null, ResultCode.success.getMessage());
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
    public ApiResult batchDelete (@RequestBody List<Object> keys) {
        service.batchDelete(keys);
        return new ApiResult<>(ResultCode.success.getCode(), null, ResultCode.success.getMessage());
    }

    /**
    * 参数请求报文:
    *
    * {
    *   "key":1
    * }
    */
    @RequestMapping(value = "/selectByKey")
    public ApiResult selectByKey (@RequestBody Object key) {
        ${ClassName} ${ClassName?lower_case} = service.selectByKey(key);
        return new ApiResult<>(ResultCode.success.getCode(), ${ClassName?lower_case}, ResultCode.success.getMessage());
    }

    /***
    * 参数请求报文:
    *
    * {
    *   "paramOne": 1,
    *   "paramTwo": "xxx"
    * }
    */
    @RequestMapping(value = "/selectList")
    public ApiResult selectList (@RequestBody ${ClassName} ${ClassName?lower_case}) {
        List<${ClassName}> result = service.selectList(${ClassName?lower_case});
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
    public ApiResult selectPage (@RequestBody ${ClassName} ${ClassName?lower_case},@RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
        PageList<${ClassName}> pageList = service.selectPage(${ClassName?lower_case}, pageNo, pageSize);
        int total = service.total(${ClassName?lower_case});
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("total",total);
        result.put("records",pageList);
        return new ApiResult<>(ResultCode.success.getCode(), resultMap, ResultCode.success.getMessage());
    }

    /***
    * 表单查询请求
    * @param searchParams Bean对象JSON字符串
    * @param page         页码
    * @param limit        每页显示数量
    */
    @RequestMapping(value = "/formPage")
    public String formPage (@RequestParam(value = "searchParams", required = false) String  searchParams,
    @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
    @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        ${ClassName} query = new ${ClassName}();
        if (StringUtils.isNotBlank(searchParams)) {
        JSONObject object = JSON.parseObject(searchParams);
        query = object.toJavaObject(${ClassName}.class);
        }
    
        PageList<${ClassName}> pageList = service.selectPage(query, page, limit);
        JSONObject response = new JSONObject();
        response.put("code" , 0);
        response.put("msg"  , "");
        response.put("data" , null != pageList.getList() ? pageList.getList() : new JSONArray());
        response.put("count", pageList.getTotalCount());
        return response.toString();
    }

    /***
    * 表单查询
    */
    @RequestMapping(value = "/formSelectByKey")
    public ${ClassName} formSelectByKey(@RequestParam(value = "key", required = false) String  key) {
        return service.selectByKey(key);
    }

    /***
    * 表单插入
    * @param params Bean对象JSON字符串
    */
    @RequestMapping(value = "/formInsert")
    public String formInsert(@RequestParam(value = "params", required = false) String  params) {
        ${ClassName} insert = null;
        if (StringUtils.isNotBlank(params)) {
        JSONObject object = JSON.parseObject(params);
        insert = object.toJavaObject(${ClassName}.class);
        }
    
        int rows = service.insert(insert);
    
        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "添加成功" : "添加失败");
        return response.toString();
    }

    /***
    * 表单修改
    * @param params Bean对象JSON字符串
    */
    @RequestMapping(value = "/formUpdate")
    public String formUpdate(@RequestParam(value = "params", required = false) String  params) {
        ${ClassName} update = null;
        if (StringUtils.isNotBlank(params)) {
        JSONObject object = JSON.parseObject(params);
        update = object.toJavaObject(${ClassName}.class);
        }
    
        int rows = service.update(update);
    
        JSONObject response = new JSONObject();
        response.put("code" , rows);
        response.put("msg"  , rows > 0 ? "修改成功" : "修改失败");
        return response.toString();
    }

    /***
    * 表单删除
    */
    @RequestMapping(value = "/formDelete")
    public int formDelete(@RequestParam(value = "key", required = false) String  key) {
        return service.delete(key);
    }
}