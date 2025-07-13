package com.letrogthien.user.services;

import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface SellerRatingService {

    ApiResponse<SellerRatingDto> rateSeller(UUID buyerId, UUID sellerId, UUID transactionId, int score, String review);

    ApiResponse<List<SellerRatingDto>> getRatingsForSeller(UUID sellerId);

    ApiResponse<List<SellerRatingDto>> getRatingsByBuyer(UUID buyerId);

    ApiResponse<Double> getAverageRating(UUID sellerId);

    ApiResponse<Boolean> hasBuyerRated(UUID buyerId, UUID transactionId);
}