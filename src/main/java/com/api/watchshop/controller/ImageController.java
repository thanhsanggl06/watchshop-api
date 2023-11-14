package com.api.watchshop.controller;

import com.api.watchshop.dto.ImageDataResponse;
import com.api.watchshop.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class ImageController {
    private StorageService storageService;

    @PostMapping("/upload")
    public ResponseEntity<ImageDataResponse> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        long size = file.getSize();

        ImageDataResponse response = storageService.uploadImage(fileName, file);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
