package com.letrogthien.user.services;

import com.letrogthien.user.dtos.UserDto;
import com.letrogthien.user.responses.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserCenterService {
    /**
     * Retrieves the profile information for a given user.
     * @param userId The ID of the user.
     * @return An ApiResponse containing the user's UserDto.
     */
    ApiResponse<UserDto> getUserProfile(UUID userId);

    /**
     * Updates the profile information for a given user.
     * @param userId The ID of the user.
     * @param userDto The UserDto containing the updated profile information.
     * @return An ApiResponse containing the updated UserDto.
     */
    ApiResponse<UserDto> updateUserProfile(UUID userId, UserDto userDto);

    /**
     * Updates the display name for a given user.
     * @param userId The ID of the user.
     * @param displayName The new display name.
     * @return An ApiResponse containing the updated UserDto.
     */
    ApiResponse<UserDto> updateDisplayName(UUID userId, String displayName);

    /**
     * Updates the avatar image for a given user.
     * @param userId The ID of the user.
     * @param avatar The MultipartFile representing the new avatar image.
     * @return An ApiResponse containing the updated UserDto.
     */
    ApiResponse<UserDto> updateAvatar(UUID userId, MultipartFile avatar);

    /**
     * Updates the seller biography for a given user.
     * @param userId The ID of the user.
     * @param sellerBio The new seller biography.
     * @return An ApiResponse containing the updated UserDto.
     */
    ApiResponse<UserDto> updateSellerBio(UUID userId, String sellerBio);
}