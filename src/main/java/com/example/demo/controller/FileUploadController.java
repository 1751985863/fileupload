package com.example.demo.controller;

import com.aliyun.oss.model.OSSObjectSummary;
import com.example.demo.service.FileUploadService;
import com.example.demo.vo.FileUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 上传图片的接口
 */
@RestController
@RequestMapping("/storage/file")
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * 上传图片
     * @param uploadFile
     * @return
     * @throws Exception
     */
    @PostMapping("/upload")
    @ResponseBody
    public FileUploadResult upload(@RequestParam("file") MultipartFile uploadFile) throws Exception {
        return this.fileUploadService.upload(uploadFile);
    }

    /**
     * 删除图片
     * @param objectName
     * @return
     * @throws Exception
     */

    @DeleteMapping("/delete")
    @ResponseBody
    public FileUploadResult delete(@RequestParam("fileName") String objectName)
            throws Exception {
        return this.fileUploadService.delete(objectName);
    }

    /**
     * 查询所有图片
     * @return
     * @throws Exception
     */

    @GetMapping("/list")
    @ResponseBody
    public List<OSSObjectSummary> list()
            throws Exception {
        return this.fileUploadService.list();
    }

    /**
     * 下载图片
     * @param objectName
     * @param response
     * @throws IOException
     */

    @GetMapping("/download")
    @ResponseBody
    public void download(@RequestParam("fileName") String objectName, HttpServletResponse response) throws IOException {
        //通知浏览器以附件形式下载
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(objectName.getBytes(), "ISO-8859-1"));
        this.fileUploadService.exportOssFile(response.getOutputStream(),objectName);
    }
}
