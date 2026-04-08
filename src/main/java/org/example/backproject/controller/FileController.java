package org.example.backproject.controller;

import lombok.RequiredArgsConstructor;
import org.example.backproject.service.S3Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FileController {
    private final S3Service s3Service;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) return ResponseEntity.badRequest().body("파일이 없습니다.");

        try {
            String savedFileName = s3Service.uploadFile(file);
            return ResponseEntity.ok("S3 업로드 성공: " + savedFileName);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("파일 업로드 실패");
        }
    }
}
