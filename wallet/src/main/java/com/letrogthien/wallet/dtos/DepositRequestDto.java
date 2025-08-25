package com.letrogthien.wallet.dtos;

import com.letrogthien.wallet.common.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class DepositRequestDto {
    private UUID id;
    private UUID walletId;
    private BigDecimal amount;
    private String paymentMethod;
    private String bankCode;
    private String vnpayTxnRef;
    private String vnpayOrderInfo;
    private String vnpayResponseCode;
    private Status status;
    private String errorMessage;
    private UUID walletTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
