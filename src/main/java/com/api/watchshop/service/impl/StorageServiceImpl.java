package com.api.watchshop.service.impl;

import com.api.watchshop.dto.ImageDataResponse;
import com.api.watchshop.entity.ImageData;
import com.api.watchshop.exception.StorageException;
import com.api.watchshop.mapper.ImageDataAutoMapper;
import com.api.watchshop.repository.ImageDataRepository;
import com.api.watchshop.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@AllArgsConstructor
public class StorageServiceImpl implements StorageService {

    private HttpServletRequest request;
    private ImageDataRepository imageDataRepository;

    @Override
    public ImageDataResponse uploadImage(String fileName, MultipartFile file) throws IOException {
        Path uploadDirectory = Paths.get("Files-Upload");
        String fileCode = RandomStringUtils.randomAlphabetic(8);
        ImageDataResponse response;
        try (InputStream inputStream = file.getInputStream()) {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            if (!isImage(file)) {
                throw new StorageException("File not support.");
            }
            String type = file.getContentType();
            Path filePath = uploadDirectory.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);

            // Xác định giao thức của URL hiện tại (HTTP hoặc HTTPS)
//            String protocol = request.getScheme();

            // Xây dựng đường dẫn để truy cập file sau khi upload với thông tin về server linh hoạt
            String serverHost = request.getRequestURL().toString();
            String fileUrl = serverHost.replace(request.getRequestURI(), "") + "/Files-Upload/" + fileCode + "-" + fileName;

            ImageData imageData = ImageData.builder().name(fileCode + "-" + fileName).type(type).filePath(fileUrl).build();
            ImageData result = imageDataRepository.save(imageData);
            response = ImageDataAutoMapper.MAPPER.mapToImageDataResponse(result);


        } catch (IOException | StorageException e) {
            e.printStackTrace();
            throw new IOException(e.getMessage());
        }
        return response;
    }

    public boolean isImage(MultipartFile file) {
        Tika tika = new Tika();

        try {
            String mimeType = tika.detect(file.getInputStream());
            return mimeType != null && mimeType.startsWith("image/");
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
