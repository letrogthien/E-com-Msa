package com.letrogthien.user.controllers;

import com.letrogthien.user.dtos.PreferenceDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.PreferenceService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferenceService preferenceService;


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<PreferenceDto>> getUserPreference(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(preferenceService.getUserPreference(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<PreferenceDto>> updateUserPreference(
            @PathVariable UUID userId,
            @RequestBody PreferenceDto preferenceDto) {
        return ResponseEntity.ok(preferenceService.updateUserPreference(userId, preferenceDto));
    }

    @PostMapping("/init/{userId}")
    public ResponseEntity<ApiResponse<PreferenceDto>> initUserPreference(
            @PathVariable UUID userId) {
        return ResponseEntity.ok(preferenceService.initUserPreference(userId));
    }

    @PutMapping("/{userId}/email-notification")
    public ResponseEntity<ApiResponse<PreferenceDto>> setEmailNotification(
            @PathVariable UUID userId,
            @RequestParam boolean enabled) {
        return ResponseEntity.ok(preferenceService.setEmailNotification(userId, enabled));
    }

    @PutMapping("/{userId}/push-notification")
    public ResponseEntity<ApiResponse<PreferenceDto>> setPushNotification(
            @PathVariable UUID userId,
            @RequestParam boolean enabled) {
        return ResponseEntity.ok(preferenceService.setPushNotification(userId, enabled));
    }
}
