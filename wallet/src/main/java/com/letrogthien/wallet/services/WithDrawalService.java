package com.letrogthien.wallet.services;

import java.util.UUID;

import com.letrogthien.wallet.dtos.WithdrawalRequestDto;
import com.letrogthien.wallet.requests.WithdrawalRequest;
import com.letrogthien.wallet.responses.ApiResponse;

public interface WithDrawalService {
    ApiResponse<WithdrawalRequestDto> createWithDrawal(UUID userId, WithdrawalRequest withDrawalRequest);
    ApiResponse<WithdrawalRequestDto> getWithDrawal(UUID userId, UUID withDrawalId);

    
}
