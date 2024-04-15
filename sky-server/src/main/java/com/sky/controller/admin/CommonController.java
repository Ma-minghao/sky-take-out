package com.sky.controller.admin;


import com.sky.result.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 通用Controller
 */
@RestController
@RequestMapping("/admin/common")
@ApiOperation("通用接口")
public class CommonController {

    /**
     * 文件上传
     * @param File
     * @return
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile File) {


        return null;
    }
}
