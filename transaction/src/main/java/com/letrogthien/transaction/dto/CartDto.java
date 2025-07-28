package com.letrogthien.transaction.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
    private UUID id;
    private UUID userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CartItemDto> cartItems;
}
