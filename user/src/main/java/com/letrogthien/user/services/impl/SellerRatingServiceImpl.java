package com.letrogthien.user.services.impl;

import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.repositories.SellerRatingRepository;
import com.letrogthien.user.repositories.UserRepository;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.SellerRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SellerRatingServiceImpl implements SellerRatingService {
    private final UserRepository userRepository;
    private final SellerRatingRepository sellerRatingRepository;


    @Override
    public ApiResponse<SellerRatingDto> rateSeller(UUID buyerId, UUID sellerId, UUID transactionId, int score, String review) {
        return null;
    }

    @Override
    public ApiResponse<List<SellerRatingDto>> getRatingsForSeller(UUID sellerId) {
        return null;
    }

    @Override
    public ApiResponse<List<SellerRatingDto>> getRatingsByBuyer(UUID buyerId) {
        return null;
    }

    @Override
    public ApiResponse<Double> getAverageRating(UUID sellerId) {
        return null;
    }

    @Override
    public ApiResponse<Boolean> hasBuyerRated(UUID buyerId, UUID transactionId) {
        return null;
    }
}
