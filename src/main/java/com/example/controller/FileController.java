//package com.example.controller;
//
//import cn.hutool.core.io.FileUtil;
//import cn.hutool.core.util.StrUtil;
//import com.example.common.Result;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//import java.net.URLEncoder;
//import java.util.List;
//
///**
// *  文件上传接口
// */
//@RestController
//@RequestMapping("/files")
//public class FileController {
//
//    // 文件上传存储路径
//    private static final String filePath = System.getProperty("user.dir") + "/files/";
//
//    /**
//     * 文件上传
//     */
//    @PostMapping("/upload")
//    public Result upload(MultipartFile file) {
//        synchronized (FileController.class) {
//            //获取当前时间戳
//            String flag = System.currentTimeMillis() + "";
//            //获取原始文件名（就是你上传的文件本身的名字）
//            String fileName = file.getOriginalFilename();
//            try {
//                //如果没有file文件夹，会给你在项目根目录下创建一个file文件夹
//                if (!FileUtil.isDirectory(filePath)) {
//                    FileUtil.mkdir(filePath);
//                }
//                // 文件存储形式：时间戳-文件名
//                FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
//                System.out.println(fileName + "--上传成功");
//                Thread.sleep(1L);
//            } catch (Exception e) {
//                System.err.println(fileName + "--文件上传失败");
//            }
//            return Result.success(flag);
//        }
//    }
//
//
//    /**
//     * 获取文件
//     */
//    @GetMapping("/{flag}")
//    public void avatarPath(@PathVariable String flag, HttpServletResponse response) {
//        if (!FileUtil.isDirectory(filePath)) {
//            FileUtil.mkdir(filePath);
//        }
//        OutputStream os;
//        List<String> fileNames = FileUtil.listFileNames(filePath);
//        String avatar = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
//        try {
//            if (StrUtil.isNotEmpty(avatar)) {
//                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(avatar, "UTF-8"));
//                response.setContentType("application/octet-stream");
//                byte[] bytes = FileUtil.readBytes(filePath + avatar);
//                os = response.getOutputStream();
//                os.write(bytes);
//                os.flush();
//                os.close();
//            }
//        } catch (Exception e) {
//            System.out.println("文件下载失败");
//        }
//    }
//
//}
//

package com.example.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.example.common.Result;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/files")
public class FileController {

    // 文件上传存储路径
    private static final String filePath = System.getProperty("user.dir") + "/files/";

    /**
     * 文件上传接口
     */
    @PostMapping("/upload")
    public Result upload(@RequestParam("file") MultipartFile file) {
        synchronized (FileController.class) {
            String flag = String.valueOf(System.currentTimeMillis());
            String fileName = file.getOriginalFilename();
            try {
                if (!FileUtil.isDirectory(filePath)) {
                    FileUtil.mkdir(filePath);
                }
                FileUtil.writeBytes(file.getBytes(), filePath + flag + "-" + fileName);
                System.out.println(fileName + " 上传成功");
                Thread.sleep(1L);
            } catch (Exception e) {
                System.err.println(fileName + " 文件上传失败");
                return Result.error("文件上传失败");
            }
            return Result.success(flag);
        }
    }

    /**
     * 获取文件接口
     */
    @GetMapping("/{flag}")
    public ResponseEntity<byte[]> getFile(@PathVariable String flag) {
        if (!FileUtil.isDirectory(filePath)) {
            FileUtil.mkdir(filePath);
        }
        List<String> fileNames = FileUtil.listFileNames(filePath);
        String avatar = fileNames.stream()
                .filter(name -> name.contains(flag))
                .findAny()
                .orElse("");

        if (StrUtil.isNotEmpty(avatar)) {
            try {
                byte[] bytes = FileUtil.readBytes(filePath + avatar);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + URLEncoder.encode(avatar, "UTF-8"));
                headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
                return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
            } catch (IOException e) {
                System.out.println("文件下载失败");
            }
        }

        return ResponseEntity.notFound().build();
    }
}

