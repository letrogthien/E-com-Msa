package com.letrogthien.wallet.dtos;

import com.letrogthien.wallet.common.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WalletTransactionDto {
    private UUID id;
    private UUID walletId;
    private String transactionType;
    private BigDecimal amount;
    private BigDecimal feeAmount;
    private BigDecimal finalAmount;
    private BigDecimal currentBalanceBefore;
    private BigDecimal currentBalanceAfter;
    private Status status;
    private UUID referenceId;
    private String referenceType;
    private String description;
    private String externalTransactionId;
    private String idempotencyKey;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
