package com.letrogthien.user.services.impl;

import com.letrogthien.user.common.Status;
import com.letrogthien.user.dtos.KycDto;
import com.letrogthien.user.entities.DeleteKyc;
import com.letrogthien.user.entities.User;
import com.letrogthien.user.entities.UserKyc;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.UserKycMapper;
import com.letrogthien.user.repositories.DeleteKycRepository;
import com.letrogthien.user.repositories.KycRepository;
import com.letrogthien.user.repositories.UserRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.FileStorageService;
import com.letrogthien.user.services.KycService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service  
@RequiredArgsConstructor
public class KycServiceImpl implements KycService {
    private final KycRepository kycRepository;
    private final FileStorageService fileStorageService;
    private final UserRepository userRepository;
    private final DeleteKycRepository deleteKycRepository;
    private final UserKycMapper userKycMapper;

    @Override
    public ApiResponse<KycDto> getKyc(UUID userId) {
        UserKyc userKyc = kycRepository.findTopByUserIdOrderByVersionDesc(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        if (userKyc.getVerificationStatus() == Status.REJECTED) {
            return ApiResponse.<KycDto>builder()
                    .message("No KYC document found")
                    .data(null)
                    .build();
        }
        KycDto kycDto = userKycMapper.toDto(userKyc);
        return ApiResponse.<KycDto>builder()
                .message("KYC document found")
                .data(kycDto)
                .build();
    }


    @Override
    public ApiResponse<KycDto> addKycDocument(
            UUID userId,
            MultipartFile front,
            MultipartFile back,
            MultipartFile selfieFront,
            MultipartFile selfieBack,
            MultipartFile selfieSmile

    ) {
        UserKyc userKyc = kycRepository.findTopByUserIdOrderByVersionDesc(userId)
                .orElse(null);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        if (userKyc == null) {
            return addNewDocument(user, front, back, selfieFront,selfieBack,selfieSmile,1);
        }
        if (!checkCurrentDocument(userKyc)) {
            throw new CustomException(ErrorCode.KYC_ALREADY_APPROVED);
        }
        if (userKyc.getVerificationStatus() == Status.PENDING) {
            userKyc.setVerificationStatus(Status.REJECTED);
        }
        int currentVersion = userKyc.getVersion();
        return addNewDocument(user, front, back, selfieFront,selfieBack,selfieSmile, currentVersion + 1);
    }

    @Override
    public ApiResponse<KycDto> deleteKycDocumentApproved(UUID userId, MultipartFile selfie) {
        UserKyc userKyc = kycRepository.findTopByUserIdOrderByVersionDesc(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        if (userKyc.getVerificationStatus() != Status.APPROVED) {
            throw new CustomException(ErrorCode.KYC_NOT_APPROVED);
        }
        DeleteKyc deleteKyc = new DeleteKyc();
        deleteKyc.setUserId(userId);
        deleteKyc.setSelfieUrl(
                fileStorageService.saveFile(selfie, "kyc/delete/" + userId + "/v" + userKyc.getVersion() + "_selfie")
        );
        deleteKycRepository.save(deleteKyc);
        return ApiResponse.<KycDto>builder()
                .message("KYC document deletion request submitted successfully")
                .build();
    }

    @Override
    public ApiResponse<KycDto> deleteKycDocumentPending(UUID userId) {
        UserKyc userKyc = kycRepository.findTopByUserIdOrderByVersionDesc(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND));
        if (userKyc.getVerificationStatus() == Status.APPROVED) {
            throw new CustomException(ErrorCode.KYC_ALREADY_APPROVED);
        }
        if (userKyc.getVerificationStatus() == Status.REJECTED) {
            throw new CustomException(ErrorCode.KYC_ALREADY_REJECTED);
        }
        userKyc.setVerificationStatus(Status.REJECTED);
        kycRepository.save(userKyc);
        return ApiResponse.<KycDto>builder()
                .message("KYC document deleted successfully")
                .data(userKycMapper.toDto(userKyc))
                .build();
    }

    @Override
    public ApiResponse<List<KycDto>> getAllByStatus(Status status) {
        if (status == null) {
            throw new CustomException(ErrorCode.INVALID_STATUS);
        }
        List<UserKyc> userKycs = kycRepository.findAllByVerificationStatus(status);


        return ApiResponse.<List<KycDto>>builder()
                .message("KYC documents retrieved successfully")
                .data(userKycs.stream().map(userKycMapper::toDto).toList())
                .build();
    }

    @Override
    public ApiResponse<List<KycDto>> getAllByUserId(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<UserKyc> userKycs = kycRepository.findAllByUserId(user.getId());
        if (userKycs.isEmpty()) {
            return ApiResponse.<List<KycDto>>builder()
                    .message("No KYC documents found for user")
                    .data(null)
                    .build();
        }
        return ApiResponse.<List<KycDto>>builder()
                .message("KYC documents retrieved successfully")
                .data(userKycs.stream().map(userKycMapper::toDto).toList())
                .build();
    }

    private ApiResponse<KycDto> addNewDocument(
            User user,
            MultipartFile front,
            MultipartFile back,
            MultipartFile selfieFront,
            MultipartFile selfieBack,
            MultipartFile selfieSmile,
            int version) {
        UserKyc userKyc = new UserKyc();
        userKyc.setUser(user);
        userKyc.setVersion(version);
        userKyc.setVerificationStatus(Status.PENDING);
        userKyc.setDocumentBackUrl(
                fileStorageService.saveFile(back, "kyc/" + user.getId() + "/v" + version + "_back")
        );
        userKyc.setDocumentFrontUrl(
                fileStorageService.saveFile(front, "kyc/" + user.getId() + "/v" + version + "_front")
        );
        userKyc.setFaceIdFrontUrl(
                fileStorageService.saveFile(selfieFront, "kyc/" + user.getId() + "/v" + version + "_selfie_front")
        );
        userKyc.setFaceIdBackUrl(
                fileStorageService.saveFile(selfieBack, "kyc/" + user.getId() + "/v" + version + "_selfie_back")
        );
        userKyc.setFaceIdSmileUrl(
                fileStorageService.saveFile(selfieSmile, "kyc/" + user.getId() + "/v" + version + "_selfie_smile")
        );
        kycRepository.save(userKyc);
        return ApiResponse.<KycDto>builder()
                .message("KYC document added successfully")
                .data(userKycMapper.toDto(userKyc))
                .build();
    }

    private boolean checkCurrentDocument(UserKyc userKyc) {
        return userKyc.getVerificationStatus() != Status.APPROVED;
    }


}
