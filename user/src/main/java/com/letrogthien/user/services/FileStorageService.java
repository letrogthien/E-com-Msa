package com.letrogthien.user.services;

import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileStorageService {
    private static final String BASE_DIRECTORY = "uploads";

    public String saveFile(MultipartFile file, String path) {
        try {
            Path fullPath = Paths.get(BASE_DIRECTORY, path + getFileExtension(file.getOriginalFilename()));

            Files.createDirectories(fullPath.getParent());

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
