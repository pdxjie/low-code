package com.pdx.utils;

import com.pdx.entity.dto.FolderDto;
import org.springframework.cglib.core.CollectionUtils;

import javax.swing.filechooser.FileSystemView;
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

    /**
     * 获取本地文件目录
     * @param path
     * @return
     */
    public static List<FolderDto> folderDtos(String path) throws IOException {
        List<FolderDto> fileName = new ArrayList<>();
        if ("".equals(path) || "请选择代码生成目录".equals(path)){
            FileSystemView fileSystemView = FileSystemView.getFileSystemView();

            File[] listRoots = File.listRoots();
            for (int i = 0; i < listRoots.length; i++) {
                String name = fileSystemView.getSystemDisplayName(listRoots[i]).toString();
                if (!name.isEmpty()){
                    int begin = name.lastIndexOf("(");
                    int end = name.lastIndexOf(")");
                    name = name.substring(begin+1,end);
                    FolderDto folderDto = new FolderDto();
                    folderDto.setValue(name);
                    folderDto.setLabel(name);
                    folderDto.setIsLeaf(false);
                    folderDto.setChildren(folderDtos(name));
                    fileName.add(folderDto);
                }
            }
        }else {
            //获取指定目录下一级的所有文件夹
            String dirName= path;
            File file = new File(dirName);
            String[] list1 = file.list();
            File[] files = file.listFiles();
            if(file.isDirectory() && !(file.list() == null) && file.list().length >0) {
                String[] list = file.list();
                for(int i=0;i<list.length;i++) {
                    File file2 = new File(dirName+"\\"+list[i]);
                    String path2 = file2.getPath();
                    if (path2.split("\\\\").length>2){
                        break;
                    }
                    if(file2.isDirectory()) {
                        FolderDto folderDto = new FolderDto();
                        folderDto.setValue(list[i]);
                        folderDto.setLabel(list[i]);
                        folderDto.setIsLeaf(true);
                        folderDto.setChildren(folderDtos(path2));
                        fileName.add(folderDto);
                    }
                }
            }
//            else {
//
//            }
        }
        return fileName;
    }
}
