package org.javaclimb.springbootmusic.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class FileController {
    @RequestMapping("/upload")
    public List<String> upload(@RequestParam("imgFile") MultipartFile imgFile, @RequestParam("songFile") MultipartFile songFile){
        List<String> list = new ArrayList<>();

        System.out.println("📤 文件上传中...");
        Path imgUploadDir = Paths.get("upload/img");
        Path songUploadDir = Paths.get("upload/song");
        if (!Files.exists(imgUploadDir)) {
            try {
                Files.createDirectories(imgUploadDir);

            } catch (IOException e) {
                System.err.println("❌ 创建上传目录失败: " + e.getMessage());
            }
        }

        if (!Files.exists(songUploadDir)) {
            try {
                Files.createDirectories(songUploadDir);
            }
            catch (IOException e) {
                System.err.println("❌ 创建上传目录失败: " + e.getMessage());
            }
        }

        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Path filePath = imgUploadDir.resolve(timestamp + "_" +Objects.requireNonNull(imgFile.getOriginalFilename()));
            list.add(String.valueOf(filePath));
            Files.write(filePath, imgFile.getBytes());
            System.out.println("📤 封面文件上传成功: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ 封面文件上传失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            Path filePath = songUploadDir.resolve(timestamp + "_" +Objects.requireNonNull(songFile.getOriginalFilename()));
            list.add(String.valueOf(filePath));
            Files.write(filePath, songFile.getBytes());
            System.out.println("📤 歌曲文件上传成功: " + filePath);
        }
        catch (IOException e) {
            System.err.println("❌ 歌曲文件上传失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("📤 文件上传完成");

        return list;
    }
    @RequestMapping
    public ResponseEntity<Resource> getFile(@RequestParam String path) throws IOException {
        Path filePath = Paths.get(path);

        // 1. 确保文件存在
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("文件未找到: " + path);
        }

        // 2. 解析 MIME 类型，若为空设置默认值
        String mimeType = Files.probeContentType(filePath);
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // 设置默认 MIME 类型
        }

        // 3. 返回文件响应
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(mimeType))
                .body(new FileSystemResource(filePath));
    }
    @DeleteMapping
    public ResponseEntity<String> deleteFile(@RequestParam String path) {
        String decodedPath = URLDecoder.decode(path, StandardCharsets.UTF_8);
        Path filePath = Paths.get(decodedPath);
        try{
            if (Files.exists(filePath)) {
                Files.delete(filePath);
                System.out.println("🗑️ 文件删除成功: " + filePath);
                return ResponseEntity.ok("文件删除成功: " + filePath);
            }
            else {
                System.err.println("❌ 文件删除失败: 文件不存在: " + filePath);
                return ResponseEntity.status(404).body("文件删除失败: 文件不存在: " + filePath);
            }
        }
        catch (IOException e) {
            System.err.println("❌ 文件删除失败: " + e.getMessage());
            return ResponseEntity.status(500).body("文件删除失败: " + e.getMessage());
        }
    }

}
