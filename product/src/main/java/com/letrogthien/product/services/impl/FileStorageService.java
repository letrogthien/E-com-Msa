package com.letrogthien.product.services.impl;

import com.letrogthien.product.exceptions.CustomException;
import com.letrogthien.product.exceptions.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileStorageService {
    private static final String BASE_DIRECTORY = "uploads/product-service/assets";

    public String saveFile(MultipartFile file, String path) {
        try {
            String originalFilename = file.getOriginalFilename();
            String extension = getFileExtension(originalFilename);
            String baseName = path;
            Path fullPath = Paths.get(BASE_DIRECTORY, baseName + extension);

            Files.createDirectories(fullPath.getParent());
            int count = 1;
            while (Files.exists(fullPath)) {
                String newBaseName = baseName + "_" + count;
                fullPath = Paths.get(BASE_DIRECTORY, newBaseName + extension);
                count++;
            }

            file.transferTo(fullPath.toFile());

            return fullPath.toString().replace("\\", "/");
        } catch (IOException e) {
            throw new CustomException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null) return "";
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex != -1) ? filename.substring(dotIndex) : "";
    }
}
