package com.letrogthien.user.controllers;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.AdminService;
import com.letrogthien.user.services.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")

public class AdminController {
    private final AdminService adminService;
    private final KycService kycService;

    @PutMapping("/kyc/approve-kyc")
    public ApiResponse<KycDto> approveKycDocument(UUID kycDocumentId) {
        return adminService.approveKycDocument(kycDocumentId);
    }


    @PutMapping("/kyc/reject-kyc")
    public ApiResponse<KycDto> rejectKycDocument(UUID kycDocumentId) {
        return adminService.rejectKycDocument(kycDocumentId);
    }

    @GetMapping("/kyc/documents/status")
    public ApiResponse<List<KycDto>> getKycDocumentStatus(@RequestParam Status status) {
        return kycService.getAllByStatus(status);
    }
    @GetMapping("/kyc/documents/user/{userId}")
    public ApiResponse<List<KycDto>> getKycDocumentsByUserId(@PathVariable UUID userId) {
        return kycService.getAllByUserId(userId);
    }
}
