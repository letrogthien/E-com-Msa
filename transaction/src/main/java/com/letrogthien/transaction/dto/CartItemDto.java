package com.letrogthien.transaction.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CartItemDto {
    private UUID id;
    private UUID cartId;
    private UUID productId;
    private Integer quantity;
    private LocalDateTime addedAt;
    private LocalDateTime updatedAt;
}
