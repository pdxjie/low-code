package com.pdx.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description: 文件工具类
 */
@SuppressWarnings("all")
public class FileUtils {
    // 得到相对路径
    public static String getRelativePath(File baseDir, File file) {
        if (baseDir.equals(file)) {
            return "";
        }
        if (baseDir.getParentFile() == null) {
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        }
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length() + 1);
    }

    //查询某个目录下的所有文件
    public static List<File> searchAllFile(File dir) throws IOException {
        ArrayList arrayList = new ArrayList();
        searchFiles(dir, arrayList);
        return arrayList;
    }

    //递归获取某个目录下的所有文件
    public static void searchFiles(File dir, List<File> collector) throws IOException {
        if (dir.isDirectory()) {
            File[] subFiles = dir.listFiles();
            for (int i = 0; i < subFiles.length; i++) {
                searchFiles(subFiles[i], collector);
            }
        } else {
            collector.add(dir);
        }
    }

    //创建文件
    public static File mkdir(String dir, String file) {
        if (dir == null) {
            throw new IllegalArgumentException("dir must be not null");
        }
        File result = new File(dir, file);
        if (result.getParentFile() != null) {
            result.getParentFile().mkdirs();
        }
        return result;
    }
}
