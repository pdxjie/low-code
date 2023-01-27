package ${path1}.${path2}.${path3}.mapper;

import ${path1}.${path2}.${path3}.entity.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
/**
 * @Author: XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description:
 */
@Mapper
public interface ${ClassName}Mapper{
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
   int batchDelete(List<Object> list);
   
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
    List<${ClassName}> selectPage (@Param("${ClassName?lower_case}") ${ClassName} ${ClassName?lower_case}, @Param("page") Integer page, @Param("pageSize") Integer pageSize);
   
    /**
    * [总量查询]
    **/
    int total(${ClassName} ${ClassName?lower_case});

}
