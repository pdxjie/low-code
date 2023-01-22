package ${path1}.${path2}.${path3}.service;

import com.baomidou.mybatisplus.extension.service.IService;
import ${path1}.${path2}.${path3}.entity.*;
import java.util.*;

/**
 * @Author: XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: Service接口
 */
public interface ${ClassName}Service extends IService<${ClassName}> {

    int insert(${ClassName} parameter);

    int update(${ClassName} parameter);

    int delete(String key);

    int batchDelete(List<String> keys);

    ${ClassName} selectByKey(String key);

    Map<String ,Object> selectPage(Integer page,Integer pageSize);
}