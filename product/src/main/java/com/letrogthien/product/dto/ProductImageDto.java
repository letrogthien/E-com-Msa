package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

import com.letrogthien.product.common.Status;

@Data
public class ProductImageDto {
    private UUID id;
    private UUID productId;
    private String imageUrl;
    private Status status;
    private int displayOrder;
    private java.time.LocalDateTime createdAt;
    private java.time.LocalDateTime updatedAt;
}
