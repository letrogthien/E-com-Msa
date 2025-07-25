package com.letrogthien.user.services;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.dtos.SellerApplicationDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface AdminService {
    /**
     * Approves a KYC document by its ID.
     *
     * @param kycDocumentId The UUID of the KYC document to approve.
     * @return An ApiResponse containing the approved KycDto.
     */
    ApiResponse<KycDto> approveKycDocument(UUID kycDocumentId);
    /**
     * Rejects a KYC document by its ID.
     *
     * @param kycDocumentId The UUID of the KYC document to reject.
     * @return An ApiResponse containing the rejected KycDto.
     */
    ApiResponse<KycDto> rejectKycDocument(UUID kycDocumentId);

    /**
     * Retrieves a specific seller application by its ID.
     *
     * @param id The UUID of the seller application.
     * @return An ApiResponse containing the SellerApplicationDTO if found.
     */
    ApiResponse<SellerApplicationDto> getSellerApplicationById(UUID id);

    /**
     * Retrieves all seller applications. This method is typically for administrative use.
     *
     * @return An ApiResponse containing a list of all SellerApplicationDTOs.
     */
    ApiResponse<List<SellerApplicationDto>> getAllSellerApplications();

    /**
     * Retrieves seller applications filtered by their status.
     *
     * @param status The status to filter by (e.g., "PENDING", "APPROVED", "REJECTED").
     * @return An ApiResponse containing a list of SellerApplicationDTOs matching the status.
     */
    ApiResponse<List<SellerApplicationDto>> getSellerApplicationsByStatus(Status status);

    /**
     * Retrieves all seller applications submitted by a specific user.
     *
     * @param userId The ID of the user whose applications are to be retrieved.
     * @return An ApiResponse containing a list of SellerApplicationDTOs for the given user.
     */
    ApiResponse<List<SellerApplicationDto>> getSellerApplicationsByUserId(UUID userId);

    /**
     * Updates the status of a seller application, typically by an administrator.
     *
     * @param id The UUID of the seller application to update.
     * @param newStatus The new status for the application (e.g., "APPROVED", "REJECTED", "NEEDS_MORE_INFO").
     * @param rejectionReason Optional: The reason for rejection if the new status is "REJECTED".
     * @param reviewerId The ID of the administrator/moderator who reviewed and updated the status.
     * @return An ApiResponse containing the updated SellerApplicationDTO.
     */
    ApiResponse<SellerApplicationDto> updateApplicationStatus(UUID id, Status newStatus, String rejectionReason, UUID reviewerId);

    /**
     * Deletes a seller application by its ID. This operation is typically for administrative use
     * and should be used with caution (consider soft delete instead).
     *
     * @param id The UUID of the seller application to delete.
     * @return An ApiResponse indicating success or failure of the deletion.
     */
    ApiResponse<Void> deleteSellerApplication(UUID id);

    /**
     * Checks if a seller application with the given ID exists.
     *
     * @param id The UUID of the seller application.
     * @return An ApiResponse containing a Boolean indicating existence.
     */
    ApiResponse<Boolean> existsById(UUID id);

}
