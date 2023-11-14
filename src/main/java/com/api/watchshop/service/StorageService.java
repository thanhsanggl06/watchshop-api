package com.api.watchshop.service;

import com.api.watchshop.dto.ImageDataResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface StorageService {
    ImageDataResponse uploadImage(String fileName, MultipartFile file) throws IOException;
}
