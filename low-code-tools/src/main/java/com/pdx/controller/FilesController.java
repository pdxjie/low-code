package com.pdx.controller;

import com.alibaba.fastjson.JSONObject;
import com.pdx.entity.dto.FolderDto;
import com.pdx.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: 派大星
 * @Date: 2023/01/09 2023/1/9
 * @Description:
 */
@RestController
@CrossOrigin
@RequestMapping("/file")
public class FilesController {

    @PostMapping("/Folders")
    public Map fileFolders(@RequestBody JSONObject jsonObject) throws Exception {
        Map folderMap = new HashMap<>();
        List<FolderDto> fileName = new ArrayList<>();
        String files = jsonObject.getString("files");
        List<FolderDto> folderDtos = FileUtils.folderDtos(files);
        folderMap.put("folders",folderDtos);
        return folderMap;
    }
}
