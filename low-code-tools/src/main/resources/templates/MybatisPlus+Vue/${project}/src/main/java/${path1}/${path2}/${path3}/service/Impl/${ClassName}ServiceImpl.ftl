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
}