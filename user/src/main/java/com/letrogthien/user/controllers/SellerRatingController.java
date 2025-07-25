package com.letrogthien.user.controllers;

import com.letrogthien.user.dtos.SellerRatingDto;
import com.letrogthien.user.responses.ApiResponse;
import com.letrogthien.user.services.SellerRatingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seller-ratings")
@RequiredArgsConstructor
public class SellerRatingController {

    private final SellerRatingService sellerRatingService;

    @PostMapping("/rate")
    public ResponseEntity<ApiResponse<SellerRatingDto>> rateSeller(
            @RequestParam UUID buyerId,
            @RequestParam UUID sellerId,
            @RequestParam UUID transactionId,
            @RequestParam int score,
            @RequestParam(required = false) String review) {
        return ResponseEntity.ok(sellerRatingService.rateSeller(buyerId, sellerId, transactionId, score, review));
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<ApiResponse<List<SellerRatingDto>>> getRatingsForSeller(
            @PathVariable UUID sellerId) {
        return ResponseEntity.ok(sellerRatingService.getRatingsForSeller(sellerId));
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<ApiResponse<List<SellerRatingDto>>> getRatingsByBuyer(
            @PathVariable UUID buyerId) {
        return ResponseEntity.ok(sellerRatingService.getRatingsByBuyer(buyerId));
    }

    @GetMapping("/average/{sellerId}")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(
            @PathVariable UUID sellerId) {
        return ResponseEntity.ok(sellerRatingService.getAverageRating(sellerId));
    }

    @GetMapping("/check")
    public ResponseEntity<ApiResponse<Boolean>> hasBuyerRated(
            @RequestParam UUID buyerId,
            @RequestParam UUID transactionId) {
        return ResponseEntity.ok(sellerRatingService.hasBuyerRated(buyerId, transactionId));
    }
}
