package com.letrogthien.user.services.impl;

import com.letrogthien.user.common.Platform;
import com.letrogthien.user.dtos.PreferenceDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {
    @Override
    public ApiResponse<PreferenceDto> getUserPreference(UUID userId) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> updateUserPreference(UUID userId, PreferenceDto dto) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> initUserPreference(UUID userId) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> setEmailNotification(UUID userId, boolean enabled) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> setPushNotification(UUID userId, boolean enabled) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> setPublicProfile(UUID userId, boolean enabled) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> setPreferredCurrency(UUID userId, String currency) {
        return null;
    }

    @Override
    public ApiResponse<PreferenceDto> setPreferredPlatform(UUID userId, Platform platform) {
        return null;
    }
}
