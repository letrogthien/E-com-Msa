package com.letrogthien.product.dto;

import lombok.Data;
import java.util.UUID;

import com.letrogthien.product.common.Status;

@Data
public class ProductReviewDto {
    private UUID id;
    private UUID productId;
    private UUID reviewerId;
    private Status status;
    private int rating;
    private String comment;
}
