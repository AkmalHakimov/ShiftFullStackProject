package com.example.demo.service.fileService;

import com.example.demo.entity.Attachment;
import com.example.demo.repository.FileRepo;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileServiceImple implements FileService{

    private final FileRepo fileRepo;

    @Override
    public HttpEntity<?> SaveFile(MultipartFile file, String prefix) throws IOException {
        try {
            UUID id = UUID.randomUUID();
            Attachment attachment = new Attachment(
                    id,
                    id+ ":" + file.getOriginalFilename(),
                    prefix
            );
            fileRepo.save(attachment);
            FileCopyUtils.copy(file.getInputStream(),new FileOutputStream("files" + prefix + "/" + attachment.getId() + ":" + file.getOriginalFilename()));
            return ResponseEntity.ok(attachment.getId());
        }catch (Exception e){
            return ResponseEntity.status(500).body("File is not supported");
        }
    }

    @Override
    public HttpEntity<?> getFile(String id, HttpServletResponse response) throws IOException {
        try {
            Attachment attachment = fileRepo.findById(UUID.fromString(id)).orElseThrow();
            FileCopyUtils.copy(new FileInputStream(" files" + attachment.getPrefix() + "/" + attachment.getName()),response.getOutputStream());
            return ResponseEntity.ok("File uploaded✅");
        }catch (Exception e){
            return ResponseEntity.status(500).body("An error has occurred");
        }
    }
}
