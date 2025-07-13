package com.letrogthien.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SellerRatingDto {
    private String id;
    private UUID sellerId;
    private UUID buyerId;
    private UUID transactionId;
    private int ratingScore;
    private String reviewText;
    private LocalDateTime createdAt;
}