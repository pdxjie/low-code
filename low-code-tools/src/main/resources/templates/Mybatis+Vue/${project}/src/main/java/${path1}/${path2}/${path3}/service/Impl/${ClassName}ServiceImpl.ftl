package ${path1}.${path2}.${path3}.service.Impl;

import ${path1}.${path2}.${path3}.entity.*;
import ${path1}.${path2}.${path3}.mapper.*;
import ${path1}.${path2}.${path3}.service.*;
import ${path1}.${path2}.${path3}.utils.*;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


/**
 * @Author: XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: Service接口实现类
*/
@Service
public class ${ClassName}ServiceImpl implements ${ClassName}Service {

    @Resource
    private ${ClassName}Mapper mapper;

    @Override
    public int insert(${ClassName} ${ClassName?lower_case}) {
        return mapper.insert(${ClassName?lower_case});
    }
    
    @Override
    public int batchInsert(List<${ClassName}> list) {
        return mapper.batchInsert(list);
    }
    
    @Override
    public int update(${ClassName} ${ClassName?lower_case}) {
        return mapper.update(${ClassName?lower_case});
    }
    
    @Override
    public int delete(Object key) {
        return mapper.delete(key);
    }
    
    @Override
    public int batchDelete(List<Object> keys) {
        return mapper.batchDelete(keys);
    }

    @Override
    public ${ClassName} selectByKey(Object key) {
        return mapper.selectByKey(key);
    }

    @Override
    public List<${ClassName}> selectList(${ClassName} ${ClassName?lower_case}) {
        return mapper.selectList(${ClassName?lower_case});
    }

    @Override
    public PageList<${ClassName}> selectPage(${ClassName} ${ClassName?lower_case}, Integer offset, Integer pageSize) {
        PageList<${ClassName}> pageList = new PageList<>();
    
        int total = this.total(${ClassName?lower_case});
    
        Integer totalPage;
        if (total % pageSize != 0) {
            totalPage = (total /pageSize) + 1;
        } else {
            totalPage = total /pageSize;
        }
    
        int page = (offset - 1) * pageSize;
    
        List<${ClassName}> list = mapper.selectPage(${ClassName?lower_case}, page, pageSize);
    
        pageList.setList(list);
        pageList.setStartPageNo(offset);
        pageList.setPageSize(pageSize);
        pageList.setTotalCount(total);
        pageList.setTotalPageCount(totalPage);
        return pageList;
    }

    @Override
    public int total(${ClassName} ${ClassName?lower_case}) {
        return mapper.total(${ClassName?lower_case});
    }
}