package com.sky.controller.admin;


import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 通用Controller
 */
@RestController
@RequestMapping("/admin/common")
@ApiOperation("通用接口")
@Slf4j
public class CommonController {

    @Autowired
    AliOssUtil aliOssUtil;
    /**
     * 文件上传
     * @param file
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传：{}", file);

        try {
            String originalFilename = file.getOriginalFilename();
            // 截取文件后缀
            String extension = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            // 生成新文件名
            String objectName = UUID.randomUUID().toString() + extension;
            aliOssUtil.upload(file.getBytes(), objectName);
            return Result.success(objectName);
        } catch (IOException e) {
            log.error("文件上传失败：{}", e.getMessage());
            e.printStackTrace();
        }

        return Result.error("文件上传失败");
    }
}
