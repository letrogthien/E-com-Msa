package com.letrogthien.user.services.impl;

import com.letrogthien.user.common.Platform;
import com.letrogthien.user.dtos.PreferenceDto;
import com.letrogthien.user.entities.Preference;
import com.letrogthien.user.entities.User;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.PreferenceMapper;
import com.letrogthien.user.repositories.PreferenceRepository;
import com.letrogthien.user.repositories.UserRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.PreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
@RequiredArgsConstructor
public class PreferenceServiceImpl implements PreferenceService {
    private final PreferenceRepository preferenceRepository;
    private final PreferenceMapper preferenceMapper;
    private final UserRepository userRepository;

    @Override
    public ApiResponse<PreferenceDto> getUserPreference(UUID userId) {

        return preferenceRepository.findById(userId)
                .map(preference -> ApiResponse.<PreferenceDto>builder()
                        .message("User preference retrieved successfully")
                        .data(preferenceMapper.toDto(preference))
                        .build())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<PreferenceDto> updateUserPreference(UUID userId, PreferenceDto dto) {
        return preferenceRepository.findById(userId)
                .map(existingPreference -> {
                    existingPreference.setNotificationEmail(dto.isNotificationEmail());
                    existingPreference.setNotificationPush(dto.isNotificationPush());
                    existingPreference.setPrivacyPublicProfile(dto.isPrivacyPublicProfile());
                    existingPreference.setPreferredCurrency(dto.getPreferredCurrency());
                    existingPreference.setPreferredPlatform(dto.getPreferredPlatform());

                    preferenceRepository.save(existingPreference);

                    return ApiResponse.<PreferenceDto>builder()
                            .message("User preference updated successfully")
                            .data(preferenceMapper.toDto(existingPreference))
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<PreferenceDto> initUserPreference(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        return preferenceRepository.findById(userId)
                .map(existingPreference -> ApiResponse.<PreferenceDto>builder()
                        .message("User preference already exists")
                        .data(preferenceMapper.toDto(existingPreference))
                        .build())
                .orElseGet(() -> {
                    Preference newPreference = Preference.builder()
                            .user(user)
                            .preferredCurrency("USD")
                            .preferredPlatform(Platform.OTHER)
                            .notificationEmail(false)
                            .notificationPush(false)
                            .privacyPublicProfile(false)
                            .build();
                    preferenceRepository.save(newPreference);
                    return ApiResponse.<PreferenceDto>builder()
                            .message("User preference initialized successfully")
                            .data(preferenceMapper.toDto(newPreference))
                            .build();
                });
    }

    @Override
    public ApiResponse<PreferenceDto> setEmailNotification(UUID userId, boolean enabled) {
        return preferenceRepository.findByUserId(userId)
                .map(existingPreference -> {
                    existingPreference.setNotificationEmail(enabled);
                    preferenceRepository.save(existingPreference);
                    return ApiResponse.<PreferenceDto>builder()
                            .message("Email notification preference updated successfully")
                            .data(preferenceMapper.toDto(existingPreference))
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<PreferenceDto> setPushNotification(UUID userId, boolean enabled) {
        return preferenceRepository.findByUserId(userId)
                .map(existingPreference -> {
                    existingPreference.setNotificationPush(enabled);
                    preferenceRepository.save(existingPreference);
                    return ApiResponse.<PreferenceDto>builder()
                            .message("Push notification preference updated successfully")
                            .data(preferenceMapper.toDto(existingPreference))
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<PreferenceDto> setPublicProfile(UUID userId, boolean enabled) {

        return preferenceRepository.findByUserId(userId)
                .map(existingPreference -> {
                    existingPreference.setPrivacyPublicProfile(enabled);
                    preferenceRepository.save(existingPreference);
                    return ApiResponse.<PreferenceDto>builder()
                            .message("Public profile preference updated successfully")
                            .data(preferenceMapper.toDto(existingPreference))
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<PreferenceDto> setPreferredCurrency(UUID userId, String currency) {

        return preferenceRepository.findByUserId(userId)
                .map(existingPreference -> {
                    existingPreference.setPreferredCurrency(currency);
                    preferenceRepository.save(existingPreference);
                    return ApiResponse.<PreferenceDto>builder()
                            .message("Preferred currency updated successfully")
                            .data(preferenceMapper.toDto(existingPreference))
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<PreferenceDto> setPreferredPlatform(UUID userId, Platform platform) {

        return preferenceRepository.findByUserId(userId)
                .map(existingPreference -> {
                    existingPreference.setPreferredPlatform(platform);
                    preferenceRepository.save(existingPreference);
                    return ApiResponse.<PreferenceDto>builder()
                            .message("Preferred platform updated successfully")
                            .data(preferenceMapper.toDto(existingPreference))
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }
}
