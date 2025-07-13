package com.letrogthien.user.services;

import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.UUID;

public interface AdminService {
    ApiResponse<KycDto> approveKycDocument(UUID kycDocumentId);
    ApiResponse<KycDto> rejectKycDocument(UUID kycDocumentId);

}
