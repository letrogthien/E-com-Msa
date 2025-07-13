package com.letrogthien.user.services;


import com.letrogthien.user.common.Platform;
import com.letrogthien.user.dtos.PreferenceDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.UUID;

public interface PreferenceService {
    ApiResponse<PreferenceDto> getUserPreference(UUID userId);
    ApiResponse<PreferenceDto> updateUserPreference(UUID userId, PreferenceDto dto);
    ApiResponse<PreferenceDto> initUserPreference(UUID userId);
    ApiResponse<PreferenceDto> setEmailNotification(UUID userId, boolean enabled);
    ApiResponse<PreferenceDto> setPushNotification(UUID userId, boolean enabled);
    ApiResponse<PreferenceDto> setPublicProfile(UUID userId, boolean enabled);
    ApiResponse<PreferenceDto> setPreferredCurrency(UUID userId, String currency);
    ApiResponse<PreferenceDto> setPreferredPlatform(UUID userId, Platform platform);
}