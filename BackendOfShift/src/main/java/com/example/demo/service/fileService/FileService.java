package com.example.demo.service.fileService;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {
    HttpEntity<?> SaveFile(MultipartFile file, String prefix) throws IOException;

    HttpEntity<?> getFile(String id, HttpServletResponse response) throws IOException;
}
