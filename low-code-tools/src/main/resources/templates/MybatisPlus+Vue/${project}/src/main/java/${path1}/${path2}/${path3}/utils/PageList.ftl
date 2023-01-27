package ${path1}.${path2}.${path3}.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author XX
 * @date ${.now?string('yyyy/MM/dd')}
 * @Description: PageList分页处理类
 */
@Data
public class PageList<T extends Serializable> {

    /**
     * 总记录数
     */
    private int totalCount;

    /**
     * 总页数
     */
    private int totalPageCount;

    /**
     * 开始查询的页数
     */
    private int startPageNo;

    /**
     * 查询的偏移量【每页查询的最大条数】
     */
    private int pageSize;

    /**
     * 对象集合
     */
    private List<T> list;
}