package com.letrogthien.user.controllers;


import com.letrogthien.user.annotaion.JwtClaims;
import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/kyc")
public class KycController {
    private final KycService kycService;

    @GetMapping("/document")
    public ApiResponse<KycDto> getKycDocument(@JwtClaims("id") UUID userId) {
        return kycService.getKyc(userId);
    }

    @PostMapping(
            path = "/document/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ApiResponse<KycDto> addKycDocument(
            @JwtClaims("id") UUID userId,
            @RequestPart("front") MultipartFile front,
            @RequestPart("back") MultipartFile back,
            @RequestPart("selfieFront") MultipartFile selfieFront,
            @RequestPart("selfieBack") MultipartFile selfieBack,
            @RequestPart("selfieSmile") MultipartFile selfieSmile) {
        return kycService.addKycDocument(userId, front, back, selfieFront, selfieBack, selfieSmile);
    }

    @DeleteMapping("/document/delete/approved")
    public ApiResponse<KycDto> deleteKycDocumentApproved(
            @JwtClaims("id") UUID userId,
            @RequestPart MultipartFile selfie) {
        return kycService.deleteKycDocumentApproved(userId, selfie);
    }

    @DeleteMapping("/document/delete/pending")
    public ApiResponse<KycDto> deleteKycDocumentPending(@JwtClaims("id") UUID userId) {
        return kycService.deleteKycDocumentPending(userId);
    }
}
