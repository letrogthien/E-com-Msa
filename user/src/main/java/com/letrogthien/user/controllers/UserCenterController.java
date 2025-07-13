package com.letrogthien.user.controllers;

import com.letrogthien.user.annotaion.JwtClaims;
import com.letrogthien.user.dtos.UserDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.UserCenterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-center")
public class UserCenterController {
    private final UserCenterService userCenterService;

    @GetMapping("/profile")
    public ApiResponse<UserDto> getUserProfile(UUID userId) {
        return userCenterService.getUserProfile(userId);
    }

    @PutMapping("/update-profile")
    public ApiResponse<UserDto> updateUserProfile(@JwtClaims("id") UUID userId, @RequestBody UserDto userDto) {
        return userCenterService.updateUserProfile(userId, userDto);
    }
    @PatchMapping("/update-display-name")
    public ApiResponse<UserDto> updateDisplayName(@JwtClaims("id") UUID userId, @RequestParam String displayName) {
        return userCenterService.updateDisplayName(userId, displayName);
    }
    @PatchMapping("/update-avatar")
    public ApiResponse<UserDto> updateAvatar(@JwtClaims("id") UUID userId, @RequestPart MultipartFile avatar) {
        return userCenterService.updateAvatar(userId, avatar);
    }

    @PatchMapping("/update-seller-bio")
    public ApiResponse<UserDto> updateSellerBio(@JwtClaims("id") UUID userId, @RequestParam String sellerBio) {
        return userCenterService.updateSellerBio(userId, sellerBio);
    }
}
