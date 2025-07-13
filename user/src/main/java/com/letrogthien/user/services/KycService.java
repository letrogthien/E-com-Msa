package com.letrogthien.user.services;


import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.responses.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface KycService {
    ApiResponse<KycDto> getKyc(UUID userId);

    ApiResponse<KycDto> addKycDocument(UUID userId, MultipartFile front, MultipartFile back,
                                  MultipartFile selfieFront,
                                  MultipartFile selfieBack,
                                  MultipartFile selfieSmile);

    ApiResponse<KycDto> deleteKycDocumentApproved(UUID userId, MultipartFile selfie);
    ApiResponse<KycDto> deleteKycDocumentPending(UUID userId);
    ApiResponse<List<KycDto>> getAllByStatus(Status status);
    ApiResponse<List<KycDto>> getAllByUserId(UUID userId);


}
