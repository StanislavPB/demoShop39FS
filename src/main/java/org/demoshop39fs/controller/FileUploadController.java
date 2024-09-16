package org.demoshop39fs.controller;

import lombok.RequiredArgsConstructor;
import org.demoshop39fs.dto.StandardResponseDto;
import org.demoshop39fs.service.fileService.FileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/public")
public class FileUploadController {

    private final FileService service;

    @PostMapping("/upload")
    public ResponseEntity<String> fileUpload(@RequestParam("uploadFile")MultipartFile uploadFile){
        service.uploadFile(uploadFile);
        return ResponseEntity.ok("Файл успешно загружен");
    }

    @PostMapping("/files")
    public ResponseEntity<StandardResponseDto> upload(@RequestParam("uploadFile")MultipartFile uploadFile){
        return ResponseEntity.ok(service.uploadFile(uploadFile));

    }
}
