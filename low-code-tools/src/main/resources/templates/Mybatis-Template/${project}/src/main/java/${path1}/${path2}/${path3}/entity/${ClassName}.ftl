package ${pPackage}.entity;

import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @Author:XX
* @date ${.now?string('yyyy/MM/dd')}
* @Description: ${ClassName} 实体类
*/
@Data
public class ${ClassName} implements Serializable{

    <#list table.columns as column>
        //${column.columnComment}
        private ${column.columnType} ${column.columnName2};

    </#list>
}