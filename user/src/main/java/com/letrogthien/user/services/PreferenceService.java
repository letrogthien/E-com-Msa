package com.letrogthien.user.services;


import com.letrogthien.user.common.Platform;
import com.letrogthien.user.dtos.PreferenceDto;
import com.letrogthien.user.responses.ApiResponse;
import java.util.UUID;

public interface PreferenceService {
    /**
     * Retrieves the preferences for a given user.
     * @param userId The ID of the user.
     * @return An ApiResponse containing the user's PreferenceDto.
     */
    ApiResponse<PreferenceDto> getUserPreference(UUID userId);

    /**
     * Updates the preferences for a given user.
     * @param userId The ID of the user.
     * @param dto The PreferenceDto containing the updated preferences.
     * @return An ApiResponse containing the updated PreferenceDto.
     */
    ApiResponse<PreferenceDto> updateUserPreference(UUID userId, PreferenceDto dto);

    /**
     * Initializes default preferences for a new user.
     * @param userId The ID of the user.
     * @return An ApiResponse containing the initialized PreferenceDto.
     */
    ApiResponse<PreferenceDto> initUserPreference(UUID userId);

    /**
     * Sets the email notification preference for a user.
     * @param userId The ID of the user.
     * @param enabled True to enable email notifications, false to disable.
     * @return An ApiResponse containing the updated PreferenceDto.
     */
    ApiResponse<PreferenceDto> setEmailNotification(UUID userId, boolean enabled);

    /**
     * Sets the push notification preference for a user.
     * @param userId The ID of the user.
     * @param enabled True to enable push notifications, false to disable.
     * @return An ApiResponse containing the updated PreferenceDto.
     */
    ApiResponse<PreferenceDto> setPushNotification(UUID userId, boolean enabled);

    /**
     * Sets the public profile preference for a user.
     * @param userId The ID of the user.
     * @param enabled True to make the profile public, false to make it private.
     * @return An ApiResponse containing the updated PreferenceDto.
     */
    ApiResponse<PreferenceDto> setPublicProfile(UUID userId, boolean enabled);

    /**
     * Sets the preferred currency for a user.
     * @param userId The ID of the user.
     * @param currency The preferred currency string (e.g., "USD", "EUR").
     * @return An ApiResponse containing the updated PreferenceDto.
     */
    ApiResponse<PreferenceDto> setPreferredCurrency(UUID userId, String currency);

    /**
     * Sets the preferred platform for a user.
     * @param userId The ID of the user.
     * @param platform The preferred Platform enum value.
     * @return An ApiResponse containing the updated PreferenceDto.
     */
    ApiResponse<PreferenceDto> setPreferredPlatform(UUID userId, Platform platform);
}