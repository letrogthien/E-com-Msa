package com.letrogthien.user.services;


import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.responses.ApiResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface KycService {
    /**
     * Retrieves the KYC document information for a given user.
     * @param userId The ID of the user.
     * @return An ApiResponse containing the user's KycDto.
     */
    ApiResponse<KycDto> getKyc(UUID userId);

    /**
     * Adds new KYC documents for a user.
     * @param userId The ID of the user.
     * @param front The front side of the identification document.
     * @param back The back side of the identification document.
     * @param selfieFront A selfie with the front side of the document.
     * @param selfieBack A selfie with the back side of the document.
     * @param selfieSmile A selfie with a smile.
     * @return An ApiResponse containing the added KycDto.
     */
    ApiResponse<KycDto> addKycDocument(UUID userId, MultipartFile front, MultipartFile back,
                                       MultipartFile selfieFront,
                                       MultipartFile selfieBack,
                                       MultipartFile selfieSmile);

    /**
     * Deletes an approved KYC selfie document for a user.
     * @param userId The ID of the user.
     * @param selfie The selfie document to be deleted.
     * @return An ApiResponse containing the updated KycDto.
     */
    ApiResponse<KycDto> deleteKycDocumentApproved(UUID userId, MultipartFile selfie);

    /**
     * Deletes pending KYC documents for a user.
     * @param userId The ID of the user.
     * @return An ApiResponse containing the updated KycDto.
     */
    ApiResponse<KycDto> deleteKycDocumentPending(UUID userId);

    /**
     * Retrieves all KYC documents filtered by their status.
     * @param status The status to filter by (e.g., PENDING, APPROVED, REJECTED).
     * @return An ApiResponse containing a list of KycDto matching the status.
     */
    ApiResponse<List<KycDto>> getAllByStatus(Status status);

    /**
     * Retrieves all KYC documents submitted by a specific user.
     * @param userId The ID of the user whose KYC documents are to be retrieved.
     * @return An ApiResponse containing a list of KycDto for the given user.
     */
    ApiResponse<List<KycDto>> getAllByUserId(UUID userId);
}