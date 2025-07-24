package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class ProductReviewDto {
    private UUID id;
    private UUID productId;
    private UUID reviewerId;
    private int rating;
    private String comment;
}
