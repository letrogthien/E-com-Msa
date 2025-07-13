package com.letrogthien.user.services;

import com.letrogthien.user.dtos.UserDto;
import com.letrogthien.user.responses.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserCenterService {
    ApiResponse<UserDto> getUserProfile(UUID userId);
    ApiResponse<UserDto> updateUserProfile(UUID userId, UserDto userDto);
    ApiResponse<UserDto> updateDisplayName(UUID userId, String displayName);
    ApiResponse<UserDto> updateAvatar(UUID userId, MultipartFile avatar);
    ApiResponse<UserDto> updateSellerBio(UUID userId, String sellerBio);

}
