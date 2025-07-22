package com.letrogthien.user.services.impl;

import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.entities.SellerRating;
import com.letrogthien.user.entities.Transaction;
import com.letrogthien.user.entities.User;
import com.letrogthien.user.exceptions.CustomException;
import com.letrogthien.user.exceptions.ErrorCode;
import com.letrogthien.user.mappers.SellerRatingMapper;
import com.letrogthien.user.repositories.SellerRatingRepository;
import com.letrogthien.user.repositories.TransactionRepository;
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
    private final TransactionRepository transactionRepository;
    private final SellerRatingMapper sellerRatingMapper;


    @Override
    public ApiResponse<SellerRatingDto> rateSeller(UUID buyerId, UUID sellerId, UUID transactionId, int score, String review) {
        User buyer = userRepository.findById(buyerId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        User seller = userRepository.findById(sellerId).orElseThrow(() ->
                new CustomException(ErrorCode.USER_NOT_FOUND)
        );
        Transaction transaction= transactionRepository.findById(transactionId).orElseThrow(() ->
                new CustomException(ErrorCode.NOT_FOUND)
        );
        if (!transactionRepository.existsByUserIdAndId(buyerId, transactionId)) {
            throw new CustomException(ErrorCode.BUYER_NOT_PARTICIPATED_IN_TRANSACTION);
        }
        SellerRating sellerRating = SellerRating.builder()
                .buyer(buyer)
                .seller(seller)
                .transaction(transaction)
                .ratingScore(score)
                .reviewText(review)
                .build();
        sellerRatingRepository.save(sellerRating);
        return ApiResponse.<SellerRatingDto>builder()
                .message("Seller rated successfully")
                .data(sellerRatingMapper.toDto(sellerRating))
                .build();
    }

    @Override
    public ApiResponse<List<SellerRatingDto>> getRatingsForSeller(UUID sellerId) {
        return ApiResponse.<List<SellerRatingDto>>builder()
                .message("Ratings for seller retrieved successfully")
                .data(sellerRatingRepository.findAllBySellerId(sellerId)
                        .stream().map( sellerRatingMapper::toDto).toList())
                .build();
    }

    @Override
    public ApiResponse<List<SellerRatingDto>> getRatingsByBuyer(UUID buyerId) {
        return ApiResponse.<List<SellerRatingDto>>builder()
                .message("Ratings by buyer retrieved successfully")
                .data(sellerRatingRepository.findAllByBuyerId(buyerId)
                        .stream().map(sellerRatingMapper::toDto).toList())
                .build();
    }

    @Override
    public ApiResponse<Double> getAverageRating(UUID sellerId) {
        return ApiResponse.<Double>builder()
                .message("Average rating for seller retrieved successfully")
                .data(sellerRatingRepository.findAverageScoreBySellerId(sellerId))
                .build();
    }

    @Override
    public ApiResponse<Boolean> hasBuyerRated(UUID buyerId, UUID transactionId) {
        return ApiResponse.<Boolean>builder()
                .message("Check if buyer has rated seller")
                .data(sellerRatingRepository.existsByBuyerIdAndTransactionId(buyerId, transactionId))
                .build();
    }
}
