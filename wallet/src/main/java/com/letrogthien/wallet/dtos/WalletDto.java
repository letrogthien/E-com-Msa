package com.letrogthien.wallet.dtos;

import com.letrogthien.wallet.common.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class WalletDto {
    private UUID id;
    private UUID userId;
    private BigDecimal balance;
    private String currency;
    private Status status;
    private UUID lastTransactionId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
