package com.letrogthien.user.services.impl;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;

import com.letrogthien.user.entities.ApprovedKycOutBox;
import com.letrogthien.user.entities.UserKyc;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.UserKycMapper;
import com.letrogthien.user.repositories.ApprovedKycOutBoxRepository;
import com.letrogthien.user.repositories.KycRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;


@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
    private final KycRepository kycRepository;
    private final UserKycMapper userKycMapper;
    private final ApprovedKycOutBoxRepository approvedKycOutBoxRepository;



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
}
