package com.letrogthien.user.services.impl;

import com.letrogthien.user.dtos.UserDto;
import com.letrogthien.user.entities.User;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.UserMapper;
import com.letrogthien.user.repositories.UserRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.FileStorageService;
import com.letrogthien.user.services.UserCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCenterServiceImpl implements UserCenterService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final FileStorageService fileStorageService;


    @Override
    public ApiResponse<UserDto> getUserProfile(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        UserDto userDto = userMapper.toDto(user);
        return ApiResponse.<UserDto>builder()
                .data(userDto)
                .message("User profile retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<UserDto> updateUserProfile(UUID userId, UserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        userMapper.updateUserFromDto(userDto, user);
        userRepository.save(user);
        UserDto updatedUserDto = userMapper.toDto(user);
        return ApiResponse.<UserDto>builder()
                .data(updatedUserDto)
                .message("User profile updated successfully")
                .build();
    }

    @Override
    public ApiResponse<UserDto> updateDisplayName(UUID userId, String displayName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        user.setDisplayName(displayName);
        userRepository.save(user);
        UserDto updatedUserDto = userMapper.toDto(user);
        return ApiResponse.<UserDto>builder()
                .data(updatedUserDto)
                .message("Display name updated successfully")
                .build();
    }

    @Override
    public ApiResponse<UserDto> updateAvatar(UUID userId, MultipartFile avatar) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        String url = fileStorageService.saveFile(avatar, "/avatar" + user.getId().toString());
        if (url == null) {
            throw new CustomException(ErrorCode.FILE_UPLOAD_FAILED);
        }
        user.setAvatarUrl(url);
        userRepository.save(user);
        UserDto updatedUserDto = userMapper.toDto(user);
        return ApiResponse.<UserDto>builder()
                .data(updatedUserDto)
                .message("Avatar updated successfully")
                .build();
    }

    @Override
    public ApiResponse<UserDto> updateSellerBio(UUID userId, String sellerBio) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        user.setSellerBio(sellerBio);
        userRepository.save(user);
        UserDto updatedUserDto = userMapper.toDto(user);
        return ApiResponse.<UserDto>builder()
                .data(updatedUserDto)
                .message("Seller bio updated successfully")
                .build();

    }
}
