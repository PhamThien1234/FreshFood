package com.example.FreshFood.service;

import com.example.FreshFood.exception.AppException;
import com.example.FreshFood.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path productUploadPath = Paths.get("uploads/product").toAbsolutePath().normalize();

    public String saveProductImage(MultipartFile file){
        if(file.isEmpty() || file == null){
            throw  new AppException(ErrorCode.INVALID_FILE);
        }
        try {
            // Tạo thư mục nếu chưa tồn tại
            Files.createDirectories(productUploadPath);

            String originalFileName = file.getOriginalFilename();
            String extension = "";

            if (originalFileName != null && originalFileName.contains(".")) {
                extension = originalFileName.substring(
                        originalFileName.lastIndexOf(".")
                );
            }
            // Tạo tên file duy nhất
            String fileName = UUID.randomUUID() + extension;

            Path targetPath = productUploadPath
                    .resolve(fileName)
                    .normalize();

            Files.copy(
                    file.getInputStream(),
                    targetPath,
                    StandardCopyOption.REPLACE_EXISTING
            );
            // Đường dẫn này sẽ lưu vào database
            return "/uploads/products/" + fileName;

        } catch (IOException e) {
            throw new AppException(ErrorCode.FILE_UPLOAD_FAILED);
        }

    }
}
