package org.javaclimb.springbootmusic.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.javaclimb.springbootmusic.service.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upload")
@CrossOrigin("*")
public class FileController {
    FileService fileService;
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping("/**")
    public ResponseEntity<Resource> getFile(HttpServletRequest request) {
        String path = request.getRequestURI().substring("/upload/".length());
        return fileService.getFile(path);
    }
}

