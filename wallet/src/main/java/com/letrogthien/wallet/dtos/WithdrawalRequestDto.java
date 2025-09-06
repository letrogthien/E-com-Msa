package com.letrogthien.wallet.dtos;

import com.letrogthien.wallet.common.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WithdrawalRequestDto {
    private UUID id;
    private UUID walletId;
    private BigDecimal amount;
    private BigDecimal feeAmount;
    private BigDecimal netAmount;
    private String recipientBankName;
    private String recipientBankAccount;
    private String recipientAccountName;
    private String vnpayTxnRef;
    private Status status;
    private UUID reviewerId;
    private String reviewNotes;
    private String errorMessage;
    private UUID walletTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
