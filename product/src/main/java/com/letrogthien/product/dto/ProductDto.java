package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private UUID id;
    private UUID sellerId;
    private UUID categoryId;
    private String title;
    private String description;
    private BigDecimal price;
    private String currency;
    private int stockQuantity;
    private String productStatus;
    private String thumbnailUrl;
    private String detailsJson;
}
