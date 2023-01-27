package ${path1}.${path2}.${path3}.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import javax.servlet.http.HttpServletRequest;
/**
 * @Author: XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: 前端控制层
 */
@RestController
@RequestMapping(value = "/${ClassName?lower_case}/api")
public class ${ClassName}Controller {

    @Autowired
    ${ClassName}Service ${ClassName?uncap_first}Service;

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
        ${ClassName?uncap_first}Service.save(parameter);
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
    public ApiResult update (@RequestBody ${ClassName} parameter) {
        ${ClassName?uncap_first}Service.updateById(parameter);
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
    public ApiResult delete (@RequestParam(name="id",required=true) String id) {
        ${ClassName?uncap_first}Service.removeById(id);
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
    public ApiResult batchDelete (@RequestParam(name="ids",required=true) String ids) {
        ${ClassName?uncap_first}Service.removeByIds(Arrays.asList(ids.split(",")));
        return new ApiResult<>(ResultCode.success.getCode(), null, ResultCode.success.getMessage());
    }

    /**
     * 参数请求报文:
     *
     * {
     *   "key":1
     * }
     */
    @GetMapping(value = "/selectByKey")
    public ApiResult selectByKey (@RequestParam(value = "id") String id) {
        ${ClassName} ${ClassName?uncap_first} = ${ClassName?uncap_first}Service.getById(id);
        if(${ClassName?uncap_first}==null) {
            return new ApiResult<>(ResultCode.wrong.getCode(), null, ResultCode.wrong.getMessage());
        }
        return new ApiResult<>(ResultCode.success.getCode(), ${ClassName?uncap_first}, ResultCode.success.getMessage());
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
    public ApiResult selectPage (@RequestBody ${ClassName} ${ClassName?uncap_first},
                                @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
                                @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
                                HttpServletRequest req) {
        QueryWrapper<${ClassName}> queryWrapper = new QueryWrapper(${ClassName?uncap_first});
        Page<${ClassName}> page = new Page<${ClassName}>(pageNo, pageSize);
        IPage<${ClassName}> pageList = ${ClassName?uncap_first}Service.page(page, queryWrapper);
        return new ApiResult<>(ResultCode.success.getCode(), pageList, ResultCode.success.getMessage());
    }
}