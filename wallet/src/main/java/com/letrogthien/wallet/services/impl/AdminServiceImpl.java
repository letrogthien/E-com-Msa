package com.letrogthien.wallet.services.impl;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.letrogthien.wallet.common.Status;
import com.letrogthien.wallet.dtos.DepositRequestDto;
import com.letrogthien.wallet.dtos.PaymentTransactionDto;
import com.letrogthien.wallet.dtos.WalletDto;
import com.letrogthien.wallet.dtos.WithdrawalRequestDto;
import com.letrogthien.wallet.entities.Wallet;
import com.letrogthien.wallet.exceptions.CustomException;
import com.letrogthien.wallet.exceptions.ErrorCode;
import com.letrogthien.wallet.mapper.DepositRequestMapper;
import com.letrogthien.wallet.mapper.PaymentTransactionMapper;
import com.letrogthien.wallet.mapper.WalletMapper;
import com.letrogthien.wallet.mapper.WithdrawalRequestMapper;
import com.letrogthien.wallet.repositories.DepositRepository;
import com.letrogthien.wallet.repositories.PaymentRepository;
import com.letrogthien.wallet.repositories.WalletRepository;
import com.letrogthien.wallet.repositories.WithDrawalRepository;
import com.letrogthien.wallet.responses.ApiResponse;
import com.letrogthien.wallet.services.AdminService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final WalletRepository walletRepository;
    private final DepositRepository depositRepository;
    private final WithDrawalRepository withDrawalRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentTransactionMapper paymentTransactionMapper;
    private final WithdrawalRequestMapper withdrawalRequestMapper;
    private final DepositRequestMapper depositRequestMapper;
    private final WalletMapper walletMapper;

    @Override
    public ApiResponse<WalletDto> deleteWallet(UUID walletId) {
        return walletRepository.findById(walletId)
                .map(wallet -> {
                    if (wallet.getStatus() == Status.INACTIVE) {
                        throw new CustomException(ErrorCode.WALLET_ALREADY_DELETED);
                    }
                    wallet.setStatus(Status.INACTIVE);
                    return ApiResponse.<WalletDto>builder()
                            .data(walletMapper.toDto(walletRepository.save(wallet)))
                            .message("Wallet deleted successfully")
                            .build();
                }).orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));
    }

    @Override
    public ApiResponse<WalletDto> getWalletByUser(UUID userId) {
        return ApiResponse.<WalletDto>builder()
                .data(walletMapper.toDto(walletRepository.findByUserId(userId)
                        .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND))))
                .message("Wallet retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<Page<WalletDto>> getAllWallet(Pageable pageable) {
        return ApiResponse.<Page<WalletDto>>builder()
                .data(walletRepository.findAll(pageable).map(
                        walletMapper::toDto
                ))
                .message("Wallet retrieved successfully")
                .build();
        
    }

    @Override
    public ApiResponse<WalletDto> activeWallet(UUID walletId, Boolean isKyc) {
        if (isKyc.equals(Boolean.FALSE)) {
            throw new CustomException(ErrorCode.WALLET_NOT_KYC);
        }
        Wallet wallet = walletRepository.findById(walletId)
                .orElseThrow(() -> new CustomException(ErrorCode.WALLET_NOT_FOUND));
        wallet.setStatus(Status.ACTIVE);
        walletRepository.save(wallet);
        return ApiResponse.<WalletDto>builder()
                .data(walletMapper.toDto(wallet))
                .message("Wallet activated successfully")
                .build();

    }

    @Override
    public ApiResponse<Page<DepositRequestDto>> getDepositRequestByWallet(UUID walletId, Pageable pageable) {
        return ApiResponse.<Page<DepositRequestDto>>builder()
                .data(depositRepository.findAllByWalletId(walletId, pageable).map(
                        depositRequestMapper::toDto
                ))
                .message("Deposit requests retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<Page<WithdrawalRequestDto>> getWithdrawRequestByWallet(UUID walletId, Pageable pageable) {
        return ApiResponse.<Page<WithdrawalRequestDto>>builder()
                .data(withDrawalRepository.findAllByWalletId(walletId, pageable).map(
                        withdrawalRequestMapper::toDto
                ))
                .message("Withdraw requests retrieved successfully")
                .build();
    }

    @Override
    public ApiResponse<Page<PaymentTransactionDto>> getPaymentTransactionByUserId(UUID userId, Pageable pageable) {
        return ApiResponse.<Page<PaymentTransactionDto>>builder()
        .data(paymentRepository.findAllByUserId(userId, pageable).map(
                paymentTransactionMapper::toDto
        ))
        .message("Payment transaction retrieved successfully")
        .build();
    }

    @Override
    public ApiResponse<PaymentTransactionDto> getPaymentTransactionById(UUID id) {
        return ApiResponse.<PaymentTransactionDto>builder()
        .data(paymentRepository.findById(id).map(
                paymentTransactionMapper::toDto
        )
        .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND)))
        .message("Payment transaction retrieved successfully")
        .build();
    }

}
