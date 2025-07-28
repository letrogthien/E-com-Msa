package com.letrogthien.transaction.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class TransactionEventLogDto {
    private UUID id;
    private UUID orderId;
    private String eventType;
    private String eventDescription;
    private LocalDateTime eventTimestamp;
    private String createdBy;
}
