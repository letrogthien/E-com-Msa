package com.letrogthien.wallet.dtos;

import com.letrogthien.wallet.common.Currency;
import com.letrogthien.wallet.common.PaymentMethod;
import com.letrogthien.wallet.common.Status;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class PaymentTransactionDto {
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private BigDecimal amount;
    private Currency currency;
    private PaymentMethod paymentMethod;
    private String gatewayTransactionId;
    private Status status;
    private String errorMessage;
    private String ipAddress;
    private String userAgent;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
