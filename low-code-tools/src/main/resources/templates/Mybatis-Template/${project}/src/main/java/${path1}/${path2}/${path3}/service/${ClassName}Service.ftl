package ${path1}.${path2}.${path3}.service;

import ${path1}.${path2}.${path3}.entity.*;
import java.util.*;
import com.pdx.book.utils.PageList;
/**
 * @Author: XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: Service接口
 */
public interface ${ClassName}Service{

    /**
    * [新增]
    **/
    int insert(${ClassName} ${ClassName?lower_case});
    
    /**
    * [批量新增]
    **/
    int batchInsert(List<${ClassName}> list);
    
    /**
    * [更新]
    **/
    int update(${ClassName} ${ClassName?lower_case});
    
    /**
    * [删除]
    **/
    int delete(Object key);
    
    /**
    * [批量删除]
    **/
    int batchDelete(List<Object> keys);

    /**
    * [主键查询]
    **/
    ${ClassName} selectByKey(Object key);

    /**
    * [条件查询]
    **/
    List<${ClassName}> selectList (${ClassName} ${ClassName?lower_case});

    /**
    * [分页条件查询]
    **/
    PageList<${ClassName}> selectPage (${ClassName} ${ClassName?lower_case}, Integer page, Integer pageSize);

    /**
    * [总量查询]
    **/
    int total(${ClassName} ${ClassName?lower_case});
}