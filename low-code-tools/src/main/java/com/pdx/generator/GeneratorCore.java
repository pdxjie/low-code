package com.pdx.generator;

import com.pdx.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description:
 */
@Data
public class GeneratorCore {
    //模板路径
    private String templatePath;
    //生成代码路径
    private String outPath;

    private Configuration configuration;

    public GeneratorCore(String templatePath, String outPath) throws Exception {
        this.templatePath = templatePath;
        this.outPath = outPath;
        configuration = new Configuration();
        //指定模板加载器
        FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(templatePath));
        configuration.setTemplateLoader(fileTemplateLoader);
    }


    /**
     * 代码生成
     * 1. 扫描模板路径下的所有的模板
     * 2. 对每个模板进行文件生成（数据模型）
     */
    public void scanAndGenerator(Map<String,Object> dataMap) throws Exception{
        //根据模板路径找到此路径下的所有模板文件
        List<File> files = FileUtils.searchAllFile(new File(templatePath));
        //对每个模板文件进行生成
        for (File file : files) {
            executeGenerator(dataMap,file);
        }
    }

    /**
     * 执行生成代码
     * @param dataMap 数据模型
     * @param file 模板文件
     */
    private void executeGenerator(Map<String, Object> dataMap, File file) throws Exception{
        //文件路径的处理
        String templateFileName = file.getAbsolutePath().replace(this.templatePath,"");
        String outFileName = processTemplateString(templateFileName, dataMap);
        //读取文件模板
        Template template = configuration.getTemplate(templateFileName);
        template.setOutputEncoding("UTF-8");
        //创建文件
        File mkdir = FileUtils.mkdir(outPath, outFileName.replace("ftl","java").replace("vuei","vue"));
        //模板处理
        FileWriter writer = new FileWriter(mkdir);
        template.process(dataMap,writer);
        writer.close();
    }

    /**
     * 处理模板字符串
     * @param templateString
     * @param dataModel
     * @return
     * @throws Exception
     */
    public String processTemplateString(String templateString,Map<String,Object> dataModel) throws Exception{
        StringWriter outWriter = new StringWriter();
        Template template = new Template("template",new StringReader(templateString),configuration);
        template.process(dataModel,outWriter);
        return outWriter.toString();
    }
}
