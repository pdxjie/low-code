package ${pPackage}.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @Author:XX
* @date ${.now?string('yyyy/MM/dd')}
* @Description: ${ClassName} 实体类
*/
@Data
@TableName(value = "${table.name}")
public class ${ClassName} implements Serializable{
<#if table.columns?exists && table.columns?size gt 0>
    <#list table.columns as column>
        //${column.columnComment}
        <#if column.columnKey??>
            @TableId(type = IdType.ID_WORKER_STR)
        </#if>
        private ${column.columnType} ${column.columnName2};

    </#list>
</#if>
}