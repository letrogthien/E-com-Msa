package com.letrogthien.user.controllers;

import com.letrogthien.user.dtos.SellerApplicationDto;
import com.letrogthien.user.request.SellerApplicationRequest;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.SellerApplicationService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seller-applications")
@RequiredArgsConstructor
public class SellerApplicationController {

    private final SellerApplicationService sellerApplicationService;

    @PostMapping("/submit")
    public ResponseEntity<ApiResponse<SellerApplicationDto>> submitApplication(
            @RequestParam UUID userId,
            @RequestBody SellerApplicationRequest request) {
        return ResponseEntity.ok(sellerApplicationService.submitApplication(userId, request));
    }
}
