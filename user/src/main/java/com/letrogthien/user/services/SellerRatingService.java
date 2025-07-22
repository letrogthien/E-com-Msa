package com.letrogthien.user.services;

import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.responses.ApiResponse;

import java.util.List;
import java.util.UUID;

public interface SellerRatingService {

    /**
     * Allows a buyer to rate a seller for a specific transaction.
     * @param buyerId The ID of the buyer.
     * @param sellerId The ID of the seller being rated.
     * @param transactionId The ID of the transaction for which the rating is given.
     * @param score The rating score (e.g., 1 to 5 stars).
     * @param review An optional review comment.
     * @return An ApiResponse containing the created SellerRatingDto.
     */
    ApiResponse<SellerRatingDto> rateSeller(UUID buyerId, UUID sellerId, UUID transactionId, int score, String review);

    /**
     * Retrieves all ratings for a specific seller.
     * @param sellerId The ID of the seller.
     * @return An ApiResponse containing a list of SellerRatingDto for the seller.
     */
    ApiResponse<List<SellerRatingDto>> getRatingsForSeller(UUID sellerId);

    /**
     * Retrieves all ratings given by a specific buyer.
     * @param buyerId The ID of the buyer.
     * @return An ApiResponse containing a list of SellerRatingDto given by the buyer.
     */
    ApiResponse<List<SellerRatingDto>> getRatingsByBuyer(UUID buyerId);

    /**
     * Calculates and retrieves the average rating for a specific seller.
     * @param sellerId The ID of the seller.
     * @return An ApiResponse containing the average rating as a Double.
     */
    ApiResponse<Double> getAverageRating(UUID sellerId);

    /**
     * Checks if a buyer has already rated a specific transaction.
     * @param buyerId The ID of the buyer.
     * @param transactionId The ID of the transaction.
     * @return An ApiResponse containing a Boolean indicating if the buyer has rated the transaction.
     */
    ApiResponse<Boolean> hasBuyerRated(UUID buyerId, UUID transactionId);
}