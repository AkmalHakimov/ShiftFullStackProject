package com.example.demo.controller;

import com.example.demo.service.fileService.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    public HttpEntity<?> SavePhoto(@RequestBody MultipartFile file, @RequestParam String prefix) throws IOException {
      return   fileService.SaveFile(file,prefix);
    }

    @GetMapping("getFile/{id}")
    public void GetFile(@PathVariable String id, HttpServletResponse response) throws SQLException, IOException {
        fileService.getFile(id,response);
    }
}
