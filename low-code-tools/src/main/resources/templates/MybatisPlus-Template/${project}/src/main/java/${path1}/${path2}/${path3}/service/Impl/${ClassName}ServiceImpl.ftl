package ${path1}.${path2}.${path3}.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${path1}.${path2}.${path3}.entity.*;
import ${path1}.${path2}.${path3}.mapper.*;
import ${path1}.${path2}.${path3}.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @Author: 派大星
 * @Date: 2023/01/20 2023/1/20
 * @Description:
 */
@Service
public class ${ClassName}ServiceImpl extends ServiceImpl<${ClassName}Mapper, ${ClassName}> implements ${ClassName}Service {

    @Resource
    private ${ClassName}Mapper mapper;

    @Override
    public Map<String, Object> selectPage(Integer page, Integer pageSize) {
        Page<${ClassName}> pageOne = new Page<>(page,pageSize);
        QueryWrapper<${ClassName}> wrapper = new QueryWrapper<>();
        IPage<${ClassName}> iPage = mapper.selectPage(pageOne,wrapper);
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("records",iPage.getRecords());
        resultMap.put("total",iPage.getTotal());
        return resultMap;
    }

    @Override
    public int insert(${ClassName} parameter) {
        int result = mapper.insert(parameter);
        return result;
    }

    @Override
    public int update(${ClassName} parameter) {
        int result = mapper.updateById(parameter);
        return result;
    }

    @Override
    public int delete(String key) {
        int result = mapper.deleteById(key);
        return result;
    }

    @Override
    public int batchDelete(List<String> keys) {
        int result = mapper.deleteBatchIds(keys);
        return result;
    }

    @Override
    public  ${ClassName} selectByKey(String key) {
        ${ClassName} result = mapper.selectById(key);
        return result;
    }
}