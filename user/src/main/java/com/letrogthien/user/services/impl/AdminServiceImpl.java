package com.letrogthien.user.services.impl;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;

import com.letrogthien.user.dtos.SellerApplicationDto;
import com.letrogthien.user.entities.ApprovedKycOutBox;
import com.letrogthien.user.entities.UserKyc;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.SellerApplicationMapper;
import com.letrogthien.user.repositories.SellerApplicationRepository;
import com.letrogthien.user.mappers.UserKycMapper;
import com.letrogthien.user.repositories.ApprovedKycOutBoxRepository;
import com.letrogthien.user.repositories.KycRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final KycRepository kycRepository;
    private final UserKycMapper userKycMapper;
    private final ApprovedKycOutBoxRepository approvedKycOutBoxRepository;
    private final SellerApplicationRepository sellerApplicationRepository;
    private SellerApplicationMapper sellerApplicationMapper;



    /*
        * here should use AI to approve or reject KYC document with schedule
        * but for now, we will just approve or reject KYC document manually
     */
    @Override
    public ApiResponse<KycDto> approveKycDocument(UUID kycDocumentId) {
        UserKyc userKyc= kycRepository.findById(kycDocumentId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND)
        );
        if (!userKyc.getVerificationStatus().equals(Status.PENDING)) {
            return ApiResponse.<KycDto>builder()
                    .message("KYC document is not in pending status")
                    .data(userKycMapper.toDto(userKyc))
                    .build();
        }
        userKyc.setVerificationStatus(Status.APPROVED);
        this.saveApprovedKycOutBox(userKyc.getUser().getId());
        kycRepository.save(userKyc);

        return ApiResponse.<KycDto>builder()
                .message("KYC document approved successfully")
                .data(userKycMapper.toDto(userKyc))
                .build();
    }

    private void saveApprovedKycOutBox(UUID userId) {
        approvedKycOutBoxRepository.save(
                ApprovedKycOutBox.builder()
                        .userId(userId)
                        .build()
        );
    }

    @Override
    public ApiResponse<KycDto> rejectKycDocument(UUID kycDocumentId) {
        UserKyc userKyc= kycRepository.findById(kycDocumentId).orElseThrow(
                ()-> new CustomException(ErrorCode.NOT_FOUND)
        );
        if (!userKyc.getVerificationStatus().equals(Status.PENDING)) {
            return ApiResponse.<KycDto>builder()
                    .message("KYC document is not in pending status")
                    .data(userKycMapper.toDto(userKyc))
                    .build();
        }
        userKyc.setVerificationStatus(Status.REJECTED);
        kycRepository.save(userKyc);
        return ApiResponse.<KycDto>builder()
                .message("KYC document rejected successfully")
                .data(userKycMapper.toDto(userKyc))
                .build();
    }

    @Override
    public ApiResponse<SellerApplicationDto> getSellerApplicationById(UUID id) {
        return sellerApplicationRepository.findById(id)
                .map(sellerApplicationMapper::toDto)
                .map(application -> ApiResponse.<SellerApplicationDto>builder()
                        .data(application)
                        .message("Seller application found")
                        .build())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<List<SellerApplicationDto>> getAllSellerApplications() {
        return ApiResponse.<List<SellerApplicationDto>>builder()
                .data(sellerApplicationRepository.findAll()
                        .stream()
                        .map(sellerApplicationMapper::toDto)
                        .toList())
                .message("All seller applications retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<List<SellerApplicationDto>> getSellerApplicationsByStatus(Status status) {
        return ApiResponse.<List<SellerApplicationDto>>builder()
                .data(sellerApplicationRepository.findAllByApplicationStatus(status)
                        .stream()
                        .map(sellerApplicationMapper::toDto)
                        .toList())
                .message("Seller applications with status " + status + " retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<List<SellerApplicationDto>> getSellerApplicationsByUserId(UUID userId) {
        return ApiResponse.<List<SellerApplicationDto>>builder()
                .data(sellerApplicationRepository.findAllByUserId(userId)
                        .stream()
                        .map(sellerApplicationMapper::toDto)
                        .toList())
                .message("Seller applications for user with ID " + userId + " retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<SellerApplicationDto> updateApplicationStatus(UUID id, Status newStatus, String rejectionReason, UUID reviewerId) {
        return sellerApplicationRepository.findById(id)
                .map(application -> {
                    application.setApplicationStatus(newStatus);
                    application.setRejectionReason(rejectionReason);
                    application.setReviewerId(reviewerId);
                    sellerApplicationRepository.save(application);
                    return ApiResponse.<SellerApplicationDto>builder()
                            .data(sellerApplicationMapper.toDto(application))
                            .message("Seller application status updated successfully")
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<Void> deleteSellerApplication(UUID id) {
        return sellerApplicationRepository.findById(id)
                .map(application -> {
                    sellerApplicationRepository.delete(application);
                    return ApiResponse.<Void>builder()
                            .message("Seller application deleted successfully")
                            .build();
                })
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
    }

    @Override
    public ApiResponse<Boolean> existsById(UUID id) {
        return ApiResponse.<Boolean>builder()
                .data(sellerApplicationRepository.existsById(id))
                .message("Checked existence of seller application with ID " + id)
                .build();
    }
}



