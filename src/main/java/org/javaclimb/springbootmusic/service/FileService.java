package org.javaclimb.springbootmusic.service;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;


@Service
public class FileService {
    public static String uploadFile(MultipartFile file, Path uploadDir) {
        String fileName;
        if (!Files.exists(uploadDir)) {
            try {
                Files.createDirectories(uploadDir);
            } catch (IOException e) {
                System.err.println("❌ 创建上传目录失败: " + e.getMessage());
            }
        }

        try {
            String timestamp = String.valueOf(System.currentTimeMillis());
            fileName = timestamp + "_" + Objects.requireNonNull(file.getOriginalFilename());
            Path filePath = uploadDir.resolve(fileName).normalize();
            Files.write(filePath, file.getBytes());
            System.out.println("📤 文件上传成功: " + filePath);
        } catch (IOException e) {
            System.err.println("❌ 文件上传失败: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return fileName;
    }

    public static ResponseEntity<Resource> getFile(String fileName, Path storageDir){
        try {


            Path filePath = storageDir.resolve(fileName).normalize();

            if (!Files.exists(filePath)) {
                throw new FileNotFoundException("文件未找到: " + fileName);
            }

            String mimeType = Files.probeContentType(filePath);
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }
            System.out.println("📥 文件获取: " + fileName);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(mimeType))
                    .body(new FileSystemResource(filePath));
        } catch (IOException e) {
            throw new RuntimeException("文件访问错误: " + e.getMessage(), e);
        }
    }

    public static ResponseEntity<String> deleteFile(String fileName, Path storageDir) {

        Path filePath = storageDir.resolve(fileName).normalize();
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
